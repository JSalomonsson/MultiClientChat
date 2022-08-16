package View.Server;

import Controller.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the gui for the server log.
 */
public class ServerUI extends JFrame {
    private JPanel mainPanel;
    private JTextField startDate;
    private JTextField endDate;
    private JLabel startLabel;
    private JLabel endLabel;
    private JButton showHistory;
    private JTextPane history;

    private final Server controller;

    public ServerUI(Server controller) {
        super("Server UI");
        this.controller = controller;
        setup();
    }

    private void setup() {
        int width = 400;
        int height = 600;
        this.setSize(width, height);
        this.setResizable(false);
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        this.setContentPane(mainPanel);

        startLabel = new JLabel("Start from:");
        startLabel.setLocation(35, 10);
        startLabel.setSize(150,25);
        startLabel.setFont(new Font("monospaced",Font.BOLD, 20));
        mainPanel.add(startLabel);

        endLabel = new JLabel("End:");
        endLabel.setLocation(35, 45);
        endLabel.setSize(100,25);
        endLabel.setFont(new Font("monospaced", Font.BOLD, 20));
        mainPanel.add(endLabel);

        startDate = new JTextField();
        startDate.setLocation(200, 10);
        startDate.setSize(100,25);
        mainPanel.add(startDate);

        endDate = new JTextField();
        endDate.setLocation(200, 45);
        endDate.setSize(100, 25);
        mainPanel.add(endDate);

        showHistory = new JButton("Show Traffic");
        showHistory.setLocation(115, 75);
        showHistory.setSize(150,30);
        mainPanel.add(showHistory);
        showHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getLogsBetween(startDate.getText(), endDate.getText());
            }
        });

        history = new JTextPane();
        JScrollPane s = new JScrollPane();
        s.setViewportView(history);
        s.setLocation(10,110);
        s.setSize(370, 445);
        mainPanel.add(s);

        setVisible(true);
    }

    public void showSelectedLogs(String logs) {
        history.setText(logs);
    }
}
