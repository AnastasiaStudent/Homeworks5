package Byzantische;

import Byzantische.Interface.Broadcaster;
import Byzantische.Interface.PathRepository;
import Byzantische.Interface.ValueStrategy;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Process {
    static final boolean debug = System.getProperty("debug", "false").equals("true");
    static int totalMessages;
    protected int id;
    protected Configuration configuration;
    protected ValueStrategy strategy;
    protected Map<String, Node> nodes;
    protected PathRepository repository;
    protected Broadcaster broadcaster;

    /**
     * @param id
     * @param configuration
     * @param repository
     * @param broadcaster
     * @param strategy
     */
    public Process(int id, Configuration configuration, PathRepository repository, Broadcaster broadcaster, ValueStrategy strategy) {
        this.id = id;
        this.strategy = strategy;
        this.repository = repository;
        this.broadcaster = broadcaster;
        this.configuration = configuration;
        this.nodes = new HashMap<String, Node>();
    }


    /**
     * Receiving a message is pretty simple here, it means that some other process
     * calls this method on the current process with path and a node. All we do
     * is store the value, we'll use it in the next round of messaging.
     */
    public void receiveMessage(String path, Node node) {
        nodes.put(path, node);
        totalMessages++;
    }


    /**
     * After constructing all messages, you need to call SendMessages on each process,
     * once per round. This routine will send the appropriate messages for each round
     * to all th eother processes listed in the vector passed in as an argument.
     * <p>
     * Deciding on what messages to send is pretty simple. If we look at the static
     * map pathsByRank, indexed by round and the processId of this process, it gives
     * the entire set of taraget paths that this process needs to send messages to.
     * So there is an iteration loop through that map, and this process sends a message
     * to the correct target process for each path in the map.
     *
     * @param round
     */
    public void sendMessages(int round) {
        List<String> pathList = repository.getRankList(round, id);
        for (String path : pathList) {
            String sourcePath = path.substring(0, path.length() - 1);
            Node source = nodes.get(sourcePath);
            if (source == null)
                throw new IllegalStateException("Failed to find source node for " + sourcePath);
            broadcaster.broadcast(round, id, source, path);
        }
    }

    //
    // After all messages have been sent, it's time to Decide.
    // 
    // This part of the algorithm follows the description in the article closely.
    // It has to work its way from the leaf values up to the root of the tree.
    // The first round consists of going to the leaf nodes, and copying the input
    // value to the output value.
    //
    // All subsequent rounds consist of getting the majority of the output values from
    // each nodes children, and copying that to the nodes output value.
    //
    // When we finally reach the root node, there is only one node with an output value,
    // and that represents this processes decision.
    //
    public Value decide() {
        //
        // Step 1 - set the leaf values
        //
        for (int i = 0; i < configuration.getNumberOfProcesses(); i++) {
            List<String> pathList = repository.getRankList(configuration.getRoundsOfMessages(), i);
            for (String path : pathList) {
                Node node = nodes.get(path);
                node.output = node.input;
            }
        }
        //
        // Step 2 - work up the tree
        //
        for (int round = configuration.getRoundsOfMessages() - 1; round >= 0; round--) {
            for (int i = 0; i < configuration.getNumberOfProcesses(); i++) {
                List<String> pathList = repository.getRankList(round, i);
                for (String path : pathList) {
                    Node node = nodes.get(path);
                    node.output = getMajority(path);
                }
            }
        }
        List<String> pathList = repository.getRankList(0, configuration.getSource());
        String topPath = pathList.get(0);
        return nodes.get(topPath).output;
    }

    //
    // This routine calculates the majority value for the children of a given
    // path. The logic is pretty simple, we increment the count for all possible
    // values over the children. If there is a clearcut majority, we return that,
    // otherwise we return the default value defined by the strategy class.
    //
    public Value getMajority(String path) {
        Map<Value, Integer> counts = new HashMap<Value, Integer>();
        counts.put(Value.ONE, new Integer(0));
        counts.put(Value.ZERO, new Integer(0));
        counts.put(Value.UNKNOWN, new Integer(0));
        Collection<String> list = repository.getPathChildren(path);
        int n = 0;
        if (list == null) {
            if (debug)
                System.out.println("No child found for '" + path + "'");
        } else {
            n = list.size();
            for (String child : list) {
                Node node = nodes.get(child);
                if (node != null) {
                    counts.put(node.output, counts.get(node.output) + 1);
                } else if (debug) {
                    //System.out.println("Could not find node for count with path " + child);
                }
            }
        }

        if (counts.get(Value.ONE) > (n / 2))
            return Value.ONE;
        else if (counts.get(Value.ZERO) > (n / 2))
            return Value.ZERO;
        else if (counts.get(Value.ONE).intValue() == counts.get(Value.ZERO).intValue() &&
            counts.get(Value.ONE) == (n / 2))
            return strategy.getDefaultValue();
        return Value.UNKNOWN;
    }


    /**
     * A utility routine that tells whether a given process is faulty
     */

    public boolean isFaulty() {
        return strategy.isFaulty(id);
    }

    /**
     * Another somewhat handy utility routine
     */
    public boolean isSource() {
        return configuration.getSource() == id;
    }
}

