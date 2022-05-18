package View.ChatClient.list;

import Controller.ClientController;
import Controller.ServerController;

import javax.swing.*;
import java.awt.*;

public class SouthEastPanel extends JPanel {
    private ClientController controller;
    private int width;
    private int height;
    private JList users;
    private JLabel homies;

    public SouthEastPanel(int width, int height, ClientController controller) {
        this.controller = controller;
        this.setLayout(null);
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        setLocation(200, 100);
        setUp();
    }

    public void setUp() {
        setBackground(Color.cyan);
        users = new JList();
        JScrollPane s = new JScrollPane();
        s.setViewportView(users);
        s.setLocation(10, 15);
        s.setSize(150, 380);
        this.add(s);

        homies = new JLabel("Online:");
        homies.setSize(50, 10);
        homies.setLocation(10, 0);
        this.add(homies);
    }

    public int getListIndex() {
        return users.getSelectedIndex();
    }


    public void currentOrder(String[] order){
        this.users.setListData(order);
    }

}
