package Byzantische.Interface;

import java.util.List;

public interface PathRepository {

    public void generateChildren();

    public List<String> getRankList(int rank, int source);

    public List<String> getPathChildren(String path);
}
