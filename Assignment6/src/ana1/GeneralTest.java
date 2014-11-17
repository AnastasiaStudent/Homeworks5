package ana1;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;


public class GeneralTest {
    private static final String DESTINATION1 = "queue/aufgabe6Queue1";
    private static final String DESTINATION2 = "queue/aufgabe6Queue2";
    private static final String DESTINATION3 = "queue/aufgabe6Queue3";
    private static final String USER = "guest";
    private static final String PASSWORD = "guest";
    private String[] Werte = new String[3];
    private String text;
    private QueueConnectionFactory factory2;
    private Queue queue2;
    private QueueConnectionFactory factory;
    private Queue queue;

    public GeneralTest() {

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
        GeneralTest gt = new GeneralTest();
        gt.ersteRunde();
        gt.zweiteRundeZuGen2();
        gt.vergleich();
        //gt.ersteRunde();
        //gt.zweiteRunde();
    }

    public void ersteRunde() throws NamingException, JMSException {
        Context ctx = getInitialContext();
        factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
        queue = (Queue) ctx.lookup(DESTINATION1);
        QueueConnection connection = factory.createQueueConnection(USER, PASSWORD);
        QueueSession session = connection.createQueueSession(
            false, Session.AUTO_ACKNOWLEDGE);
        QueueReceiver receiver = session.createReceiver(queue);
        connection.start();
        System.out.println("Auf Nachricht warten ...");

        TextMessage nachricht = (TextMessage) receiver.receive();
        for (int i = 0; i < 50; i++) {
            TextMessage nachricht2 = (TextMessage) receiver.receive();
        }

        Werte[0] = nachricht.getText();
        System.out.println("Kommander sagt: " + Werte[0]);
        receiver.close();
        session.close();
        connection.close();
    }

    public void zweiteRundeZuGen2() throws JMSException, NamingException {
        Context ctx2 = getInitialContext();
        factory2 = (QueueConnectionFactory) ctx2.lookup("ConnectionFactory");
        queue2 = (Queue) ctx2.lookup(DESTINATION2);
        QueueConnection connection2 = null;
        QueueSession session2 = null;
        TemporaryQueue tempQueue2 = null;
        QueueSender sender2 = null;
        QueueReceiver receiver2 = null;

        try {
            connection2 = factory2.createQueueConnection(USER, PASSWORD);
            session2 = connection2.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            // temporäre Queue für die Antwort erzeugen
            tempQueue2 = session2.createTemporaryQueue();

            sender2 = session2.createSender(queue2);
            receiver2 = session2.createReceiver(tempQueue2);
            connection2.start();

            TextMessage request = session2.createTextMessage();
            request.setText(Werte[0]);
            request.setJMSReplyTo(tempQueue2);
            sender2.send(request);
            System.out.println("Zweite Runde. An General2:" + Werte[0]);
            System.out.println("auf Antwort warten");
            TextMessage response = (TextMessage) receiver2.receive();
            Werte[1] = response.getText();
            System.out.println("Zweiter General meldet: " + Werte[1]);
        } finally {
            sender2.close();
            receiver2.close();
            tempQueue2.delete();
            session2.close();
            connection2.close();
        }
    }

    public void vergleich() {
        for (int i = 0; i < 3; i++) {
            System.out.println(i + Werte[i]);
        }
    }


}
