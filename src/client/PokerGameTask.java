package client;

import constants.Constants;
import gameLogic.Card;
import gameLogic.HandType;
import guiComponents.game.GamePanel;
import networkInterfaces.gameInterfaces.BoardInfo;
import networkInterfaces.gameInterfaces.GameInfo;
import networkInterfaces.gameInterfaces.PlayerDecision;
import rmi.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Callable;

import static networkInterfaces.gameInterfaces.DecisionType.CHECK;
import static networkInterfaces.gameInterfaces.DecisionType.FOLD;

public class PokerGameTask implements Callable<Float> {
    private ObjectInputStream in;
    private Player stub;
    private GamePanel gamePanel;
    private boolean leaveBoard;
    private Card[] cards = new Card[2];

    public PokerGameTask(GamePanel gamePanel, ObjectInputStream in, String login, InetAddress serverIp) throws IOException, NotBoundException {
        this.gamePanel = gamePanel;
        this.in = in;
        Registry reg = LocateRegistry.getRegistry(serverIp.getHostAddress());
        stub  = (Player) reg.lookup(login);

        initView();
        initActionListeners();
    }

    private void initActionListeners(){
        gamePanel.setFoldActionListener(e -> {
            try {
                if(stub.checkMeTurn()){
                    stub.makeDecision(new PlayerDecision(FOLD));
                    actionPerformed();
                }else
                    actionRejected();

            } catch (RemoteException e1) {
                e1.printStackTrace();
                throw new RuntimeException();
            }
        });
        gamePanel.setCheckActionListener(e -> {
            try {
                if(stub.checkMeTurn()){
                    if(stub.canCheck()){
                        stub.makeDecision(new PlayerDecision(CHECK));
                        actionPerformed();
                    }else
                        gamePanel.setActionResultMsg("you do not have enough tokens");
                }else
                    actionRejected();

            } catch (RemoteException e1) {
                e1.printStackTrace();
                throw new RuntimeException();
            }
        });
        gamePanel.setRaiseActionListener(e -> {
            try {
                if(stub.checkMeTurn()){
                    int amount = gamePanel.getRaiseAmount();
                    if(stub.canRaise(amount)){
                        stub.makeDecision(new PlayerDecision(amount));
                        actionPerformed();
                    }else
                        gamePanel.setActionResultMsg("you do not have enough tokens");
                }else
                    actionRejected();

            } catch (RemoteException e1) {
                e1.printStackTrace();
                throw new RuntimeException();
            }
        });
        gamePanel.setLeaveBoardButtonActionListener(e -> {
            try {
                stub.leaveBoard();
                leaveBoard = true;
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void initView() throws RemoteException {
        gamePanel.setTotalTokens(String.valueOf(stub.getTotalTokens()));
        gamePanel.setActionResultMsg("");
        gamePanel.setHandType("");
    }

    private Card[] getCards() throws RemoteException {
        return stub.getCards();
    }

    @Override
    public Float call() throws Exception {
        long startTime = System.currentTimeMillis();
            while (true){
                GameInfo info = (GameInfo) in.readObject();
                info.changeView(gamePanel);
                gamePanel.setTotalTokens(String.valueOf(stub.getTotalTokens()));

                if(info instanceof BoardInfo){
                    BoardInfo bInfo = (BoardInfo) info;
                    if(bInfo.isEndGame()){
                        gamePanel.setHandType(HandType.rank(bInfo.getCards(), cards).toString());
                        Thread.sleep(Constants.DELAY_AFTER_GAME);
                        if(leaveBoard)
                            return (System.currentTimeMillis()-startTime)/1000F;
                        gamePanel.setJackpot(0);
                    }else {
                        cards = stub.getCards();
                        gamePanel.setPlayersCards(getCards(), stub.getSeatNumber());
                    }
                }else{
                    gamePanel.setPlayersCards(getCards(), stub.getSeatNumber());
                }
            }
    }

    public boolean inGame() throws RemoteException {
        return stub.checkInGame();
    }

    public void leaveBoard() throws RemoteException {
        stub.leaveBoard();
    }

    private void actionRejected(){
        gamePanel.setActionResultMsg("action rejected, it is not your turn");
    }

    private void actionPerformed(){
        gamePanel.setActionResultMsg("action performed");
    }
}
