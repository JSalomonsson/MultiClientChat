package Controller;

import Model.Buffer;
import Model.ChatMessage;
import Model.NetworkMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerClientHandler {
    private Server server;
    private Socket socket;
    private Buffer<NetworkMessage> buffer;

    public void connect(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        buffer = new Buffer<>();

        new ClientInputThread().start();
        new ClientOutputThread().start();
    }


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

    //denna metoden används för att sortera upp meddelande från clienten, t ex chattmeddelande, att någon stänger servern, går online etc.
    public void receiveMessageFromClient(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        NetworkMessage networkMessage = (NetworkMessage) ois.readObject();
        server.handleIncomingMessage(networkMessage, this);
    }

    public void sendMessageToClient(NetworkMessage networkMessage) {
        if(networkMessage.getTypeOfMsg().equals("chatmessage")){
            ((ChatMessage)networkMessage.getData()).setDeliveredToReceiverAt(LocalDateTime.now());
        }
        buffer.put(networkMessage);
    }




}
