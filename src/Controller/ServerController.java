package Controller;
import Model.*;

//controller class for the server side
public class ServerController {

    public ServerController() {

        Server serverVariable = new Server("127.0.0.1", 2343); //skapar server
        serverVariable.start(); //start servern
    }




}

