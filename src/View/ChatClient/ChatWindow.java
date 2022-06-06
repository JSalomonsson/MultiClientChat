package View.ChatClient;

import Controller.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ChatWindow extends JFrame {
    private final ClientController controller;
    private JTextField typeMessageBox;
    private JTextArea chat;
    private JButton sendMessage;
    private JButton addImage;
    private JList<String> peopleInChat;
    private JLabel usersInChat;
    private JPanel mainPanel;

    public ChatWindow(ClientController controller, String thisUser, ArrayList<String> userToChatWith){
        super("Chat");
        this.controller = controller;
        setUp(thisUser, userToChatWith); //call setup-method which setUp all GUI-pars for chat window
    }

    private void setUp(String thisUser, ArrayList<String> userToChatWith) {
        //main frame
        this.setSize(600, 600);
        this.setResizable(false);
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //chat window
        chat = new JTextArea();
        chat.setEditable(false);
        chat.setLocation(5, 15);
        chat.setSize(370, 450);
        chat.setFont(new Font("Comic sans", Font.PLAIN, 15));
        chat.setLineWrap(true);
        mainPanel.add(chat);

        //message box
        typeMessageBox = new JTextField();
        typeMessageBox.setLocation(5, 470);
        typeMessageBox.setSize(370, 90);
        mainPanel.add(typeMessageBox);

        //friends in chat
        peopleInChat = new JList<>();
        String[] users = new String[]{thisUser, String.valueOf(userToChatWith)};
        peopleInChat.setListData(users);
        JScrollPane s = new JScrollPane();
        s.setViewportView(peopleInChat);
        s.setLocation(380,15);
        s.setSize(200, 450);
        mainPanel.add(s);

        usersInChat = new JLabel("People in chat:");
        usersInChat.setLocation(380, 0);
        usersInChat.setSize(200, 15);
        mainPanel.add(usersInChat);

        //buttons
        sendMessage = new JButton("Send");
        sendMessage.setLocation(380, 525);
        sendMessage.setSize(75, 35);
        sendMessage.addActionListener(l -> sendMessage());
        mainPanel.add(sendMessage);

        addImage = new JButton("Image");
        addImage.setLocation(380, 475);
        addImage.setSize(75, 35);
        addImage.addActionListener(l -> selectImage());
        mainPanel.add(addImage);

        this.setVisible(true);
    }

    private void selectImage() {
       controller.addImageToChat();
    }

    public void sendMessage(){
        controller.sendMessage(typeMessageBox.getText(), new ArrayList<String>(java.util.List.of(new String[]{"person 1", "person 2"})));
    }

    public JTextField getTypeMessageBox() {
        return typeMessageBox;
    }

    public void clearText() {
        typeMessageBox.setText("");
    }
}
