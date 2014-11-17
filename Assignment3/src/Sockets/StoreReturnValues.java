package Sockets;

import java.io.*;

public class StoreReturnValues implements Serializable {
    CounterInter ci = new CounterInter();

    /**
     * Stores CI Object to write and later read from file
     *
     * @param ci
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public synchronized void store(CounterInter ci) throws IOException, ClassNotFoundException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("answers.ser"))) {
            oos.writeObject(ci);
            System.out.print("I have stored CI object in file");
            this.ci = returnci();
            if (ci.getAnswer().equals("ja")) {
                ci.setCounterJA(1);
            }
            if (ci.getAnswer().equals("nein")) {
                ci.setCounterNEIN(1);
            }
            if (ci.getAnswer().equals("maybe")) {
                ci.setCounterMAYBE(1);
            }
            String msg = "\n\n " + ci.sizeOfQueue() + " clients voted all in all as follows: \n Ja: " +
                ci.getCounterJA() + "\n Nein: " + ci.getCounterNEIN() + "\n Maybe: " + ci.getCounterMAYBE();
            System.out.println(msg);
            oos.writeUTF(msg);// store in plaintext
            oos.flush();
            oos.close();
        }
    }

    /**
     * Hell
     *
     * @return read
     */
    private CounterInter returnci() {
        ObjectInputStream ios = null;
        try {
            ios = new ObjectInputStream(new FileInputStream("answers.ser"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ci = (CounterInter) ios.readObject();
            System.out.print(" and I have read object from file");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception to read Object from file:  " + e.getMessage() + " " + e.toString());
        }
        return ci;
    }

}
