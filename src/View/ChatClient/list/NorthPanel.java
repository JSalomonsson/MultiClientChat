package View.ChatClient.list;

import Controller.ClientController;
import Controller.ServerController;

import javax.swing.*;
import java.awt.*;

public class NorthPanel extends JPanel {
    private int width;
    private int height;
    private ClientController controller;
    private JLabel username;
    private JLabel image;

    public NorthPanel(int width, int height, ClientController controller) {
        this.controller = controller;
        this.setLayout(null);
        this.width = width;
        this.height = height;
        this.setSize(width, height - 500);
        setLocation(0, 0);
        setUp();
    }

    public void setUp() {
        setBackground(Color.green);
        username = new JLabel("användarnamn");
        Font font = new Font("Comic sans", Font.BOLD, 20);
        username.setFont(font);
        username.setLocation(width / 2, 10);
        username.setSize(200, 50);
        this.add(username);

        image = new JLabel("bild");
        image.setLocation(10, 10);
        image.setSize(75, 75);
        this.add(image);
    }
}
