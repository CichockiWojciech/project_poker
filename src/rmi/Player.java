package rmi;

import gameLogic.Card;
import networkInterfaces.gameInterfaces.PlayerDecision;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Player extends Remote {
    boolean checkMeTurn() throws RemoteException;
    void makeDecision(PlayerDecision decision) throws RemoteException;
    Card[] getCards() throws RemoteException;
    int getTotalTokens() throws RemoteException;
    boolean checkInGame() throws RemoteException;

    boolean canCheck() throws RemoteException;
    boolean canRaise(int amount) throws RemoteException;

    int getSeatNumber() throws RemoteException;

    void leaveBoard() throws RemoteException;
}
