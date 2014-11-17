package ana1;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class General2 {
    private static final String DESTINATION1 = "queue/aufgabe6Queue1";
    private static final String DESTINATION2 = "queue/aufgabe6Queue2";
    private static final String DESTINATION3 = "queue/aufgabe6Queue3";
    private static final String USER = "guest";
    private static final String PASSWORD = "guest";
    private String[] Werte = new String[3];
    private int i = 0;

    public General2() {

    }

    public static Context getInitialContext() throws NamingException {
        Properties props = new Properties();
        props.put("java.naming.factory.initial",
            "org.jnp.interfaces.NamingContextFactory");
        props.put("java.naming.provider.url", "jnp://localhost:1099");
        props.put("java.naming.factory.url.pkgs",
            "org.jboss.naming:org.jnp.interfaces");
        props.put("jnp.socket.Factory", "org.jnp.interfaces.TimedSocketFactory");
        Context context = new InitialContext(props);
        return context;
    }

    public static void main(String[] args) throws Exception {
        General2 general2 = new General2();
        general2.ersteRunde();
        general2.zweiteRundeZuGen1();
        general2.zweiteRundeZuGen3();
        general2.vergleich();
    }

    public void ersteRunde() throws NamingException, JMSException {

        Context ctx = getInitialContext();
        QueueConnectionFactory factory = (QueueConnectionFactory) ctx
            .lookup("ConnectionFactory");
        Queue queue = (Queue) ctx.lookup(DESTINATION2);
        QueueConnection connection = factory.createQueueConnection(USER,
            PASSWORD);
        QueueSession session = connection.createQueueSession(false,
            Session.AUTO_ACKNOWLEDGE);
        QueueReceiver receiver = session.createReceiver(queue);
        connection.start();
        System.out.println("Auf Nachricht warten ...");

        TextMessage nachricht = (TextMessage) receiver.receive();
        for (int i = 0; i < 20; i++) {
            TextMessage nachricht2 = (TextMessage) receiver.receive();
        }

        Werte[0] = nachricht.getText();
        System.out.println("Kommander sagt: " + Werte[0]);
        receiver.close();
        session.close();
        connection.close();
    }

    public void zweiteRundeZuGen1() throws JMSException, NamingException {
        Context ctx2 = getInitialContext();
        QueueConnectionFactory factory2 = (QueueConnectionFactory) ctx2
            .lookup("ConnectionFactory");
        Queue queue2 = (Queue) ctx2.lookup(DESTINATION2);
        QueueConnection connection2 = factory2.createQueueConnection(USER,
            PASSWORD);
        QueueSession session2 = connection2.createQueueSession(false,
            Session.AUTO_ACKNOWLEDGE);
        QueueReceiver receiver2 = session2.createReceiver(queue2);
        connection2.start();
        System.out.println("Auf Nachricht warten ...");

        TextMessage nachricht = (TextMessage) receiver2.receive();
        Werte[1] = nachricht.getText();
        System.out.println("General 1 sagt: " + Werte[1]);
        Queue tempQueue2 = (Queue) nachricht.getJMSReplyTo();
        TextMessage antwort = session2.createTextMessage();
        antwort.setText(Werte[0]);
        QueueSender sender2 = session2.createSender(tempQueue2);
        System.out.println("Ich an General 1 schicke: " + antwort.getText());
        try {
            sender2.send(antwort);
        } finally {
            sender2.close();
            receiver2.close();
            session2.close();
            connection2.close();
        }
    }

    public void zweiteRundeZuGen3() throws JMSException, NamingException {
        Context ctx3 = getInitialContext();
        QueueConnectionFactory factory3 = (QueueConnectionFactory) ctx3.lookup("ConnectionFactory");
        Queue queue3 = (Queue) ctx3.lookup(DESTINATION3);
        QueueConnection connection3 = null;
        QueueSession session3 = null;
        TemporaryQueue tempQueue3 = null;
        QueueSender sender3 = null;
        QueueReceiver receiver3 = null;

        try {
            connection3 = factory3.createQueueConnection(USER, PASSWORD);
            session3 = connection3.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            // temporäre Queue für die Antwort erzeugen
            tempQueue3 = session3.createTemporaryQueue();
            sender3 = session3.createSender(queue3);
            receiver3 = session3.createReceiver(tempQueue3);
            connection3.start();

            TextMessage request = session3.createTextMessage();
            request.setText(Werte[0]);
            request.setJMSReplyTo(tempQueue3);
            sender3.send(request);
            System.out.println("Zweite Runde. An General3:" + Werte[0]);
            System.out.println("auf Antwort warten");
            TextMessage response = (TextMessage) receiver3.receive();
            Werte[2] = response.getText();
            System.out.println("Dritter General meldet: " + Werte[2]);
        } finally {
            sender3.close();
            receiver3.close();
            tempQueue3.delete();
            session3.close();
            connection3.close();
        }
    }

    public void vergleich() {
        for (int i = 0; i < 3; i++) {
            System.out.println(i + Werte[i]);
        }
    }
}
