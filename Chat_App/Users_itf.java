import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Users_itf extends Remote {
    public Boolean login(String name) throws RemoteException;
    public void logout(String name) throws RemoteException;
}
