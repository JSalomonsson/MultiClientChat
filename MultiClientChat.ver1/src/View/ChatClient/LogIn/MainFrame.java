package View.ChatClient.LogIn;

import Controller.ClientController;

import javax.swing.*;

public class MainFrame extends JFrame {

    private int width = 400;
    private int height = 400;
    private MainPanel mainPanel;
    private ClientController controller;

    public MainFrame(ClientController controller) {
        super("Chat");
        this.controller = controller;
        this.setResizable(false);
        this.setSize(width, height);
        this.mainPanel = new MainPanel(width, height, controller);
        this.setContentPane(mainPanel);
        this.setVisible(true);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
}
