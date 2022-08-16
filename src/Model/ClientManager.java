package Model;

import Controller.ServerClientHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that helps manage the clients,
 * containes a HashMap with users.
 */
public class ClientManager {
    HashMap<User, ServerClientHandler> clients; //map över anslutna användare

    public ClientManager(){
        clients = new HashMap<>();
    }

    /**
     * Used to add users to the hashmap.
     */
    public synchronized void add(User user, ServerClientHandler clientHandler) {
        clients.put(user, clientHandler);
    }

    public synchronized ServerClientHandler get(User user){
        return clients.get(user);
    }

    /**
     * Loops through logged-in users and sends them to the client
     */
    public synchronized void sendLoggedInUsers() {
        for(Map.Entry<User, ServerClientHandler> clientToSendTo : clients.entrySet()){
            clientToSendTo.getValue().sendMessageToClient(new NetworkMessage("logged_in_list", clients.size()));
            for (Map.Entry<User, ServerClientHandler> clientToSend : clients.entrySet()) {
                clientToSendTo.getValue().sendMessageToClient(new NetworkMessage("userinfo", clientToSend.getKey()));
            }
        }
    }

    /**
     * Used to remove a user from the hashmap.
     */
    public void remove(User user) {
        clients.remove(user);
    }
}
