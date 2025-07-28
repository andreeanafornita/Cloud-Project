import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestRMI {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            String[] services = registry.list();
            for (String service : services) {
                System.out.println("Service: " + service);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
