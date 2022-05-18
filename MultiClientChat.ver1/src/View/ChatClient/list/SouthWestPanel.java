package View.ChatClient.list;

import Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class SouthWestPanel extends JPanel {
    private ClientController controller;
    private int width;
    private int height;
    private JList friendList;
    private JLabel friendLabel;

    public SouthWestPanel(int width, int height, ClientController controller) {
        this.controller = controller;
        this.setLayout(null);
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        setLocation(0, 100);
        setUp();
    }

    public void setUp() {
        setBackground(Color.ORANGE);

        friendList = new JList();
        JScrollPane s = new JScrollPane();
        //s.setViewportView(friendListe);
        s.setLocation(10, 15);
        s.setSize(150, 380);
        this.add(s);

        friendLabel = new JLabel("Friends:");
        friendLabel.setSize(50, 10);
        friendLabel.setLocation(10, 0);
        this.add(friendLabel);
    }


}
