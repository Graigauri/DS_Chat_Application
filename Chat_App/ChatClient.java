import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient {
    public static void main(String[] args) {
        try {
            
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            Registry registry = LocateRegistry.getRegistry("localhost");
            Users_itf users = (Users_itf) registry.lookup("UserService");
            Messages_itf messages = (Messages_itf) registry.lookup("MessageService");

            String clientName;

            System.out.println("Available Options:");
            System.out.println("'-quit':quit the application");
            System.out.println("'-hist': look at the chat history");
            System.out.println("");

            System.out.println("Please input your username: ");
            while (true) {
                String s = stdIn.readLine();
                if (s.equals("-quit"))
                    System.exit(0);
                else if (users.login(s)) {
                    clientName = new String(s);
                    break;
                }else
                    System.out.println("Username taken! Please try other username: ");
            }

            ClientMessageReceive_itf ClientMessageReceive = new ClientMessageReceiveImpl();
            messages.registerReceiver(ClientMessageReceive);

            messages.send((clientName + " joined"), "SERVER");

            System.out.println("Welcome to the chat!");
            System.out.println("");
            printHistory(messages.getHistory());

            while (true) {
                String s = stdIn.readLine();
                if (s.equals("-quit"))
                    break;
                else if (s.equals("-hist"))
                    printHistory(messages.getHistory());
                else
                    messages.send(s, clientName);
            }
            
            users.logout(clientName);
            messages.send((clientName + " left"), "SERVER");
        } catch (Exception e) {
			System.err.println("Error with Client: " + e);
        }
    }

    static void printHistory(List<String> history) {
        System.out.println("");
        for(int i = 0; i < history.size(); i++) {
            System.out.println(history.get(i));
        }
        System.out.println("");
    }
}