package Model;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
//TODO: ta bort?
//skickar ut meddelanden
//public class MessageServer {

    /*
    private Socket socket;
    private ServerSocket serverSocket;
    private String host;
    private int port;


    public MessageServer(String host, int port){
        this.host = host;
        this.port = port;

        try {
            serverSocket = new ServerSocket(port); //försöker skapa en serversocket som lyssnar på "port"
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(ChatMessage msg){
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new ServerHandler(msg).start(); //ny tråd
    }



    public class ServerHandler extends Thread{ //TRÅD, gör något med servern
        private ChatMessage msg;
        public ServerHandler() {
        } //konstruktor, den är tom

        public ServerHandler(ChatMessage msg) {
            this.msg = msg;
        }
        @Override
        public void run() {
            Socket clientSocket = null;
            //while hämtar ett meddelande ur messageBuffer med metoden get() och sparar till message-variabeln
            try {
                clientSocket = serverSocket.accept(); //accept() ligger och lyssnar på anslutning och när en anslutning kommer blir clienSocket tilldelad den
                new MessageClient(clientSocket, msg).start(); //skickar in anslutningen, sparkar igång en tråd på "hittade anslutningen" för att hantera kommunikationen
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




     */
//}
