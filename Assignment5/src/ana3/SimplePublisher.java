package ana3;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import java.util.Hashtable;

public class SimplePublisher {
    public static void main(String[] args) {
        try {
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
            env.put(Context.PROVIDER_URL, "jnp://infpro52.reutlingen-university.de:1099");
            env.put(Context.SECURITY_PRINCIPAL, "guest");
            env.put(Context.SECURITY_CREDENTIALS, "quest");
            System.out.println("test env");
            Context ctx = new InitialContext(env);
            System.out.println("test inizial");

            //Найдем ConnectionFactory
            //В случае pub-sub это TopicConnectionFactory
            TopicConnectionFactory tcf =
                (TopicConnectionFactory) ctx.lookup("ConnectionFactory");



            System.out.println("test factory");
            //Создадим TopicConnection
            TopicConnection con = tcf.createTopicConnection();
            //И затем TopicSession
            TopicSession session =
                con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            //Найдем topic в дереве JNDI
            //или создадим новый
            Topic topic = null;
            try {
                topic = (Topic) ctx.lookup("aufgabe5Topic");
                System.out.println("test topic");
            } catch (NameNotFoundException nnfe) {
                topic = session.createTopic("aufgabe5Topic");
                ctx.bind("aufgabe5Topic", topic);
            }
            TextMessage textMessage = session.createTextMessage();
            //Producer, в данном случае TopicPublisher
            TopicPublisher publisher = session.createPublisher(topic);
            con.start();
            long sendInterval;
            try {
                sendInterval = Long.parseLong(args[0]);
            } catch (Exception rae) {
                sendInterval = 1000;
            }
            for (int i = 0; i < 1000; i++) {
                textMessage.setText("My topic message number" + i);
                //Посылаем сообщение
                publisher.publish(textMessage);
                try {
                    Thread.sleep(sendInterval);
                } catch (Exception ie) {
                    ie.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

