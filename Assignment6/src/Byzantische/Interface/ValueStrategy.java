package Byzantische.Interface;

import Byzantische.Node;
import Byzantische.Value;

public interface ValueStrategy {

    public Node getSourceValue();

    public Value getValue(Value value, int source, int destination, String path);

    public Value getDefaultValue();

    public boolean isFaulty(int process);
}
