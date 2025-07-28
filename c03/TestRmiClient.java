import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import eu.ase.c04.ZoomService;
public class TestRmiClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("172.21.0.4", 1099);
            String[] services = registry.list();
            for (String service : services) {
                System.out.println("Service: " + service);
            }

            ZoomService service = (ZoomService) registry.lookup("ZoomService");
            System.out.println("ZoomService is available.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
