package ana1;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class EchoServer {
    private static final String DESTINATION = "queue/aufgabe6Queue2";
    private static final String USER = "guest";
    private static final String PASSWORD = "guest";

    private QueueConnectionFactory factory;
    private Queue queue;

    public EchoServer() throws NamingException, JMSException {
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
        EchoServer server = new EchoServer();
        server.process();
    }

    public void process() throws JMSException {
        QueueConnection connection = factory.createQueueConnection(USER, PASSWORD);
        QueueSession session = connection.createQueueSession(
            false, Session.AUTO_ACKNOWLEDGE);
        QueueReceiver receiver = session.createReceiver(queue);
        connection.start();
        System.out.println("EchoServer gestartet ...");

        while (true) {
            TextMessage nachricht = (TextMessage) receiver.receive();
            String text = nachricht.getText();
            Queue tempQueue = (Queue) nachricht.getJMSReplyTo();
            TextMessage antwort = session.createTextMessage();
            antwort.setText(text + "zurück");
            QueueSender sender = session.createSender(tempQueue);
            sender.send(antwort);
        }
    }
}
