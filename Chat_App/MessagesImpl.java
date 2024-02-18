import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.rmi.RemoteException;

public class MessagesImpl implements Messages_itf {
    private File saveFile;
    private List<String> history;
    private List<ClientMessageReceive_itf> receivers;
    

    public MessagesImpl() {
        history = new ArrayList<String>();
        receivers = new ArrayList<>();
    }

    @Override
    public synchronized void registerReceiver(ClientMessageReceive_itf receiver) {
        receivers.add(receiver);
    }

    // Load chat history file
    public void loadHistory() throws IOException {
        saveFile = new File("./chat_history.txt");
        
        if (saveFile.exists()) {    // if file alreday exists -> load history from it
            FileReader fr = new FileReader(saveFile);
            BufferedReader br = new BufferedReader(fr);
            String str;
            while ((str = br.readLine()) != null) {
                history.add(str);
            }
            fr.close();
        }else                       // otherwise create a new file
            saveFile.createNewFile();
    }

    // Update the chat history
    public void updateHistory(String str, String clientName) throws IOException {
        // Get current date and time
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = sdf.format(date);

        // Add date, time and name to the message
        String newStr = "[" + time + "] " + clientName + " : " + str;

        // add message to history array
        history.add(newStr);

        // add message to chat history file
        FileWriter fw = new FileWriter(saveFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(newStr + "\n");
        bw.close();
    }

    // Get content of the history
    @Override
    public synchronized List<String> getHistory() {
        return history;
    }

   // Send a message
   @Override
    public synchronized void send(String str, String clientName) {
        try {
            updateHistory(str, clientName);
        } catch (IOException e) {
            System.err.println("Error with updating the history :" + e);
            e.printStackTrace();
        }

        String strMessage = clientName + " : " + str;
        broadcast(strMessage);
    }

    // Add a method to broadcast messages to all connected users
    public synchronized void broadcast(String message) {
        for (ClientMessageReceive_itf receiver : receivers) {
            try {
                receiver.receiveMessage(message);
            } catch (RemoteException e) {
                System.err.println("Error with broadcasting message: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}