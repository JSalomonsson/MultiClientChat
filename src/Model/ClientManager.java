package Model;

import Controller.ServerClientHandler;

import java.util.HashMap;
import java.util.Map;

public class ClientManager {
    HashMap<User, ServerClientHandler> clients; //map över anslutna användare

    public ClientManager(){
        clients = new HashMap<>();
    }


    public synchronized void add(User user, ServerClientHandler clientHandler) {
        clients.put(user, clientHandler);
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
}
