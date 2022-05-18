package View.ChatClient;

import Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class ClientHomeView extends JFrame {
    private ClientController controller;
    private int width = 400;
    private int height = 600;
    private JLabel username;
    private JLabel image;
    private JList usersOnline;
    private JLabel onlineLabel;
    private JButton startChat;
    private JButton addFriend;
    private JList friendList;
    private JLabel friendLabel;

    public ClientHomeView(ClientController controller){
        super("CHAT NEW HOME VIEW");
        this.controller = controller;

        SetUp();
    }

    public void SetUp() {
        this.setSize(width, height);
        this.setLayout(null);
        this.setResizable(false);

        //top bar, displaying picture and username
        username = new JLabel("username");
        Font font = new Font("Comic sans", Font.BOLD, 20);
        username.setFont(font);
        username.setLocation(200, 10);
        username.setSize(200, 50);
        this.add(username);

        image = new JLabel("bild här");
        image.setLocation(0, 5);
        image.setSize(75, 75);
        this.add(image);

        //list displaying people online
        usersOnline = new JList();
        usersOnline.setBackground(Color.pink);
        usersOnline.setLocation(0, 115);
        usersOnline.setSize(width/2, 420);
        this.add(usersOnline);

        onlineLabel = new JLabel("Online:");
        onlineLabel.setSize(50, 10);
        onlineLabel.setLocation(10, 100);
        this.add(onlineLabel);

        //BUTTONS
        startChat = new JButton("Start Chat");
        startChat.setLocation(20, 536);
        startChat.setSize(100, 25);
        startChat.addActionListener(l-> new ChatWindow(controller));
        this.add(startChat);

        addFriend = new JButton("Add Friend");
        addFriend.setLocation(250, 536);
        addFriend.setSize(100, 25);
        this.add(addFriend);


        //friend-list
        friendList = new JList();
        friendList.setLocation(200, 115);
        friendList.setSize(width/2, 420);
        friendList.setBackground(Color.GRAY);
        this.add(friendList);

        friendLabel = new JLabel("Friends:");
        friendLabel.setSize(50, 10);
        friendLabel.setLocation(205, 100);
        this.add(friendLabel);

        this.setVisible(true);

    }




}
