package eu.ase.c03;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import eu.ase.c04.ZoomService;

public class ZoomMDB implements MessageListener {

    public static void main(String[] args) {
        String brokerURL = "tcp://c02:61616"; // IP broker ActiveMQ
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = null;
        System.setProperty("java.rmi.server.useCodebaseOnly", "true");
System.out.println("java.rmi.server.useCodebaseOnly: " + System.getProperty("java.rmi.server.useCodebaseOnly"));


        try {
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("ImageTopic");
            MessageConsumer consumer = session.createConsumer(topic);

            consumer.setMessageListener(new ZoomMDB());
            System.out.println("Listening for messages on topic: ImageTopic");

            synchronized (ZoomMDB.class) {
                ZoomMDB.class.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof BytesMessage) {
                BytesMessage bytesMessage = (BytesMessage) message;

                byte[] imageBytes = new byte[(int) bytesMessage.getBodyLength()];
                bytesMessage.readBytes(imageBytes);
                double zoomFactor = bytesMessage.getDoubleProperty("zoomFactor");

                System.out.println("Received image with zoom factor: " + zoomFactor);
                System.out.println("Image bytes length: " + imageBytes.length);
System.out.println("Attempting RMI lookup...");
System.out.println("java.rmi.server.useCodebaseOnly: " + System.getProperty("java.rmi.server.useCodebaseOnly"));
System.out.println("ZoomService class is present: " + (ZoomService.class != null));

                // Conectare la RMI server C04
                Registry regC04 = LocateRegistry.getRegistry("c04", 1099);
                ZoomService zoomServiceC04 = (ZoomService) regC04.lookup("ZoomService");

                // Procesare imagine pe C04
                byte[] processedImageC04 = zoomServiceC04.zoomImage(imageBytes, zoomFactor);
                System.out.println("Image processed by C04.");
                System.out.println("Image processed by C04. Processed image bytes length: " + processedImageC04.length);
System.out.println("Attempting RMI lookup...");
System.out.println("java.rmi.server.useCodebaseOnly: " + System.getProperty("java.rmi.server.useCodebaseOnly"));
System.out.println("ZoomService class is present: " + (ZoomService.class != null));

                // Conectare la RMI server C05
                Registry regC05 = LocateRegistry.getRegistry("c05", 1099);
                ZoomService zoomServiceC05 = (ZoomService) regC05.lookup("ZoomService");

                // Procesare imagine pe C05
                byte[] processedImageC05 = zoomServiceC05.zoomImage(processedImageC04, zoomFactor);
                System.out.println("Image processed by C05.");
                // ======================
            // 3) TRIMITERE LA C06
            // ======================
            String c06Url = "http://c06:3000/images"; // URL-ul pentru C06
            java.net.URL url = new java.net.URL(c06Url);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");

            // Trimitem byte[] direct in corpul request-ului
            try (java.io.OutputStream os = conn.getOutputStream()) {
                os.write(processedImageC05);
            }

            
            int responseCode = conn.getResponseCode();
            System.out.println("Response code from c06 = " + responseCode);
            if (responseCode == 200) {
                java.io.InputStream is = conn.getInputStream();
                java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
                String responseBody = s.hasNext() ? s.next() : "";
                s.close();

                // Extragem ID-ul din raspunsul JSON
                String idStr = responseBody.replace("{\"id\":", "")
                        .replace("}", "")
                        .trim();

                
                String c01Url = "http://c01:8080/notifyImageReady?id=" + idStr;
                try {
                    java.net.URL c01notifyUrl = new java.net.URL(c01Url);
                    java.net.HttpURLConnection c01conn = (java.net.HttpURLConnection) c01notifyUrl.openConnection();
                    c01conn.setRequestMethod("GET");
                    int c01respCode = c01conn.getResponseCode();
                    System.out.println("Notified C01, response code = " + c01respCode);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Error from C06. HTTP code: " + responseCode);
            }
            } else {
                System.out.println("Received non-image message.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
