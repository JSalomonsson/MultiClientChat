package Model;
import Controller.ClientController;

import java.io.*;
import java.net.Socket;

//
public class Client implements MessageListener{
    private User user;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String username;
    private String host;
    private int port;
    private Buffer<NetworkMessage> buffer;
    private ClientController controller;
    private ChatMessage message;

    //konstruktor
    public Client(String host, int port, Buffer<NetworkMessage> buffer, ClientController controller) {
        this.host = host;
        this.port = port;
        this.buffer = buffer;
        this.controller = controller;
    }


    //connectar till server
    public void connect(User user) { //startar ny client thread
        this.user = user;

        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new ClientThreadOutput(socket).start(); //tråd för output

        new ClientThreadInput(socket).start(); //tråd för input
    }


    /*
     /*public void sendMessage(){
        try{
            OOS.write(username);
            OOS.newLine();
            OOS.flush();

            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected()){
                String messageToSend = scanner.nextLine();
                OOS.write(username + ": " + messageToSend);
                OOS.newLine();
                OOS.flush();

            }
        } catch (IOException e){
            closeEverything(socket, OOS, OIS);
        }
    }

    public void run(){
        try {
            while (!Thread.interrupted()){
                String request = ois.readUTF();
                if(request.equals("getUser")){
                    oos.writeObject(user);
                    oos.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //vilken tråd ska den här tillhöra?
     */ //VAD GÖR DETTA KODBLOCKET

    @Override
    public void setMessage(ChatMessage message) {
        this.message = message;
    }

    public void sendNetworkMessage(NetworkMessage message) {
        buffer.put(message);
    }

    //CLASS FOR CLIENT THREAD, Output
    public class ClientThreadOutput extends Thread{
        private Socket socket;

        //constructor
        public ClientThreadOutput(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try(ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                   sendUserInfo(); //skickar userinfo
                while (!Thread.interrupted()){
                    oos.writeObject(buffer.get());
                    System.out.println("SENDING message to server");
                    oos.flush();

                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    //CLASS FOR CLIENT THREAD, Input
    public class ClientThreadInput extends Thread {
        private Socket socket;
        private ChatMessage msg;

        //konsturktor
        public ClientThreadInput(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            try(ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                while (!Thread.interrupted()){
                   receiveMessageFromServer(ois);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void receiveMessageFromServer(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        NetworkMessage networkMessage = (NetworkMessage) ois.readObject();

        switch (networkMessage.getTypeOfMsg()){
            case "chatmessage":
                ChatMessage chatMessage = (ChatMessage) networkMessage.getData();
                controller.receiveChatMessageFromServer(chatMessage);
                break;
        }

    }

    public void setNewChatMessage(ChatMessage msg){
        System.out.println("New message set: " + msg.getMessageText());
    }

    public void sendUserInfo() throws IOException {
        NetworkMessage networkMessage = new NetworkMessage("userinfo", this.user); //"presenterar" sig för server
        sendNetworkMessage(networkMessage); //skickar networkmessage
    }


}
