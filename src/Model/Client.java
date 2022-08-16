package Model;
import Controller.ClientController;

import java.io.*;
import java.net.Socket;

/**
 * The class for the Client.
 */
public class Client {
    private User thisUser;
    private Socket socket;
    private String host;
    private int port;
    private  Buffer<NetworkMessage> buffer;
    private ClientController controller;

    public Client(String host, int port, Buffer<NetworkMessage> buffer, ClientController controller) {
        this.host = host;
        this.port = port;
        this.buffer = buffer;
        this.controller = controller;
    }

    /**
     * Method that connects the user to the server and
     * starts a thread for output and a thread for input.
     */
    //connectar till server
    public void connect(User user) { //startar ny client thread
        this.thisUser = user;

        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendUserInfo(); //skickar userinfo

        new ClientThreadOutput(socket).start(); //tråd för output

        new ClientThreadInput(socket).start(); //tråd för input
    }

    /**
     * Puts a NetworkMessage in the buffer.
     */
    public void sendNetworkMessage(NetworkMessage message) {
        buffer.put(message);
    }

    /**
     * Method that runs when user signs out.
     */
    public void logout() throws IOException {
        NetworkMessage networkMessage = new NetworkMessage("logout", thisUser);
        sendNetworkMessage(networkMessage);
    }

    /**
     * Class for the output part of the program.
     */
    //CLASS FOR CLIENT THREAD, Output
    public class ClientThreadOutput extends Thread{
        private final Socket socket;

        //constructor
        public ClientThreadOutput(Socket socket) {
            this.socket = socket;
        }

        /**
         * Run method that tries to create a new ObjectOutputStream and
         * as long as the thread isn't interrupted it gets a network message
         * from the buffer and if the socket is connected it writes the
         * message to the output stream and flushes the stream.
         */
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

    /**
     * Class for the input part of the program.
     */
    //CLASS FOR CLIENT THREAD, Input
    public class ClientThreadInput extends Thread {
        private final Socket socket;

        //konsturktor
        public ClientThreadInput(Socket socket){
            this.socket = socket;
        }

        /**
         * Run method that tries to create an ObjectInputStream and as
         * long as the thread isn't interrupted it calls the recieveMessageFromServer
         * method.
         */
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

    /**
     * Method that takes and ObjectInputStream as a parameter.
     * Creates a NetworkMessage by reading the object in the
     * input stream. Then we check what type of message it is. If
     * it is a "logged_in_list" message we get the number of users from
     * the networkMessage and call the updateLoggedInUsers method.
     * If it is a "chatmessage" we create a ChatMessage from the data in
     * the networkMessage and calls the receiveChatmEssageFromServer method
     * from the client controller class.
     * If it is a "exit" message we exit the system.
     */
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
