package Byzantische;

import Byzantische.Interface.PathRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultPathRepository implements PathRepository {
    static final boolean debug = System.getProperty("debug", "false").equals("true");
    private Map<String, List<String>> children;
    private Map<Integer, Map<Integer, List<String>>> pathByRank;
    private Configuration configuration;

    /**
     * @param configuration
     */
    public DefaultPathRepository(Configuration configuration) {
        this.configuration = configuration;
        this.children = new HashMap<String, List<String>>();
        this.pathByRank = new HashMap<Integer, Map<Integer, List<String>>>();
    }

    /**
     * @return
     */
    public Map<String, List<String>> getChildren() {
        return children;
    }

    /**
     * @param path
     * @return
     */
    public List<String> getPathChildren(String path) {
        List<String> pathList = children.get(path);
        if (pathList == null) {
            pathList = new ArrayList<String>();
            children.put(path, pathList);
        }
        return pathList;
    }

    /**
     * @param rank
     * @param source
     * @return
     */
    public List<String> getRankList(int rank, int source) {
        Map<Integer, List<String>> pathMap = pathByRank.get(rank);
        if (pathMap == null) {
            pathMap = new HashMap<Integer, List<String>>();
            pathByRank.put(rank, pathMap);
        }
        List<String> pathList = pathMap.get(source);
        if (pathList == null) {
            pathList = new ArrayList<String>();
            pathMap.put(source, pathList);
        }
        return pathList;
    }

    public void generateChildren() {
        generateChildren(configuration.getSource(), new boolean[configuration.getNumberOfProcesses()], "", 0);
    }

    /**
     * @param source
     * @param ids
     * @param path
     * @param rank
     */
    private void generateChildren(int source, boolean[] ids, String path, int rank) {
        ids[source] = true;
        path += toCharString(source);
        getRankList(rank, source).add(path);

        //
        if (rank < configuration.getRoundsOfMessages()) {
            for (int i = 0; i < ids.length; i++) {
                if (!ids[i]) {
                    boolean[] newIds = new boolean[ids.length];
                    System.arraycopy(ids, 0, newIds, 0, ids.length);
                    generateChildren(i, newIds, path, rank + 1);
                    getPathChildren(path).add(path + toCharString(i));
                }
            }
        }

        //
        if (debug) {
            System.out.print("generateChildren(" + source + "," + rank + "," + path + "), children = ");
            List<String> list = getPathChildren(path);
            for (String s : list) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }


    //
    private char toChar(int n) {
        return (char) (n + '0');
    }

    private String toCharString(int n) {
        return String.valueOf(toChar(n));
    }
}
