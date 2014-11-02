package Socket;

import java.io.Serializable;

/**
 * Created by jm on 10/28/2014.
 */
public class Question implements Serializable {
    String ja = "ja";
    String nein = "nein";
    String maybe = "maybe";

    String DeineAbstimmung = null;

    int counterJA = 0;
    int counterMAYBE = 0;
    int counterNEIN = 0;

    public Question() {

    }
}
