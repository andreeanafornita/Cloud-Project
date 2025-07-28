
package eu.ase.c04;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.imageio.ImageIO;

public class ZoomServer extends UnicastRemoteObject implements ZoomService {

    public ZoomServer() throws RemoteException {
        super();
    }

    @Override
    public byte[] zoomImage(byte[] imageBytes, double zoomFactor) throws RemoteException {
        try {
            System.out.println("Processing image with zoom factor: " + zoomFactor);

            
            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

            
            int newWidth = (int) (originalImage.getWidth() * zoomFactor);
            int newHeight = (int) (originalImage.getHeight() * zoomFactor);

         
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "bmp", baos);
            byte[] processedImage = baos.toByteArray();

            System.out.println("Image processed successfully.");
            return processedImage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error processing image", e);
        }
    }

    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "c04-container");
            LocateRegistry.createRegistry(1099);
            ZoomServer server = new ZoomServer();
            Naming.rebind("rmi://c04:1099/ZoomService", server);
            System.out.println("ZoomServer is running and awaiting connections...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
