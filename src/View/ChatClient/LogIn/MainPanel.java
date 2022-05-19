package View.ChatClient.LogIn;

import Controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    private JTextField enterUsername;
    private JLabel chooseUsername;
    private JButton enterChat;
    private String username;
    private ClientController controller;
    private View.ChatClient.list.MainPanel mainPanelList;

    String      appName     = "Coolaste chatrummet";
    //MainGUI mainGUI;
    JFrame      newFrame    = new JFrame(appName);
    JButton     sendMessage;
    JTextField  messageBox;
    JTextArea   chatBox;
    JTextField  usernameChooser;
    JFrame      preFrame;


    public MainPanel(int width, int height, ClientController controller) {
        super(null);
        this.setSize(width, height);
        this.controller = controller;

        setUp();
    }
    public void setUp() {
        setBackground(Color.red);

        chooseUsername = new JLabel("Enter your username:");
        chooseUsername.setLocation(120, 100);
        chooseUsername.setSize(200, 50);
        this.add(chooseUsername);

        enterUsername = new JTextField("a");
        enterUsername.setLocation(100, 150);
        enterUsername.setSize(200, 25);
        enterUsername.addActionListener(l -> new textFieldListener());
        this.add(enterUsername);

        enterChat = new JButton("Login");
        enterChat.setLocation(150, 300);
        enterChat.setSize(75, 25);
        enterChat.addActionListener(new loginButtonListener());
        this.add(enterChat);


    }

    public String getUsername() {
        return username;
    }
    private class textFieldListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            username = enterUsername.getText();
        }
    }

    private class loginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            username = enterUsername.getText();
            if (username.length() < 1) {
                System.out.println("No!");
            }    else {
                //controller.newUser();
                    setVisible(false);
                    //controller.test();
                }

        }
    }
    public void display() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        sendMessage = new JButton("Send ChatMessage");
        //sendMessage.addActionListener(new MainGUI.sendMessageButtonListener());

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(500, 600);
        newFrame.setVisible(true);
    }




}
