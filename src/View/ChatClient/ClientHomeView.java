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
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(usersOnline);
        scroll.setLocation(10,120);
        scroll.setSize(width / 2 - 20, 420);
        mainPanel.add(scroll);

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

        JButton logout = new JButton("Logout");
        logout.setLocation(135, 542);
        logout.setSize(100, 20);
        logout.addActionListener(l-> logout());
        mainPanel.add(logout);

        JButton addFriend = new JButton("Add Friend");
        addFriend.setLocation(250, 542);
        addFriend.setSize(100, 20);
        addFriend.addActionListener(l -> controller.addFriend(usersOnline.getSelectedIndex()));
        mainPanel.add(addFriend);

        //friend-list
        friendList = new JList();
        friendList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane s = new JScrollPane();
        s.setViewportView(friendList);
        s.setLocation(200,120);
        s.setSize(width / 2 - 20, 420);
        mainPanel.add(s);

        JLabel friendLabel = new JLabel("Friends:");
        friendLabel.setSize(50, 10);
        friendLabel.setLocation(205, 100);
        mainPanel.add(friendLabel);

        this.setVisible(true);
    }

    private void logout() {
        controller.logout();
    }

    /**
     * Checkar om du har valt en anv??ndare att chatta med,
     * om inte f??r du ett felmeddelande. Om du har valt en eller flera
     * anv??ndare adderas de valda anv??ndarna till en lista genom addAll() metoden.
     * Listan skickas med i anropet till metoden openChatWith i controller.
     * Till sist tas markering bort fr??n de valda anv??ndarna i listorna.
     */
    private void newChatWindow() {
        ArrayList<String> selectedUsers = new ArrayList<>();
        if (friendList.isSelectionEmpty() && usersOnline.isSelectionEmpty()){
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "You must select at least one person!");
        }
        else {
            selectedUsers.addAll(friendList.getSelectedValuesList());
            selectedUsers.addAll(usersOnline.getSelectedValuesList());
            controller.openChatWithString(selectedUsers);
            friendList.clearSelection();
            usersOnline.clearSelection();
        }
    }


    /**
     * @param loggedInUsers lista ??ver inloggade anv??ndare
     * befolkar listan med inloggade v??nner
     */
    public void setUsersOnline(String[] loggedInUsers) {
        usersOnline.setListData(loggedInUsers);
    }

    /**
     * @param friends samtliga kontakter, inloggade eller ej
     * befolkar listan med v??nner, oavsett om dessa ??r inloggade eller ej
     */
    public void setFriendList(String[] friends) {
        friendList.setListData(friends);
    }

    /**
     * @param username inloggat anv??ndarnamn
     * @param image inloggade anv??ndarens bild
     * uppdaterar info om inloggad anv??ndare (bild + namn)
     */
    public void setUser(String username, ImageIcon image) {
        usernameLabel.setText(username);
        imageLabel.setIcon(image);
    }
}
