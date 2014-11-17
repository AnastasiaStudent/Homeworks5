package ana1;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class Consumer2 implements MessageListener {
    private static final String DESTINATION = "queue/myQueue1";
    private static final String USER = "guest";
    private static final String PASSWORD = "guest";

    private QueueConnection connection;
    private QueueSession session;
    private QueueReceiver receiver;

    public Consumer2() throws NamingException, JMSException {
        // JNDI-Kontext erzeugen
        Context ctx = getInitialContext();

        // ConnectionFactory �ber Namensdienst auslesen
        QueueConnectionFactory factory =
            (QueueConnectionFactory) ctx.lookup("ConnectionFactory");

        // Zieladresse �ber Namensdienst auslesen
        Queue queue = (Queue) ctx.lookup(DESTINATION);

        // Verbindung aufbauen
        connection = factory.createQueueConnection(USER, PASSWORD);

        // Session erzeugen
        session = connection.createQueueSession(
            false, Session.AUTO_ACKNOWLEDGE);

        // Empf�nger erzeugen
        receiver = session.createReceiver(queue);

        // MessageListener setzen
        receiver.setMessageListener(this);

        // Empfang von Nachrichten starten
        connection.start();
    }

    public static void main(String[] args) throws Exception {
        long timeout = Long.parseLong(args[0]);
        Consumer2 consumer = new Consumer2();
        Thread.sleep(timeout);
        consumer.close();
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

    // Nachrichten werden im Push-Verfahren empfangen
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println(textMessage.getText());
            }
        } catch (JMSException e) {
            System.err.println(e);
        }
    }

    // Ressourcen freigeben
    public void close() throws JMSException {
        receiver.close();
        session.close();
        connection.close();
    }
}
