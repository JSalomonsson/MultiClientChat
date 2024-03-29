package View.ChatClient;

import Controller.ClientController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The window that opens when you run the client and lets
 * you log in.
 */
public class LoginWindow extends JFrame {
    private final ClientController controller;
    private JTextField enterUsername;
    private String myUsername;
    private ImageIcon icon;

    public LoginWindow(ClientController controller){
        super("Chat");
        this.controller = controller;

        setUp();
    }

    private void setUp() {
        this.setResizable(false);
        this.setSize(400, 400);
        this.setLayout(null);
        this.setBackground(Color.red);

        JLabel chooseUsername = new JLabel("Enter your username:");
        chooseUsername.setLocation(120, 100);
        chooseUsername.setSize(200, 50);
        this.add(chooseUsername);

        enterUsername = new JTextField();
        enterUsername.setLocation(100, 150);
        enterUsername.setSize(200, 25);
        this.add(enterUsername);

        JButton enterChat = new JButton("Login");
        enterChat.setLocation(150, 300);
        enterChat.setSize(75, 25);
        enterChat.addActionListener(new loginButtonListener());
        this.add(enterChat);

        this.setVisible(true);
    }

    private class loginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //username = enterUsername.getText();
            if (hasUserName()) {
                newUser();
                setVisible(false);
                controller.showMainForm();
            }

        }
    }


    public void newUser(){
            myUsername = getUsername();
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(choosePicture()));
            Image image = bufferedImage.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            loginUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasUserName() {
        return !getUsername().isEmpty();
    }

    //denna metoden låter dig välja en användarbild
    public String choosePicture(){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        File selectedFile = null;
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = jfc.getSelectedFile();
        }
        return selectedFile.getAbsolutePath();
    }


    public String getUsername() {
        return enterUsername.getText();
    }

    public ImageIcon getImageIcon() {
        return icon;
    }

    public void loginUser(){
        controller.loginUser(getUsername(), getImageIcon());
    }
}
