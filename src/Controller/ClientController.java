package Controller;
import Model.*;
import View.ChatClient.*;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientController {
    private ClientHomeView homeView;
    private final HashMap<ArrayList<String>, ChatWindow> chatWindows;
    private Client client;
    private User user;
    private final Buffer<NetworkMessage> buffer;
    private final LoggedInManager loggedInUsers;

    public ClientController(){
        new LoginWindow(this);
        buffer = new Buffer<>();
        loggedInUsers = new LoggedInManager(this);
        chatWindows = new HashMap<>();
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
        loggedInUsers.loadFriends();
    }

    /**
     * Creates an arraylist for the receivers and adds the
     * receiving user to it. Creates a new chatMessage, sets the time
     * when it was sent to the server and then calls the
     * sendChatMessageToServer() and clears the text field.
     */
    public void sendMessage(String msg, ImageIcon image, ArrayList<String> peopleToReceive, ChatWindow chatWindow) {
        ChatMessage chatMessage;
        User usersToReceive;
        ArrayList<User> receivers = new ArrayList<>();
        for (int i = 0; i < peopleToReceive.size(); i++) {
            usersToReceive = loggedInUsers.getByUserName(peopleToReceive.get(i));
            receivers.add(usersToReceive);
        }
            chatMessage = new ChatMessage(receivers, msg, image, user);
            chatMessage.setReceivedByServerAt(LocalDateTime.now());
            chatWindow.addChatMessage(chatMessage);
            sendChatMessageToServer(chatMessage);
            chatWindow.clearText();
            if(chatWindow.getImageToSend() != null) {
                chatWindow.clearImage();
            }
        }

    /**
     * Used to send messages to the server
     */
    public void sendChatMessageToServer(ChatMessage msg){
        NetworkMessage message = new NetworkMessage("chatmessage", msg);
        client.sendNetworkMessage(message);
    }

    /**
     * Used when receiving messages.
     */
    public void receiveChatMessageFromServer(ChatMessage chatMessage) {
        openChatAndAddMessage(chatMessage);
    }

    /**
     * Adds a new user to the signed in users list.
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
        homeView.setFriendList(loggedInUsers.getFriendsStringArray());
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
     * Creates a ChatWindow with use of the findChatWindow() method
     * by passing in the users chosen to open with. Checks if the
     * window is null and if it is we create start a new ChatWindow
     * and add the users to it.
     */
    public void openChatWithString(ArrayList<String> users) {
        ChatWindow window;
        window = findChatWindow(users);
        if(window == null) {
            window = new ChatWindow(this, user.getUsername(), users);
            chatWindows.put(users, window);
        }
        window.display();
    }

    /**
     * Opens a chat window when a user is receives a message.
     * We add the receivers to an arraylist and remove our
     * username from it. Then we find the chat window with
     * the correct users in it. If the window is null we create
     * a new window and displays the window and message.
     */
    public void openChatAndAddMessage(ChatMessage message) {
        ArrayList<String> usersInWindowString = new ArrayList<>();
        for (User user : message.getReceivers()) {
                usersInWindowString.add(user.getUsername());
        }

        usersInWindowString.remove(user.getUsername());
        usersInWindowString.add(message.getSender().getUsername());

        ChatWindow window;
        window = findChatWindow(usersInWindowString);

        if(window == null) {
            window = new ChatWindow(this, user.getUsername(), usersInWindowString);
            chatWindows.put(usersInWindowString, window);
        }
        window.addChatMessage(message);
        window.display();
    }

    /**
     * Goes through all windows and tries to find the one with the correct users.
     */
    private ChatWindow findChatWindow(ArrayList<String> users) {
        int numberOfCorrectUsers = 0;
        for(ArrayList<String> usersInList : chatWindows.keySet()){
            for(String username : usersInList){
                for(String username2 : users){
                    if(username.equals(username2)){
                        numberOfCorrectUsers++;
                    }
                }
            }
            if(numberOfCorrectUsers == usersInList.size() && numberOfCorrectUsers == users.size()){
                return chatWindows.get(usersInList);
            }else{
                numberOfCorrectUsers = 0;
            }
        }
        return null;
    }

    /**
     * Adds a new friend by getting the user from the signed in
     * users with use of the index chosen in the jlist for the
     * specifik user. Updates the friend list in the gui.
     * @param userIndex index for chosen user in gui.
     */
    public void addFriend(int userIndex) {
        User user = loggedInUsers.getLoggedInUsers().get(userIndex);
        boolean success = loggedInUsers.addFriend(user);
        if(success) {
            homeView.setFriendList(loggedInUsers.getFriendsStringArray());
        }
    }

    public User getUser() {
        return user;
    }

    public void logout() {
        try {
            client.logout();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
