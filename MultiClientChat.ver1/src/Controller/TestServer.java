package Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/*
public class TestServer {

    private int port;

    public TestServer(int port) {
        this.port = port;
    }


    public class Worker extends Thread{
        private int port;
        private ServerSocket serverSocket;
        private Socket socket;

        public Worker(int port){
            this.port = port;
        }

        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (!Thread.interrupted()) {
                    Socket socket = serverSocket.accept();
                    new ServerHandler.ServerClientHandler(socket).run();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public class ServerClientHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream OOS;
        private ObjectInputStream OIS;


    }
} */
