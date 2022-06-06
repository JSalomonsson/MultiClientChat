package View.ChatClient;

import Controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClientHomeView extends JFrame {
    private final ClientController controller;
    private JLabel usernameLabel;
    private JLabel imageLabel;
    private JList<String> usersOnline;
    private JList<String> friendList;

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
        usersOnline.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        usersOnline.setLocation(10, 120);
        usersOnline.setSize(width /2 - 20, 420);
        mainPanel.add(usersOnline);

        JLabel onlineLabel = new JLabel("Online:");
        onlineLabel.setSize(50, 10);
        onlineLabel.setLocation(10, 100);
        mainPanel.add(onlineLabel);

        //BUTTONS
        JButton startChat = new JButton("Start Chat");
        startChat.setLocation(20, 542);
        startChat.setSize(100, 20);
        startChat.addActionListener(l-> newChatWindow());
        mainPanel.add(startChat);

        JButton addFriend = new JButton("Add Friend");
        addFriend.setLocation(250, 542);
        addFriend.setSize(100, 20);
        addFriend.addActionListener(l -> controller.addFriend(usersOnline.getSelectedIndex()));
        mainPanel.add(addFriend);

        //friend-list
        friendList = new JList();
        friendList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        friendList.setLocation(200, 120);
        friendList.setSize(width /2 - 20, 420);
        mainPanel.add(friendList);

        JLabel friendLabel = new JLabel("Friends:");
        friendLabel.setSize(50, 10);
        friendLabel.setLocation(205, 100);
        mainPanel.add(friendLabel);

        this.setVisible(true);
    }

    /**
     * Checkar om du har valt en användare att chatta med,
     * om inte får du ett felmeddelande. Om du har valt en eller flera
     * användare adderas de valda användarna till en lista genom addAll() metoden.
     * Listan skickas med i anropet till metoden openChatWith i controller.
     * Till sist tas markering bort från de valda användarna i listorna.
     */
    private void newChatWindow() {
        ArrayList<Object> selectedUsers = new ArrayList<>();
        if (friendList.isSelectionEmpty() && usersOnline.isSelectionEmpty()){

            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "You must select at least one person!");
        }
        else {
            selectedUsers.addAll(friendList.getSelectedValuesList());
            selectedUsers.addAll(usersOnline.getSelectedValuesList());
            controller.openChatWith(selectedUsers);
            friendList.clearSelection();
            usersOnline.clearSelection();
        }
    }


    /**
     * @param loggedInUsers lista över inloggade användare
     * befolkar listan med inloggade vänner
     */
    public void setUsersOnline(String[] loggedInUsers) {
        usersOnline.setListData(loggedInUsers);
    }

    /**
     * @param friends samtliga kontakter, inloggade eller ej
     * befolkar listan med vänner, oavsett om dessa är inloggade eller ej
     */
    public void setFriendList(String[] friends) {
        friendList.setListData(friends);
    }

    /**
     * @param username inloggat användarnamn
     * @param image inloggade användarens bild
     * uppdaterar info om inloggad användare (bild + namn)
     */
    public void setUser(String username, ImageIcon image) {
        usernameLabel.setText(username);
        imageLabel.setIcon(image);
    }
}
