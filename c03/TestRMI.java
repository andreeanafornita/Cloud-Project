import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestRMI {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("172.21.0.5", 1099);
            String[] services = registry.list();
            System.out.println("Available services:");
            for (String service : services) {
                System.out.println(" - " + service);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
