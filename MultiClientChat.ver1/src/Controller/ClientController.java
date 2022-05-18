package Controller;
import Model.*;
import View.ChatClient.LoginWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClientController {
    private LoginWindow loginGUI;
    private Client client;
    private User user;
    private String username;
    private Image image;
    private Buffer<NetworkMessage> buffer;


    public ClientController(){
        loginGUI = new LoginWindow(this);
        buffer = new Buffer<NetworkMessage>();
    }

    public void login(User user) {
        this.client = new Client("localhost", 2343, buffer, this); // skapar en client
        this.client.connect(user); //connectar clienten
    }

    public void loginUser(String username, ImageIcon image){
        user = new User(username, image);
        login(user);
    }


    /*

    public User newUser() {
        if(loginGUI.getUsername() != null) {
            username = loginGUI.getUsername();
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(getPicture()));
            Image image = bufferedImage.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            user = new User(username, icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ImageIcon icon = new ImageIcon(client.getPicture());
        //user = new User(username, icon);
        //System.out.println(user);

        return user;
    }

    public String getPicture() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        File selectedFile = null;
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        }
        return selectedFile.getAbsolutePath();
    }

   /* public void setUsername() {
        String username = mainPanel.getUsername();
        System.out.println(username);
    }

    public String getUsername() {
        String username = user.getUsername();
        return username;
    }

    public ImageIcon getImage() {
        ImageIcon image = user.getIcon();
        return image;
    }

     */ //new user



    public String[] userArray() {
        String[] users = {"Erik", "HungaMördaren"};
        return users;
    }

    public void sendMessage(String msg, ArrayList<String> receivers) {
        /*
        skapa ett chattmeddelande
        i denna controllern, ha en sendmessage-metod där jag skickar in meddelande
        metoden packar in meddelande i ett network-message
        skicka till servern
         */

        ChatMessage chatMessage = new ChatMessage(receivers, msg);
        sendChatMessageToServer(chatMessage);

        System.out.println("Creating new chat message in ClientController");

    }

    public void sendChatMessageToServer(ChatMessage msg){
        NetworkMessage message = new NetworkMessage("chatmessage", msg);
        client.sendNetworkMessage(message);

    }

    public void receiveChatMessageFromServer(ChatMessage chatMessage) {
        /*
        detta ska hamna i guit
         */

        System.out.println(chatMessage.getMessageText());
    }
}
