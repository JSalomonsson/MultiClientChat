package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class UnsentMessages {

    private final HashMap<User, ArrayList<ChatMessage>> unsent;

    public UnsentMessages() {
        unsent = new HashMap<>();
    }

    public synchronized void put(User user,ChatMessage message) {
        ArrayList<ChatMessage> messageList = unsent.get(user);
        if(messageList != null){
            messageList.add(message);
        }else {
            messageList = new ArrayList<>();
            messageList.add(message);
            unsent.put(user, messageList);
        }
    }

    public synchronized ArrayList<ChatMessage> get(User user) {
        return unsent.get(user);
    }
// fler synchronized-metoder som behövs
}
