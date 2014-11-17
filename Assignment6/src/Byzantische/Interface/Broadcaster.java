package Byzantische.Interface;

import Byzantische.Node;

public interface Broadcaster {
    public void broadcast(int round, int id, Node source, String path);
} 
