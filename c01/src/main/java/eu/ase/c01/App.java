package eu.ase.c01;

import io.javalin.Javalin;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.io.InputStream;

public class App {
    private static ConnectionFactory connectionFactory;

    public static void main(String[] args) {
        String brokerURL = "tcp://c02:61616"; 
        connectionFactory = new ActiveMQConnectionFactory(brokerURL);

        
        Javalin app = Javalin.create().start(8080);
        app.get("/", ctx -> {
            ctx.result("Serverul C01 este activ! Accesați /upload pentru a trimite imagini spre procesare.");
        });

       
        app.post("/upload", ctx -> {
            try {
                String zoomParam = ctx.formParam("zoomFactor");
                double zoomFactor = Double.parseDouble(zoomParam);
                System.out.println("Received zoom factor: " + zoomFactor);
                
                InputStream fileInput = ctx.uploadedFile("imageFile").content();
                byte[] imageBytes = fileInput.readAllBytes();
                System.out.println("Received image bytes length: " + imageBytes.length);
                
                publishToJMS(imageBytes, zoomFactor);
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(500).result("A aparut o eroare: " + e.getMessage());
            }
        });
        //////////////////////////////////
               
        app.get("/notifyImageReady", ctx -> {
            String id = ctx.queryParam("id");
            if (id == null) {
                ctx.status(400).result("Missing 'id' param");
                return;
            }
        });
    }
    ///////////////////////////////////
    

   private static void publishToJMS(byte[] imageBytes, double zoomFactor) throws JMSException {
    Connection connection = null;
    Session session = null;
    try {
        
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        
        Topic topic = session.createTopic("ImageTopic");
        MessageProducer producer = session.createProducer(topic);

        
        BytesMessage message = session.createBytesMessage();
        message.writeBytes(imageBytes);
        message.setDoubleProperty("zoomFactor", zoomFactor);

       
        producer.send(message);
        System.out.println("Message published to JMS.");
    } finally {
        
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}

}
