package rmi;

import board.Board;
import constants.Constants;
import constants.Rules;
import gameLogic.Card;
import networkInterfaces.gameInterfaces.GameInfo;
import networkInterfaces.gameInterfaces.PlayerDecision;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static board.BoardState.WAITING;
import static networkInterfaces.gameInterfaces.DecisionType.FOLD;
import static networkInterfaces.gameInterfaces.DecisionType.INIT;

public class PlayerImp extends UnicastRemoteObject implements Player {
    private String login;
    private String username;
    private ObjectOutputStream out;
    private PlayerDecision decision;
    private Board board;
    private Card[] cards = new Card[Rules.PLAYER_CARDS];
    private int tokens;
    private int totalTokens;
    private boolean myTurn;
    private boolean leaveBoard;
    private int seatNumber = -1;


    public PlayerImp(String login, String username, Board board, ObjectOutputStream out, int tokens) throws RemoteException {
        super(Registry.REGISTRY_PORT);
        this.login = login;
        this.username = username;
        this.out = out;
        this.board = board;
        this.decision = new PlayerDecision(INIT);
        this.totalTokens = tokens;
    }

    public PlayerImp(String id, Board board) throws RemoteException {
        super(Registry.REGISTRY_PORT);
        this.login = id;
        this.board = board;
        this.decision = new PlayerDecision(INIT);
        this.totalTokens = Constants.INIT_TOKENS;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    @Override
    public boolean checkMeTurn() {
        return myTurn;
    }

    @Override
    public void makeDecision(PlayerDecision decision) {
        if(!myTurn || board.getBoardState() == WAITING)
            return;
        if(this.decision.getType() != FOLD)
            this.decision = decision;

        myTurn = false;
        board.handlePlayerAction();
    }

    @Override
    public Card[] getCards() {
        return cards;
    }

    @Override
    public void leaveBoard() {
        leaveBoard = true;
        board.removeFromWaitingQueue(this);
    }

    @Override
    public boolean checkInGame() throws RemoteException {
        if(board.getPlayers().contains(this))
            return true;
        return false;
    }

    public PlayerDecision getDecision() {
        return decision;
    }

    public boolean isLeaveBoard() {
        return leaveBoard;
    }

    public boolean raise(int tokensToCompensate, int tokens) {
        if(this.tokens < tokensToCompensate)
            if(!check(tokensToCompensate))
                return false;

        if(totalTokens >= tokens) {
            this.tokens += tokens;
            this.totalTokens -= tokens;
            return true;
        }
        return false;
    }

    public boolean check(int tokensToCompensate) {
        int diff = tokensToCompensate - tokens;
        if(diff > 0 && totalTokens >= diff){
            tokens += diff;
            totalTokens -= diff;
            return true;
        }
        return false;
    }

    public void send(GameInfo info)  {
        try {
            out.writeObject(info);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTokens() {
        return tokens;
    }

    public int getTotalTokens() {
        return totalTokens;
    }

    public String getUsername() {
        return username;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public String getLogin() {
        return login;
    }

    public void addToTotalTokens(int tokens) {
        totalTokens += tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public void setDecision(PlayerDecision decision) {
        this.decision = decision;
    }

    public void notLeaveBoard(){
        this.leaveBoard = false;
    }


    @Override
    public boolean canCheck() throws RemoteException {
        return getTotalTokens() >= board.getTokensToCompensate() - getTokens();
    }

    @Override
    public boolean canRaise(int amount) throws RemoteException {
        return getTotalTokens() >= board.getTokensToCompensate()- getTokens() + amount;
    }

    public void setSeatNumber(int seatNumber){
        this.seatNumber = seatNumber;
    }

    @Override
    public int getSeatNumber() throws RemoteException {
        return seatNumber;
    }
}
