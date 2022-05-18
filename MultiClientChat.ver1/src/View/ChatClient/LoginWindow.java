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

public class LoginWindow extends JFrame {
    private ClientController controller;
    private JTextField enterUsername;
    private JLabel chooseUsername;
    private String myUsername;
    private JButton enterChat;
    private String username;
    private JPanel panel;
    private JTextField messageBox;
    private JButton sendMessage;
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

        chooseUsername = new JLabel("Enter your username:");
        chooseUsername.setLocation(120, 100);
        chooseUsername.setSize(200, 50);
        this.add(chooseUsername);

        enterUsername = new JTextField();
        enterUsername.setLocation(100, 150);
        enterUsername.setSize(200, 25);
        //enterUsername.addActionListener(l -> new MainPanel.textFieldListener());
        this.add(enterUsername);

        enterChat = new JButton("Login");
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
            if (!hasUserName()) {
                System.out.println("There's no user name!");
            }    else {
                newUser();
                setVisible(false);
                ClientHomeView homeView = new ClientHomeView(controller);
                homeView.SetUp();
                //controller.test();
            }

        }
    }


    public void newUser(){
        /*
        kollar så att man har skrivit ett användarnamn,
         */
        if(hasUserName()) {
            myUsername = getUsername();
        }
        try {
            //sparar bild till användaren, med hjälp av metoden getPicture() som låter dig välja en bild
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
            System.out.println(selectedFile.getAbsolutePath());
        }
        return selectedFile.getAbsolutePath();
    }


    public String getUsername() {
        return enterUsername.getText();
    }

    public ImageIcon getImage() {
        return icon;
    }

    public void loginUser(){
        controller.loginUser(getUsername(), getImage());
    }
}
