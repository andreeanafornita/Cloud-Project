package eu.ase.c04;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ZoomService extends Remote {
    long serialVersionUID = 1L;
    byte[] zoomImage(byte[] imageBytes, double zoomFactor) throws RemoteException;
}
