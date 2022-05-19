package View.ChatClient;

import Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class ClientHomeView extends JFrame {
    private final ClientController controller;
    private JLabel usernameLabel;
    private JLabel imageLabel;
    private JList<String> usersOnline;

    private JPanel mainPanel;

    public ClientHomeView(ClientController controller){
        super("CHAT NEW HOME VIEW");
        this.controller = controller;

        setup();
        pack();
    }

    public void setup() {
        int width = 400;
        int height = 600;
        this.setSize(width, height);
        this.setResizable(false);
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        this.setContentPane(mainPanel);

        //top bar, displaying picture and username
        usernameLabel = new JLabel("");
        Font font = new Font("Comic sans", Font.BOLD, 20);
        usernameLabel.setFont(font);
        usernameLabel.setLocation(200, 10);
        usernameLabel.setSize(200, 50);
        mainPanel.add(usernameLabel);

        imageLabel = new JLabel("");
        imageLabel.setLocation(0, 5);
        imageLabel.setSize(75, 75);
        mainPanel.add(imageLabel);

        //list displaying people online
        usersOnline = new JList<>();
        usersOnline.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        usersOnline.setLocation(0, 120);
        usersOnline.setSize(width /2, 420);
        mainPanel.add(usersOnline);

        JLabel onlineLabel = new JLabel("Online:");
        onlineLabel.setSize(50, 10);
        onlineLabel.setLocation(10, 100);
        mainPanel.add(onlineLabel);

        //BUTTONS
        JButton startChat = new JButton("Start Chat");
        startChat.setLocation(20, 545);
        startChat.setSize(100, 25);
        startChat.addActionListener(l-> controller.openChatWith(usersOnline.getSelectedValue()));
        mainPanel.add(startChat);

        JButton addFriend = new JButton("Add Friend");
        addFriend.setLocation(250, 545);
        addFriend.setSize(100, 25);
        mainPanel.add(addFriend);

        //friend-list
        JList friendList = new JList();
        friendList.setLocation(200, 120);
        friendList.setSize(width /2, 420);
        mainPanel.add(friendList);

        JLabel friendLabel = new JLabel("Friends:");
        friendLabel.setSize(50, 10);
        friendLabel.setLocation(205, 100);
        mainPanel.add(friendLabel);

        this.setVisible(true);
    }


    public void setLoggedInUsers(String[] loggedInUsers) {
        usersOnline.setListData(loggedInUsers);
    }

    public void setUser(String username, ImageIcon image) {
        usernameLabel.setText(username);
        imageLabel.setIcon(image);
    }
}
