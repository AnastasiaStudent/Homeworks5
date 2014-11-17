package Byzantische;

import Byzantische.Interface.ValueStrategy;

public class DefaultValueStrategy implements ValueStrategy {
    private Configuration configuration;
    private Node sourceValue;

    /**
     * @param configuration
     */
    public DefaultValueStrategy(Configuration configuration) {
        this.configuration = configuration;
        this.sourceValue = new Node(Value.ZERO, Value.UNKNOWN);
    }

    /**
     * This method returns the true value of the source's value. The source may send
     * faulty values to other processes, but the Node returned by this value will be
     * in its root node.
     * In this case, the General's node has in input value of 0, which makes that
     * the desired value. Of course, since the General is faulty, this doesn't really
     * matter.
     *
     * @return
     */
    public Node getSourceValue() {
        return sourceValue;
    }

    //
    // During message, GetValue() is called to get the value returned by a given process
    // during a messaging round. 'value' is the input value that it should be sending to 
    // the destination process (if it isn't faulty), source is the source process ID,
    // destination is the destination process ID, and Path is the path being used for this
    // particular message.
    //
    // In this particular implementation, we have two faulty processes - the source
    // process, which returns a sort-of random value, and process ID 2, which returns
    // a ONE always, in contradiction of the General's desired value of 0.
    //
    public Value getValue(Value value, int source, int destination, String path) {
        if (configuration.getSource() == source)
            return (destination & 1) != 0 ? Value.ZERO : Value.ONE;
        else if (source == 2)
            return Value.ONE;
        return value;
    }


    //
    // When breaking a tie, GetDefault() is used to get the default value.
    //
    // This is an arbitrary decision, but it has to be consistent across all processes.
    // More importantly, the processes have to arrive at a correct decision regardless
    // of whether the default value is always 0 or always 1. In this case we've set it to 
    // a value of 1.
    //
    public Value getDefaultValue() {
        return Value.ONE;
    }

    //
    // This method is used to identify fault processes by ID
    //
    public boolean isFaulty(int process) {
        return process == configuration.getSource() || process == 2;
    }
}

