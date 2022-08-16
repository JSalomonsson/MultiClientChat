package Controller;

/**
 * This class starts the server.
 */
public class ServerMain {

    public static void main(String[] args) {
        Server controller = new Server(2343);
        controller.start();
    }
}
