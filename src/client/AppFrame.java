package client;

import guiComponents.game.GamePanel;
import guiComponents.main.MainPanel;

import javax.swing.*;

public class AppFrame extends JFrame {
    public static final int FRAME_WIDTH = 16;
    public static final int FRAME_HEIGHT = 37;
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    private MainPanel mainPanel;
    private GamePanel gamePanel;

    public AppFrame(){
        initFrame();
        initMainPanel();
        initGamePanel();

        add(mainPanel);
        setResizable(false);
    }


    private void initFrame() {
        setSize(WIDTH + FRAME_WIDTH, HEIGHT + FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void initMainPanel() {
        mainPanel = new MainPanel(this);
    }

    private void initGamePanel(){
        gamePanel = new GamePanel(this);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void enterGame(){
        remove(mainPanel);
        add(gamePanel);
        repaint();
    }

    public void backToAccount(){
        remove(gamePanel);
        add(mainPanel);
        repaint();
    }
}
