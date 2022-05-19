package View.ChatClient;

import Controller.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChatWindow extends JFrame {
    private final ClientController controller;
    private JTextField typeMessageBox;
    private JTextArea chat;
    private JButton sendMessage;
    private JButton addImage;
    private JList<JLabel> peopleInChat;
    private JLabel usersInChat;

    public ChatWindow(ClientController controller, String thisUser, String userToChatWith){
        super("Chat");
        this.controller = controller;
        setUp(thisUser, userToChatWith); //call setup-method which setUp all GUI-pars for chat window
    }

    private void setUp(String thisUser, String userToChatWith) {
        //main frame
        this.setSize(600, 600);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        //chat window
        chat = new JTextArea();
        chat.setEditable(false);
        chat.setLocation(5, 5);
        chat.setSize(300, 460);
        chat.setFont(new Font("Comic sans", Font.PLAIN, 15));
        chat.setLineWrap(true);
        this.add(chat);

        //message box
        typeMessageBox = new JTextField();
        typeMessageBox.setLocation(5, 470);
        typeMessageBox.setSize(300, 100);
        this.add(typeMessageBox);

        //friends in chat
        peopleInChat = new JList<>();
        String[] users = new String[]{thisUser, userToChatWith};
        peopleInChat.setListData(users);
        JScrollPane s = new JScrollPane();
        s.setViewportView(peopleInChat);
        s.setLocation(310,0);
        s.setSize(300, 500);
        this.add(s);

        usersInChat = new JLabel("People in chat:");
        usersInChat.setLocation(100, 0);
        usersInChat.setSize(200, 50);
        this.add(usersInChat);

        //buttons
        sendMessage = new JButton("Send");
        sendMessage.setLocation(300, 500);
        sendMessage.setSize(75, 25);
        sendMessage.addActionListener(l -> sendMessage());
        this.add(sendMessage);

        addImage = new JButton("Image");
        addImage.setLocation(300, 550);
        addImage.setSize(75, 25);
        //addImage.addActionListener();
        this.add(addImage);

        this.setVisible(true);

    }

    public void sendMessage(){
        controller.sendMessage(typeMessageBox.getText(), new ArrayList<String>(java.util.List.of(new String[]{"person 1", "person 2"})));
    }


}
