package ana2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;


public class ByzGenOne {
    public static void main(String args[]) throws Exception {
        int icounter;
        InetAddress lclhost;
        GenOne gentwo, genthree, genfour;

        for (icounter = 0; icounter < 2; icounter++) {
            lclhost = InetAddress.getLocalHost();
            gentwo = new GenOne(lclhost);
            gentwo.setSendPort(9001);
            gentwo.setRecPort(8002);

            gentwo.sendData();
            gentwo.recData();

            lclhost = InetAddress.getLocalHost();
            genthree = new GenOne(lclhost);
            genthree.setSendPort(9003);
            genthree.setRecPort(8004);

            genthree.sendData();
            genthree.recData();

            lclhost = InetAddress.getLocalHost();
            genfour = new GenOne(lclhost);
            genfour.setSendPort(9005);
            genfour.setRecPort(8006);

            genfour.sendData();
            genfour.recData();

            GenOne.vecdata = true;

        }
    }
}


class GenOne {
    static boolean vecdata = false;
    static int dataarr[] = new int[12];
    static int finalarr[] = new int[3];
    private static String vecstr = "", datastr = "";
    private static int recctr = 0;
    private static int ctr = 0;
    private static int fnctr = 0;
    private static int recdata = 0;
    InetAddress lclhost;
    int sendport, recport;

    GenOne(InetAddress lclhost) {
        this.lclhost = lclhost;
    }

    public void setSendPort(int sendport) {
        this.sendport = sendport;
    }

    public void setRecPort(int recport) {
        this.recport = recport;
    }

    public void sendData() throws Exception {
        DatagramSocket ds;
        DatagramPacket dp;
        BufferedReader br;
        System.out.println("Enter the Data");

        if (vecdata == false) {
            br = new BufferedReader(new InputStreamReader(System.in));
            datastr = br.readLine();
        } else {
            br = new BufferedReader(new InputStreamReader(System.in));
            datastr = br.readLine();
            datastr = vecstr;
        }

        ds = new DatagramSocket(sendport);
        dp = new DatagramPacket(datastr.getBytes(), datastr.length(), lclhost, sendport - 1000);
        ds.send(dp);
        ds.close();
    }

    public void recData() throws Exception {
        DatagramSocket ds;
        DatagramPacket dp;
        byte[] buf = new byte[256];

        String msgstr;
        ds = new DatagramSocket(recport);
        dp = new DatagramPacket(buf, buf.length);
        ds.receive(dp);
        ds.close();

        msgstr = new String(dp.getData(), 0, dp.getLength());
        System.out.println(msgstr);
        int l = msgstr.length();
        System.out.println("Hello");
        System.out.println("Length =  " + l);

        if (msgstr.length() == 9) {
            recctr++;
            recdata = Integer.parseInt(msgstr);
            dataarr[ctr++] = recdata / 1000000;
            recdata = recdata % 1000000;
            dataarr[ctr++] = recdata / 1000;
            recdata = recdata % 1000;
            dataarr[ctr++] = recdata;
            if (recctr == 3) {
                maxval();
            }
        }
        if (vecdata == false) {
            vecstr = vecstr.concat(msgstr);
            System.out.println("Vector Data " + vecstr);
        }

    }

    public void maxval() {
        int ctr1, ctr2, i, j;
        boolean gentwo = false, genthree = false, genfour = false;
        for (ctr1 = 0; ctr1 < 9; ctr1++) {
            i = 0;
            j = dataarr[ctr1];
            if (dataarr[ctr1] != 0) {
                //System.out.println("found");
                for (ctr2 = 0; ctr2 < 9; ctr2++) {
                    if (j == dataarr[ctr2]) {
                        i++;
                        dataarr[ctr2] = 0;
                    }
                    if (i == 2) {
                        finalarr[fnctr++] = j;
                    }
                }

            }

            Arrays.sort(finalarr);
            System.out.println("Final Vector");

            for (ctr1 = 0; ctr1 < 3; ctr1++) {
                System.out.println(finalarr[ctr1] + " ");
            }

            for (ctr1 = 0; ctr1 < 3; ctr1++) {
                if (finalarr[ctr1] >= 200 && finalarr[ctr1] < 300) {
                    System.out.println("General Number two is Loyal");
                    gentwo = true;
                } else if (finalarr[ctr1] >= 300 && finalarr[ctr1] < 400) {
                    System.out.println("General Number three is Loyal");
                    genthree = true;
                } else if (finalarr[ctr1] >= 400 && finalarr[ctr1] < 500) {
                    System.out.println("General Number four is Loyal");
                    genfour = true;
                }
            }

            if (gentwo == false) {
                System.out.println("General Number Two is Not Loyal");
            }

            if (genthree == false) {
                System.out.println("General Number Three is Not Loyal");
            }

            if (genfour == false) {
                System.out.println("General Number Four is Not Loyal");
            }

        }
    }
}
   
	
  
			
        	

	
			

	
	
	
	
	
