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

    //"startar" servern
    public void start() {
        try {
            serverSocket = new ServerSocket(port); //försöker skapa en serversocket som lyssnar på en specifik port
        } catch (IOException e) {
            e.printStackTrace();
        }
        new ServerHandler(serverSocket).start();
    }

    public void getLogsBetween(String fromDate, String endDate) {
        LocalDateTime from = LocalDate.parse(fromDate, DateTimeFormatter.ISO_LOCAL_DATE).atTime(0,0);
        LocalDateTime until = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE).atTime(23, 59);

        String logs = getLogs().getTheseLogs(from, until);

        serverUI.showSelectedLogs(logs);
    }

    //hanterar servern
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

    private void handleUnsentMessages(User user) {
        ArrayList<ChatMessage> unsent = unsentMessages.get(user);
        if(unsent != null){
            for(ChatMessage msg : unsent){
                clients.get(user).sendMessageToClient(new NetworkMessage("chatmessage", msg));
            }
        }
    }

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
