package eu.ase.c02;

import org.apache.activemq.broker.BrokerService;

public class App {
    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName("localhost");
        broker.addConnector("tcp://0.0.0.0:61616");
        broker.start();
        System.out.println("ActiveMQ broker started and listening on tcp://0.0.0.0:61616");
 synchronized (App.class) {
            App.class.wait();
        }
    }
}
