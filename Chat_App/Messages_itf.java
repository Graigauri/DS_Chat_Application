import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Messages_itf extends Remote{
    public List<String> getHistory() throws RemoteException;
    public void send(String s, String clientName) throws RemoteException;
    public void registerReceiver(ClientMessageReceive_itf receiver) throws RemoteException;
}
