import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientMessageReceiveImpl extends UnicastRemoteObject implements ClientMessageReceive_itf {

    protected ClientMessageReceiveImpl() throws RemoteException {
        super();
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println(message);
    }
}
