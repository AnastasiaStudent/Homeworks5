package ana3;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class Subscriber implements MessageListener {
    private static final String DESTINATION = "topic/aufgabe5Topic";
    private static final String USER = "guest";
    private static final String PASSWORD = "guest";

    private TopicConnectionFactory factory;
    private Topic topic;
    private TopicConnection connection;
    private TopicSession session;
    private TopicSubscriber subscriber;

    public Subscriber() throws NamingException, JMSException {
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

    public void subscribe() throws JMSException {
        connection = factory.createTopicConnection(USER, PASSWORD);
        session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        subscriber = session.createSubscriber(topic);
        subscriber.setMessageListener(this);
        connection.start();
    }

    public void close() throws JMSException {
        subscriber.close();
        session.close();
        connection.close();
    }

    public void onMessage(Message message) {
        try {
            if (message instanceof MapMessage) {
                MapMessage mapMessage = (MapMessage) message;
                System.out.println(mapMessage.getString("Time"));
                System.out.println(mapMessage.getDouble("Value"));
                System.out.println();
            }
        } catch (JMSException e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) throws Exception {
        long time = Long.parseLong("20000");
        Subscriber sub = new Subscriber();
        sub.subscribe();
        Thread.sleep(time);
        sub.close();
    }
}
