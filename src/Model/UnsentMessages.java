package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that handle messages that can't be delivered
 * because the receiving user is offline.
 */
public class UnsentMessages {

    private final HashMap<User, ArrayList<ChatMessage>> unsent;

    public UnsentMessages() {
        unsent = new HashMap<>();
    }

    /**
     * Method that puts the messages that hasn't been
     * sent to the unsent hashmap.
     */
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
}
