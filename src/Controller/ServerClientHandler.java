package Controller;

import Model.Buffer;
import Model.ChatMessage;
import Model.NetworkMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * Class that handles the input and
 * output from clients with the server.
 */
public class ServerClientHandler {
    private Server server;
    private Socket socket;
    private Buffer<NetworkMessage> buffer;

    /**
     * Connect method that starts the input and
     * output threads.
     */
    public void connect(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        buffer = new Buffer<>();

        new ClientInputThread().start();
        new ClientOutputThread().start();
    }


    /**
     * Class that handles the input from the client.
     */
    private class ClientInputThread extends Thread {

        @Override
        public void run() {
            try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                while (!Thread.interrupted()) {
                    if(socket.isConnected()) {
                        receiveMessageFromClient(ois); //tar emot meddelande
                    }
                }
            } catch (ClassNotFoundException | IOException ignore) {
            }
        }

    }

    /**
     * CLass that handles the output from the client.
     */
    private class ClientOutputThread extends Thread {

        @Override
        public void run() {
            try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                while (!Thread.interrupted()){
                    oos.writeObject(buffer.get());
                    oos.flush();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method that receives a message from the client from
     * an ObjectInputStream and creates a new network message which is
     * used as a parameter in the method that is called to sort the
     * different kinds of messages, handleIncomingMessage().
     */
    //denna metoden används för att sortera upp meddelande från clienten, t ex chattmeddelande, att någon stänger servern, går online etc.
    public void receiveMessageFromClient(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        NetworkMessage networkMessage = (NetworkMessage) ois.readObject();
        server.handleIncomingMessage(networkMessage, this);
    }

    /**
     * Method that is used to send a message to a client.
     * If it is a message of the type "chatmessage" we
     * also set the time that it was delivered to the receiver.
     */
    public void sendMessageToClient(NetworkMessage networkMessage) {
        if(networkMessage.getTypeOfMsg().equals("chatmessage")){
            ((ChatMessage)networkMessage.getData()).setDeliveredToReceiverAt(LocalDateTime.now());
        }
        buffer.put(networkMessage);
    }




}
