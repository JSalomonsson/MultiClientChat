package View.ChatClient.list;

import Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class SouthPanel extends JPanel {
    private int width;
    private int height;
    private ClientController controller;
    private JButton startChat;
    private JButton addFriend;

    public SouthPanel(int width, int height, ClientController controller) {
        this.controller = controller;
        this.setLayout(null);
        this.width = width;
        this.height = height;
        this.setSize(width, height - 400);
        setLocation(0, 400);
        setUp();
    }

    public void setUp(){
        setBackground(Color.MAGENTA);

        startChat = new JButton("Start Chat");
        startChat.setLocation(0, 100);
        startChat.setSize(100, 25);
        //startChat.addActionListener(l-> new ChatWindow(controller));
        this.add(startChat);

        addFriend = new JButton("Add Friend");
        addFriend.setLocation(200, 100);
        addFriend.setSize(100, 25);
        this.add(addFriend);
    }
}
