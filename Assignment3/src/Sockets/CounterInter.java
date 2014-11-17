package Sockets;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CounterInter implements Serializable {
    String answer;
    int counterJA, counterMAYBE, counterNEIN;
    ConcurrentHashMap<UUID, String> chm;

    public CounterInter() {
        chm = new ConcurrentHashMap<UUID, String>();
    }

    public int getCounterMAYBE() {
        return counterMAYBE;
    }

    public void setCounterMAYBE(int counterMAYBE) {
        this.counterMAYBE = +counterMAYBE;
    }

    public int getCounterJA() {
        return counterJA;
    }

    public void setCounterJA(int counterJA) {
        this.counterJA = +counterJA;
    }

    public int getCounterNEIN() {
        return counterNEIN;
    }

    public void setCounterNEIN(int counterNEIN) {
        this.counterNEIN = +counterNEIN;
    }

    public String getAnswer() {
        return answer;
    }

    public void getAnswersMap() {
        for (ConcurrentHashMap.Entry<UUID, String> entry : chm.entrySet()) {
            System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());
        }
    }

    public void setUUIDandAnswer(String answer) throws InterruptedException {
        chm.put(UUID.randomUUID(), answer);
        this.answer = answer;
    }

    public int sizeOfQueue() {
        return chm.size();
    }

}
