package View.ChatClient;

import Controller.*;
import Model.ChatMessage;
import Model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This is the gui for the window where you chat.
 */
public class ChatWindow extends JFrame {
    private final ClientController controller;
    private JTextField typeMessageBox;
    private JList<ChatMessage> chat;
    private MessageListRenderer renderer;
    private JButton sendMessage;
    private JButton addImage;
    private JList<String> peopleInChat;
    private JLabel usersInChat;
    private JPanel mainPanel;
    private ImageIcon imageToSend;
    private ArrayList<ChatMessage> messages;

    public ChatWindow(ClientController controller, String thisUser, ArrayList<String> userToChatWith){
        super(thisUser + "'s chat");
        this.controller = controller;
        setUp(thisUser, userToChatWith); //call setup-method which setUp all GUI-pars for chat window
    }

    private void setUp(String thisUser, ArrayList<String> usersToChatWith) {
        //main frame
        this.setSize(600, 600);
        this.setResizable(false);
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);

        //chat window
        renderer = new MessageListRenderer();
        chat = new JList<>();
        chat.setCellRenderer(renderer);
        JScrollPane scroll = new JScrollPane(chat);
        scroll.setLocation(5, 15);
        scroll.setSize(370, 450);
        scroll.setFont(new Font("Comic sans", Font.PLAIN, 15));
        mainPanel.add(scroll);

        //message box
        typeMessageBox = new JTextField();
        typeMessageBox.setLocation(5, 470);
        typeMessageBox.setSize(370, 90);
        mainPanel.add(typeMessageBox);

        //friends in chat
        peopleInChat = new JList<>();
        //String[] users = new String[]{thisUser, String.valueOf(userToChatWith)};
        //peopleInChat.setListData(users);
        JScrollPane s = new JScrollPane();
        s.setViewportView(peopleInChat);
        s.setLocation(380,15);
        s.setSize(200, 450);
        String[] setChatList = usersToChatWith.toArray(new String[0]);
        peopleInChat.setListData(setChatList);
        mainPanel.add(s);

        usersInChat = new JLabel("Chatting with:");
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

        messages = new ArrayList<>();
    }

    private void selectImage() {
        addImageToChat();
    }

    public void display(){
        setVisible(true);
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
            imageToSend = new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(){
        controller.sendMessage(typeMessageBox.getText(), imageToSend, sendMessageTo(), this);//new ArrayList<String>(java.util.List.of(new String[]{"person 1", "person 2"})));
    }

    public JTextField getTypeMessageBox() {
        return typeMessageBox;
    }

    public void clearText() {
        typeMessageBox.setText("");
    }

    public ArrayList<String> sendMessageTo() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < peopleInChat.getModel().getSize(); i++) {
            list.add(peopleInChat.getModel().getElementAt(i));
        }
        return list;
    }

    public void addChatMessage(ChatMessage chatMessage) {
        messages.add(chatMessage);
        ChatMessage[] messageArray = messages.toArray(new ChatMessage[0]);
        chat.setListData(messageArray);
    }

    public ImageIcon getImageToSend() {
        return imageToSend;
    }

    public void clearImage() {
        imageToSend = null;
    }
}
