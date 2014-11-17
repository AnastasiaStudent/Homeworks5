package Byzantische;

public class Node {
    Value input;
    Value output;

    public Node() {
        this(Value.FAULTY, Value.FAULTY);
    }

    /**
     * @param input
     * @param output
     */
    public Node(Value input, Value output) {
        this.input = input;
        this.output = output;
    }
}
