package Byzantische;

import Byzantische.Interface.Broadcaster;
import Byzantische.Interface.Engine;
import Byzantische.Interface.PathRepository;
import Byzantische.Interface.ValueStrategy;

import java.util.ArrayList;
import java.util.List;

public class DefaultEngine implements Engine, Broadcaster {
    static final boolean debug = System.getProperty("debug", "false").equals("true");
    private Configuration configuration;
    private PathRepository repository;
    private List<Process> processes;
    private ValueStrategy strategy;

    /**
     * @param source
     * @param roundsOfMessages
     * @param numberOfProcesses
     */
    public DefaultEngine(int source, int roundsOfMessages, int numberOfProcesses) {
        this.configuration = new Configuration(source, roundsOfMessages, numberOfProcesses);
        this.strategy = new DefaultValueStrategy(configuration);
        this.repository = new DefaultPathRepository(configuration);
        this.processes = new ArrayList<Process>();
    }

    /**
     * @param round
     * @param id
     * @param source
     * @param path
     */
    public void broadcast(int round, int id, Node source, String path) {
        for (int j = 0; j < configuration.getNumberOfProcesses(); j++) {
            if (j != configuration.getSource()) {
                Value value = strategy.getValue(source.input, id,
                    j,
                    path);
                if (debug) {
                    String sourcePath = path.substring(0, path.length() - 1);
                    System.out.println("Sending for round " + round + " from process " + id + " to " + j + ": {" + value +
                        ", " + path + ", " + Value.UNKNOWN + "}, getting value from source " + sourcePath);
                }
                getProcesses().get(j).receiveMessage(path, new Node(value, Value.UNKNOWN));
            }
        }
    }


    public List<Process> getProcesses() {
        return processes;
    }

    public void run() {
        //
        // Starting at round 0 and working up to round M, call the
        // SendMessages() method of each process. It will send the appropriate
        // message to all other sibling processes.
        //
        for (int i = 0; i <= configuration.getRoundsOfMessages(); i++) {
            for (int j = 0; j < configuration.getNumberOfProcesses(); j++) {
                processes.get(j).sendMessages(i);
            }
        }

        //
        // All that is left is to print out the results. For non-faulty processes,
        // we call the Decide() method to see what what the process decision was
        //
        for (int j = 0; j < configuration.getNumberOfProcesses(); j++) {
            if (processes.get(j).isSource())
                System.out.print("Source ");
            System.out.print("Process " + j);
            if (!processes.get(j).isFaulty()) {
                System.out.print(" decides on value " +
                    processes.get(j).decide());
            } else {
                System.out.print(" is faulty");
            }
            System.out.println();
        }
    }


    public void start() {
        for (int i = 0; i < configuration.getNumberOfProcesses(); i++) {
            Process process =
                i == configuration.getSource() ?
                    new GeneralProcess(i, configuration, repository, this, strategy) :
                    new Process(i, configuration, repository, this, strategy);
            processes.add(process);
        }
        repository.generateChildren();
    }
}
