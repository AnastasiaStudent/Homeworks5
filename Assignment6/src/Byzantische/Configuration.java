package Byzantische;

public class Configuration {
    private int source;
    private int roundsOfMessages;
    private int numberOfProcesses;

    /**
     * @param source
     * @param roundsOfMessages
     * @param numberOfProcesses
     */
    public Configuration(int source, int roundsOfMessages, int numberOfProcesses) {
        this.source = source;
        this.roundsOfMessages = roundsOfMessages;
        this.numberOfProcesses = numberOfProcesses;
    }

    /**
     * @return
     */
    public int getSource() {
        return source;
    }


    public int getRoundsOfMessages() {
        return roundsOfMessages;
    }


    public int getNumberOfProcesses() {
        return numberOfProcesses;
    }
}
