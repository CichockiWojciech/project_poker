package client;

import guiComponents.main.MainPanel;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class WaitingBoardTask implements Runnable {
    private String login;
    private ObjectInputStream in;
    private MainPanel mainPanel;
    private PokerGameTask clientPlayer;
    private boolean leaving;
    private JTextField timeField;

    public WaitingBoardTask(String login, ObjectInputStream in, MainPanel mainPanel, JTextField timeFiled) {
        this.login = login;
        this.in = in;
        this.mainPanel = mainPanel;
        this.timeField = timeFiled;
    }

    @Override
    public void run() {
        try {
            clientPlayer = new PokerGameTask(mainPanel.getGamePanel(), in, login, mainPanel.getServerIp());
            while (!clientPlayer.inGame()){
                Thread.sleep(20);
                if(leaving)
                    return;
            }
            FutureTask<Float> futureTask = new FutureTask<>(clientPlayer);
            new Thread(futureTask).start();
            mainPanel.enterGame();
            mainPanel.setTile("game: " + login);

            float timeResult = futureTask.get();
            timeField.setText(Float.toString(timeResult) + " s");
            mainPanel.backToAccount();

        } catch (IOException | InterruptedException | ExecutionException
                | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void leaveBoard() throws RemoteException {
        clientPlayer.leaveBoard();
        leaving = true;
    }
}
