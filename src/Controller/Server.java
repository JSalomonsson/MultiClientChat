package Controller;
import Model.*;
import View.Server.ServerUI;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class controls the server
 */
public class Server {
    private ServerSocket serverSocket;
    private final int port;
    private final UnsentMessages unsentMessages;

    private final ClientManager clients;

    private final Logs logs;

    ServerUI serverUI;

    public Server(int port){
        serverUI = new ServerUI(this);
        logs = new Logs(this);
        logs.load();
        clients = new ClientManager();
        unsentMessages = new UnsentMessages();
        this.port = port;
    }

    /**
     * Method to start the server
     */
    public void start() {
        try {
            serverSocket = new ServerSocket(port); //försöker skapa en serversocket som lyssnar på en specifik port
        } catch (IOException e) {
            e.printStackTrace();
        }
        new ServerHandler(serverSocket).start();
    }

    /**
     * Method to get the server log.
     * You enter the start date and the end date
     * for the period of which you wish to see the log.
     */
    public void getLogsBetween(String fromDate, String endDate) {
        LocalDateTime from = LocalDate.parse(fromDate, DateTimeFormatter.ISO_LOCAL_DATE).atTime(0,0);
        LocalDateTime until = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE).atTime(23, 59);

        String logs = getLogs().getTheseLogs(from, until);

        serverUI.showSelectedLogs(logs);
    }

    /**
     * Class that handles the server.
     */
    public class ServerHandler extends Thread{
        ServerSocket serverSocket;

        public ServerHandler(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            Socket clientSocket;
            try {
                while (!Thread.interrupted()) {
                    logs.add("Server waiting for client connection");
                    clientSocket = serverSocket.accept(); //accept() ligger och lyssnar på anslutning och när en anslutning kommer blir clienSocket tilldelad den
                    new ServerClientHandler().connect(Server.this, clientSocket); //skickar in anslutningen, sparkar igång en tråd på "hittade anslutningen" för att hantera kommunikationen
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method that sends the unsent message to
     * the receiving user when he/she comes online.
     */
    private void handleUnsentMessages(User user) {
        ArrayList<ChatMessage> unsent = unsentMessages.get(user);
        if(unsent != null){
            for(ChatMessage msg : unsent){
                clients.get(user).sendMessageToClient(new NetworkMessage("chatmessage", msg));
            }
        }
    }

    /**
     * Handles the different kinds of incoming messages.
     * If it is a message of the type "userinfo" a user is created
     * and added to the hashmap of online users by using and then
     * the sendLoggedInUsers method is called to send the user the
     * online users, then the handleUnsentMessages method is called to
     * send any messages that the user was sent while he/she was offline.
     * If it is a "chatmessage" we create a new ChatMessage and calls the
     * method that handles the incoming chat messages.
     * If it is a "logout" message we create a User and calls the method
     * to remove the user from the hashmap of online users. Then the
     * method for updating the online users is called and after that
     * we create a new Network message of the type "exit".
     */
    public void handleIncomingMessage(NetworkMessage networkMessage, ServerClientHandler clientHandler){
        switch (networkMessage.getTypeOfMsg()) {
            case "userinfo" -> {
                User user = (User) networkMessage.getData(); //typad till user pga kommer som "Object"
                clients.add(user, clientHandler);//key, value, lägger till en användare
                clients.sendLoggedInUsers();
                handleUnsentMessages(user);
                logs.add("User " + user.getUsername() + " logged in.");
            }
            case "chatmessage" -> {
                ChatMessage chatMessage = (ChatMessage) networkMessage.getData(); //packar upp nätverksmeddelandet
                handleIncomingChatMessage(networkMessage, chatMessage.getReceivers(), chatMessage.getSender());
            }
            case "logout" -> {
                User user = (User) networkMessage.getData();
                clients.remove(user);
                clients.sendLoggedInUsers();
                clientHandler.sendMessageToClient(new NetworkMessage("exit", null));
                logs.add("User " + user.getUsername() + " logged out!");
            }
        }
    }

    /**
     * Method that handles the incoming ChatMessages.
     * For loops that goes through the receivers and if
     * the receiver is online the message is sent. If
     * the receiver is offline the message is stored
     * and delivered when the user goes online.
     */
    private void handleIncomingChatMessage(NetworkMessage networkMessage, ArrayList<User> receivers, User sender) {
        for (User receiver: receivers) { //tar receivers och lägger i receiver-stringen
            ServerClientHandler clientHandler = clients.get(receiver);
            if(clientHandler != null) {
                logs.add(sender.getUsername() + " sent message to " + receiver.getUsername());
                clientHandler.sendMessageToClient(networkMessage);
            }else{
                logs.add("Message for " + receiver.getUsername() + " will be sent when he/she goes online.");
                unsentMessages.put(receiver, (ChatMessage) networkMessage.getData());
            }
        }
    }

    public Logs getLogs() {
        return logs;
    }
}
