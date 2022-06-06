package Controller;
import Model.*;
import View.ChatClient.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientController<T> {
    private LoginWindow loginView;
    private ClientHomeView homeView;
    private final HashMap<User, ChatWindow> chatWindows;
    private Client client;
    private User user;
    private  Buffer<T> buffer;
    private LoggedInManager loggedInUsers;
    private ChatWindow chat;
    private ChatMessage chatMessage;

    public ClientController(){
        loginView = new LoginWindow(this);
        buffer = new Buffer<>();
        loggedInUsers = new LoggedInManager(user);
        chatWindows = new HashMap<>();
        chatMessage = new ChatMessage();
    }

    /**
     * Creates a new client and connects
     * it to the server.
     * @param user the new client.
     */
    public void login(User user) {
        this.client = new Client("localhost", 2343, (Buffer<NetworkMessage>) buffer, this); // skapar en client
        this.client.connect(user); //connectar clienten
    }

    /**
     * Creates a new user and calls the method that
     * is used to create a new client.
     * @param username the username
     * @param image the users image
     */
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

    /**
     * If statements to decide which type of message that is to
     * be sent, text, text and image, image. Then calls the
     * sendChatMessageToServer() and clears the text field and removes
     * the chosen image.
     * @param msg text to be sent
     * @param receivers receivers
     */
    public void sendMessage(String msg, ArrayList<String> receivers) {
        /*
        skapa ett chattmeddelande
        i denna controllern, ha en sendmessage-metod där jag skickar in meddelande
        metoden packar in meddelande i ett network-message
        skicka till servern
         */
        //TODO försöka hitta smidigare lösning
        if (!chat.getTypeMessageBox().getText().isEmpty() && chatMessage.getImage() == null) {
            chatMessage = new ChatMessage(receivers, msg);
        System.out.println("test1: " + chatMessage.getImage());
        }
        else if (!chat.getTypeMessageBox().getText().isEmpty() && chatMessage.getImage() != null) {
            chatMessage = new ChatMessage(receivers, msg, chatMessage.getImage());
            System.out.println("test2: " + chatMessage.getImage());
        }
        else if (chat.getTypeMessageBox().getText().isEmpty() && chatMessage.getImage() != null) {
            chatMessage = new ChatMessage(receivers, chatMessage.getImage());
            System.out.println("test3: " + chatMessage.getImage());
        }
        sendChatMessageToServer(chatMessage);
        System.out.println("Creating new chat message in ClientController");
        chat.clearText();
        chatMessage.setImage(null);
    }

    public void sendChatMessageToServer(ChatMessage msg){
        NetworkMessage message = new NetworkMessage("chatmessage", msg);
        client.sendNetworkMessage(message);
    }

    public void receiveChatMessageFromServer(ChatMessage chatMessage) {
        System.out.println(chatMessage.getMessageText());
    }

    /**
     * Adds a new user to the signed in users list.
     * @param user user added.
     */
    public void addNewLoggedInUser(User user) {
        loggedInUsers.add(user);
    }

    /**
     * Clears the list of signed in users.
     */
    public void emptyLoggedInUsers() {
        loggedInUsers.clear();
    }

    /**
     * Updates the list of signed in users.
     */
    public void updateLoggedInUsersView() {
        homeView.setUsersOnline(loggedInUsers.getAsStringArray());
        homeView.setUser(user.getUsername(), user.getIcon());
    }

    /**
     * Opens the main part of the application where you
     * can see other users.
     */
    public void showMainForm() {
        homeView = new ClientHomeView(this);
        homeView.setup();
    }

    /**
     * Opens a new chat window. In a for loop it checks if the usernames
     * of the chosens users to open a chat with exists in the list of
     * signed in users. If it doesnt exists it checks the friend list.
     * When the it finds the username in a list we get the index of the
     * username and saves the user in the userToAdd variable and adds
     * the user to the chat.
     */
    public void openChatWith(ArrayList<String> users) {
        //TODO ta bort kod som inte används
        /*User usersToChatWith = loggedInUsers.getByUserName(String.valueOf(users));
        ChatWindow chat = new ChatWindow(this, user.getUsername(), usersToChatWith.getUsername());
        chatWindows.put(usersToChatWith, chat);

        ArrayList<User> usersToChatWith = (ArrayList<User>) users.clone();
        ChatWindow chat = new ChatWindow(this, user.getUsername(), users);
        for (int i = 0; i < usersToChatWith.size(); i++) {
            System.out.println("test1");
            User userToAdd = usersToChatWith.get(i);
            System.out.println("test2" + userToAdd);
            chatWindows.put(userToAdd, chat);
        } */
        User userToAdd;
        chat = new ChatWindow(this, user.getUsername(), users);
        for (int i = 0; i < users.size(); i++) {
            if(loggedInUsers.getLoggedInUsers().contains(users.get(i))) {
                int index = loggedInUsers.getLoggedInUsers().indexOf(users.get(i));
                userToAdd = loggedInUsers.getLoggedInUsers().get(index);
                chatWindows.put(userToAdd,chat);
            } else if(loggedInUsers.getFriends().contains(users.get(i))) {
                int index = loggedInUsers.getFriends().indexOf(users.get(i));
                userToAdd = loggedInUsers.getFriends().get(index);
                chatWindows.put(userToAdd,chat);
            }
        }
    }

    /**
     * Adds a new friend by getting the user from the signed in
     * users with use of the index chosen in the jlist for the
     * specifik user. Updates the friend list in the gui.
     * @param userIndex index for chosen user in gui.
     */
    public void addFriend(int userIndex) {
        User user = loggedInUsers.getLoggedInUsers().get(userIndex);
        loggedInUsers.addFriend(user);
        homeView.setFriendList(loggedInUsers.getFriendsStringArray());
    }

    /**
     * This method runs when the user selects to add
     * an image and send it in the chat. It lets the
     * user select and image file and sets the image
     * in ChatMessages to that image.
     */
    public void addImageToChat() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        File selectedFile = null;
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = jfc.getSelectedFile();
        }
        try {
        Image image = ImageIO.read(new File(selectedFile.getAbsolutePath()));
        chatMessage.setImage(new ImageIcon(image));
        } catch (IOException e) {
        e.printStackTrace();
     }
    }
}
