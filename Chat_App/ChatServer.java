import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatServer {
    public static void main(String[] args) {
        try {
            MessagesImpl messages = new MessagesImpl();
            Messages_itf messages_stub = (Messages_itf) UnicastRemoteObject.exportObject(messages, 0);

            UsersImpl users = new UsersImpl();
            Users_itf users_stub = (Users_itf) UnicastRemoteObject.exportObject(users, 0);

            Registry registry = LocateRegistry.getRegistry("localhost");
            registry.bind("MessageService", messages_stub);
            registry.bind("UserService", users_stub);

            messages.loadHistory();

            System.out.println("Server Ready");
        } catch (Exception e) {
            System.err.println("Error with server :" + e);
            e.printStackTrace();
        }
    }
}