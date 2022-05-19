package View.ChatClient.list;

import Controller.ClientController;

import javax.swing.*;

public class MainPanel extends JPanel {

    private NorthPanel nPanel;
    private SouthWestPanel swPanel;
    private SouthEastPanel sePanel;
    private SouthPanel sPanel;


    public MainPanel(int width, int height, ClientController controller) {
        super(null);
        this.setSize(width, height);

        nPanel = new NorthPanel(width , height, controller);
        add(nPanel);

        swPanel = new SouthWestPanel(width / 2, height - 200, controller);
        add(swPanel);

        sePanel = new SouthEastPanel(width / 2, height - 200, controller);
        add(sePanel);

        sPanel = new SouthPanel(width, height , controller);
        add(sPanel);
    }

    public SouthEastPanel getSePanel() {
        return sePanel;
    }
}
