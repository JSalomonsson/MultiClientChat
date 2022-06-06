package Controller;

import Model.Buffer;
import Model.NetworkMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
                    receiveMessageFromClient(ois); //tar emot meddelande
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
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
        System.out.println("Added message to buffer: " + networkMessage.getTypeOfMsg());
        System.out.println("i serverClientHandler: " + networkMessage.getData());
        buffer.put(networkMessage);
    }




}
