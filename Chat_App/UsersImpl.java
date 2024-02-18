import java.util.ArrayList;
import java.util.List;

public class UsersImpl implements Users_itf{
    private List<String> connectedUsers;

    public UsersImpl(){
        connectedUsers = new ArrayList<String>();
    }

    private Boolean isConnected(String name){
        return connectedUsers.contains(name);
    }

    @Override
    public synchronized Boolean login(String name) {
        if (isConnected(name))
            return false;
        else {
            connectedUsers.add(name);
            return true;
        }
    }

    @Override
    public synchronized void logout(String name) {
        connectedUsers.remove(name);
    }


}