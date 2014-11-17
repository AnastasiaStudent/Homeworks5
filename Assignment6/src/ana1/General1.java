package ana1;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class General1 {
    private static final String DESTINATION1 = "queue/aufgabe6Queue1";
    private static final String DESTINATION2 = "queue/aufgabe6Queue2";
    private static final String USER = "guest";
    private static final String PASSWORD = "guest";
    TextMessage message;
    private QueueConnectionFactory factory;
    private QueueConnection connection;
    private QueueSession session;
    private QueueReceiver receiver1;
    private QueueReceiver receiver2;
    private QueueSender sender1;
    private QueueSender sender2;
    private long timeout;
    private Context ctx;
    private int Szenario;
    private String[] Werte = new String[3];

    public General1(int Szenario) throws NamingException {
        this.timeout = 20000l;
        this.Szenario = Szenario;
        // JNDI-Kontext erzeugen
        ctx = getInitialContext();

        // ConnectionFactory �ber Namensdienst auslesen
        factory =
            (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
    }

    public static void main(String[] args) throws Exception {
        General1 general1 = new General1(1);
        general1.ersteRunde();
        // general1.zweiteRunde();

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

    // Aktives Warten auf Nachrichten (Pull-Prinzip)
    public void ersteRunde() throws JMSException, NamingException {
        // Zieladresse �ber Namensdienst auslesen
        Queue queue = (Queue) ctx.lookup(DESTINATION1);
        // Verbindung aufbauen
        connection = factory.createQueueConnection(USER, PASSWORD);
        // Session erzeugen
        session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        // Empf�nger erzeugen
        receiver1 = session.createReceiver(queue);
        // Empfang von Nachrichten starten
        connection.start();
        Message message;
        while ((message = receiver1.receive(10000)) != null) {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                Werte[0] = textMessage.getText();
                System.out.println("Vom Kommander: " + Werte[0]);
            }
        }
        receiver1.close();
        session.close();
        connection.close();
    }

    public void zweiteRunde() throws JMSException, NamingException {
        // JNDI-Kontext erzeugen
        ctx = getInitialContext();

        // ConnectionFactory �ber Namensdienst auslesen
        factory =
            (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
        Queue queue1 = (Queue) ctx.lookup(DESTINATION1);
        Queue queue2 = (Queue) ctx.lookup(DESTINATION2);
        connection = factory.createQueueConnection(USER, PASSWORD);
        session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        sender1 = session.createSender(queue1);
        sender2 = session.createSender(queue2);
        // Empf�nger erzeugen
        receiver1 = session.createReceiver(queue1);
        receiver2 = session.createReceiver(queue2);


        switch (Szenario) {
            case 1:
                System.out.println("Szenario Nr.1. Alle Generäle arbeiten korrekt.");
                message = session.createTextMessage();
                message.setText(Werte[0]);
                sender1.setTimeToLive(10000);
                sender1.send(message);
                System.out.println("an General2: " + message.getText());
                sender1.close();

                sender2.setTimeToLive(10000);
                sender2.send(message);
                System.out.println("an General3: " + message.getText());
                sender2.close();
                break;
            case 2:
                System.out.println("Szenario Nr.2");
                message = session.createTextMessage();
                message.setText(Werte[0]);
                sender1.setTimeToLive(10000);
                sender1.send(message);
                System.out.println("an General2: " + message.getText());
                sender1.close();
                message.setText("Hallo Welt!");
                sender2.setTimeToLive(10000);
                sender2.send(message);
                System.out.println("an General3: " + message.getText());
                sender2.close();
                break;
            case 3:
                System.out.println("i ist zwei");
                break;
            case 4:
                System.out.println("i ist drei");
                break;
            case 5:
                System.out.println("i ist drei");
                break;
            default:
                System.out.println("Dieses Szenario wurde nicht programmiert.");
        }
        // Empfang von Nachrichten starten
        connection.start();
        Message message1;
        while ((message1 = receiver1.receive(10000)) != null) {
            if (message1 instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message1;
                Werte[1] = textMessage.getText();
                System.out.println("Vom General2:" + Werte[1]);
            }
        }
        Message message2;
        while ((message2 = receiver2.receive(10000)) != null) {
            if (message2 instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message1;
                Werte[2] = textMessage.getText();
                System.out.println("Vom General3:" + Werte[2]);
            }
        }
        receiver1.close();
        receiver2.close();
        session.close();
        connection.close();
    }
}
