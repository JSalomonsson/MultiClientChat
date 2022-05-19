package Controller;
import Model.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


//den här klassen sköter själva servern
public class Server {
    private Socket socket;
    private ServerSocket serverSocket;
    private int port;
    private String host;
    private Buffer buffer;

    private ClientManager clients;

    HashMap<String, NetworkMessage> outgoingMessages = new HashMap<>(); //ha smap för meddelande som väntar på att mottagare ska gå online

    public Server(String host, int port){
        clients = new ClientManager();
        this.port = port;
        this.host = host;

        buffer = new Buffer();
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

    //hanterar servern
    public class ServerHandler extends Thread{
        ServerSocket serverSocket;

        public ServerHandler(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            Socket clientSocket = null;
            try {
                while (!Thread.interrupted()) {
                    System.out.println("Waiting for client connection");
                    clientSocket = serverSocket.accept(); //accept() ligger och lyssnar på anslutning och när en anslutning kommer blir clienSocket tilldelad den
                    new ServerClientHandler().connect(Server.this, clientSocket); //skickar in anslutningen, sparkar igång en tråd på "hittade anslutningen" för att hantera kommunikationen
                    //skapa en till clienthandler
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleIncomingMessage(NetworkMessage networkMessage, ServerClientHandler clientHandler){
        switch (networkMessage.getTypeOfMsg()){
            case "userinfo":
                User user = (User) networkMessage.getData(); //typad till user pga kommer som "Object"
                clients.add(user, clientHandler);//key, value, lägger till en användare
                clients.sendLoggedInUsers();
                System.out.println("Added user " + user.getUsername() + "to hashmap");
                break;
            case "chatmessage":
                ChatMessage chatMessage = (ChatMessage) networkMessage.getData(); //packar upp nätverksmeddelandet
                handleIncomingChatMessage(networkMessage, chatMessage.getReceivers());
                break;
        }
    }

    private void handleIncomingChatMessage(NetworkMessage networkMessage, ArrayList<String> receivers) {

//        for (String receiver: receivers) { //tar receivers och lägger i i receiver-stringen
//            ServerClientHandler clientHandler = clients.computeIfAbsent(receiver, username -> {
//                ServerClientHandler result = new ServerClientHandler();
//                return result;
//            });
//            sendNetworkMessage(networkMessage, clientHandler);
//        }
    }

    private void sendNetworkMessage(NetworkMessage networkMessage, ServerClientHandler clientHandler) {
        clientHandler.sendMessageToClient(networkMessage);
    }

}
