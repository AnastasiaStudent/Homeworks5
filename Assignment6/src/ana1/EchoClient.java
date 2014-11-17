package ana1;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class EchoClient {
    private static final String DESTINATION = "queue/aufgabe6Queue2";
    private static final String USER = "guest";
    private static final String PASSWORD = "guest";

    private String text;
    private QueueConnectionFactory factory;
    private Queue queue;

    public EchoClient(String text) throws NamingException, JMSException {
        this.text = text;
        Context ctx = getInitialContext();
        factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
        queue = (Queue) ctx.lookup(DESTINATION);
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
        String text = "Text";
        EchoClient client = new EchoClient(text);
        client.process();
    }

    public void process() throws JMSException {
        QueueConnection connection = null;
        QueueSession session = null;
        TemporaryQueue tempQueue = null;
        QueueSender sender = null;
        QueueReceiver receiver = null;

        try {
            connection = factory.createQueueConnection(USER, PASSWORD);
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            // temporäre Queue für die Antwort erzeugen
            tempQueue = session.createTemporaryQueue();

            sender = session.createSender(queue);
            receiver = session.createReceiver(tempQueue);
            connection.start();

            TextMessage nachricht = session.createTextMessage();
            nachricht.setText(text);
            nachricht.setJMSReplyTo(tempQueue);
            sender.send(nachricht);
            System.out.println("auf antwort warten");
            // auf Antwort warten
            TextMessage antwort = (TextMessage) receiver.receive();

            System.out.println(antwort.getText());
        } finally {
            sender.close();
            receiver.close();
            tempQueue.delete();
            session.close();
            connection.close();
        }
    }
}
