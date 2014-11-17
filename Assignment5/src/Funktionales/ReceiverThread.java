package Funktionales;

import com.rabbitmq.client.QueueingConsumer;

import java.sql.Timestamp;
import java.util.Calendar;

public class ReceiverThread implements Runnable {
    public QueueingConsumer consumer;
    public int userID;
    public Timestamp tm;

    public ReceiverThread(QueueingConsumer consumer, int userID) {
        super();
        this.consumer = consumer;
        this.userID = userID;
        tm = new Timestamp(Calendar.getInstance().getTime().getTime());
    }

    public void run() {
        QueueingConsumer.Delivery delivery;
        try {
            while (true) {
                delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                String userID = message.split(":")[0];
                //                userID.equalsIgnoreCase(String.valueOf(this.userID));
                System.out.println(tm.toString() + "::::" + consumer.getConsumerTag() + ":'" + message + "'");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
