package Model;
import Controller.ClientController;

import java.io.*;
import java.net.Socket;

//
public class Client {
    private User thisUser;
    private Socket socket;
    private String host;
    private int port;
    private  Buffer<NetworkMessage> buffer;
    private ClientController controller;

    //konstruktor
    public Client(String host, int port, Buffer<NetworkMessage> buffer, ClientController controller) {
        this.host = host;
        this.port = port;
        this.buffer = buffer;
        this.controller = controller;
    }
    


    //connectar till server
    public void connect(User user) { //startar ny client thread
        this.thisUser = user;

        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendUserInfo(); //skickar userinfo

        //TODO: Check if logged in properly before continue (reply from server)

        new ClientThreadOutput(socket).start(); //tråd för output

        new ClientThreadInput(socket).start(); //tråd för input
    }

    public void sendNetworkMessage(NetworkMessage message) {
        buffer.put(message);
    }

    public void logout() throws IOException {
        NetworkMessage networkMessage = new NetworkMessage("logout", thisUser);
        sendNetworkMessage(networkMessage);
    }

    //CLASS FOR CLIENT THREAD, Output
    public class ClientThreadOutput extends Thread{
        private final Socket socket;

        //constructor
        public ClientThreadOutput(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try(ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                while (!Thread.interrupted()){
                    NetworkMessage networkMessage = buffer.get();
                    System.out.println("I Client: Sending to server: " + networkMessage.getTypeOfMsg());
                    if(socket.isConnected()) {
                        oos.writeObject(networkMessage);
                        oos.flush();
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //CLASS FOR CLIENT THREAD, Input
    public class ClientThreadInput extends Thread {
        private final Socket socket;

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
        System.out.println(networkMessage.getTypeOfMsg());

        switch (networkMessage.getTypeOfMsg()) {
            case "logged_in_list" -> {
                int numberOfUsers = (int) networkMessage.getData();
                updateLoggedInUsers(ois, numberOfUsers);
            }
            case "chatmessage" -> {
                ChatMessage chatMessage = (ChatMessage) networkMessage.getData();
                controller.receiveChatMessageFromServer(chatMessage);
            }
            case "exit" -> {
                System.exit(0);
            }
        }

    }

    /**
     * Loops through the users, creates a networkMessage and if the
     * username does not equal our username it adds the user to the
     * logged-in list by calling addNewLoggedInUser from controller.
     * @param ois
     * @param numberOfUsers
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void updateLoggedInUsers(ObjectInputStream ois, int numberOfUsers) throws IOException,
            ClassNotFoundException {
        controller.emptyLoggedInUsers();
        for(int i = 0; i < numberOfUsers; i++){
            NetworkMessage networkMessage = (NetworkMessage) ois.readObject();
            User user = (User)networkMessage.getData();
            if(!user.getUsername().equals(thisUser.getUsername())) {
                controller.addNewLoggedInUser(user);
            }
        }
        controller.updateLoggedInUsersView();
    }

    public void sendUserInfo() {
        NetworkMessage networkMessage = new NetworkMessage("userinfo", thisUser); //"presenterar" sig för server
        sendNetworkMessage(networkMessage); //skickar networkmessage
    }


}
