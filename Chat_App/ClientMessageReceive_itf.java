import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientMessageReceive_itf extends Remote {
    void receiveMessage(String message) throws RemoteException;
}