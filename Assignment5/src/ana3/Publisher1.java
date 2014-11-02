package ana3;


import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Publisher1 {
    private static final String DESTINATION = "topic/aufgabe5Topic";
    private static final String USER = "guest";
    private static final String PASSWORD = "guest";

    private TopicConnectionFactory factory;
    private Topic topic;

    public Publisher1() throws NamingException, JMSException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
            "org.jnp.interfaces.NamingContextFactory");
        env.put(Context.PROVIDER_URL, "jnp://infpro52.reutlingen-university.de:1099");
        env.put(Context.SECURITY_PRINCIPAL, "guest");
        env.put(Context.SECURITY_CREDENTIALS, "quest");

        Context ctx = new InitialContext(env);
        factory = (TopicConnectionFactory) ctx.lookup("ConnectionFactory");
        topic = (Topic) ctx.lookup(DESTINATION);
    }

    public void process() throws JMSException {
        TopicConnection connection = factory.createTopicConnection(USER, PASSWORD);
        TopicSession session = connection.createTopicSession(
            false, Session.AUTO_ACKNOWLEDGE);
        TopicPublisher publisher = session.createPublisher(topic);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        while (true) {
            String time = formatter.format(new Date());
            double value = Math.random() * 100;
            MapMessage message = session.createMapMessage();
            message.setString("Time", time);
            message.setDouble("Value", value);
            publisher.publish(message);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Publisher1 pub = new Publisher1();
        pub.process();
    }
}
