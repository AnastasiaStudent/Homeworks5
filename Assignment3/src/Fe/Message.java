package Fe;

import java.io.Serializable;

/**
 * Created by jm on 11/4/2014.
 */
public class Message implements Serializable {
    static final int maybe = 0;
    public static final int ja = 1;
    static final int nein = 2;
    int type;
    String ques = "Is he black";
    String antwort;

    public Message(String antwort) {
        this.antwort = antwort;
    }

    public Message(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAntwort() {
        return antwort;
    }

    public void setAntwort(String antwort) {
        this.antwort = antwort;
    }


}
