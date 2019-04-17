package board;

import constants.Rules;
import database.Account;
import database.Game;
import gameLogic.Card;
import gameLogic.Deck;
import gameLogic.HandType;
import networkInterfaces.gameInterfaces.BoardInfo;
import networkInterfaces.gameInterfaces.PlayerDecision;
import networkInterfaces.gameInterfaces.PlayerDecisionInfo;
import networkInterfaces.gameInterfaces.PlayerInfo;
import rmi.PlayerImp;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static board.BoardState.*;
import static constants.Rules.MAX_PLAYERS;
import static constants.Rules.MIN_PLAYERS;
import static database.GameResult.LOSE;
import static database.GameResult.WIN;
import static networkInterfaces.gameInterfaces.DecisionType.FOLD;
import static networkInterfaces.gameInterfaces.DecisionType.INIT;

public class Board {

    private String name;
    private Deck deck = new Deck();
    private BoardState state = WAITING;
    private Card[] boardCards = new Card[Rules.BOARD_CARDS];
    private List<PlayerImp> players = new LinkedList<>();
    private Queue<PlayerImp> waitingPlayers = new LinkedList<>();
    private PlayerImp currentPlayer;
    private int startingPlayerIndex = 0;
    private int foldCounter = 0;
    private boolean anotherCycle = false;
    private int jackpot = 0;
    private int tokensToCompensate = 0;
    private Connection connection;


    public BoardState getBoardState() {
        return state;
    }

    public Board(String name)  {
        this.name = name;
    }

    public Board(String name, Connection connection) {
        this.name = name;
        this.connection = connection;
    }

    public synchronized void handlePlayerAction() {
        if(foldCounter == players.size() - 1) {
            while (state != FIVE_CARD)
                changeState();

            changeState();
            return;
        }
        PlayerDecision decision = currentPlayer.getDecision();
        int seat = players.indexOf(currentPlayer);

        handelPlayerDecision();
        changePlayerTurn();

        // skip all folded player
        while (currentPlayer.getDecision().getType() == FOLD)
            changePlayerTurn();

        // jackpot counting
        jackpot = 0;
        for(PlayerImp p : players)
            jackpot += p.getTokens();

        // send player decision
        PlayerDecisionInfo info =
                new PlayerDecisionInfo(seat, players.indexOf(currentPlayer), decision , jackpot, tokensToCompensate);

        sendPlayerDecision(info);
    }

    private boolean checkEqualsStake() {
        boolean equals = true;

        for(PlayerImp player : players) {
            if(player.getDecision().getType() != FOLD &&
                    player.getTokens() != tokensToCompensate) {
                equals = false;
                break;
            }
        }
        return equals;
    }

    private void changeState() {
        if(getBoardState() == FIVE_CARD) {
            summarizeGame();
            startNewGame();
        }
        else {
            switch (state) {
                case THREE_CARD:
                    addFourthCard();
                    break;
                case FOUR_CARD:
                    addFifthCard();
                    break;
            }
        }
    }

    public void changePlayerTurn() {

        if(state == WAITING)
            return;

        int index = players.indexOf(currentPlayer);
        if(anotherCycle) {
            if(checkEqualsStake())
                changeState();
            else {
                if(index == players.size() - 1){
                    currentPlayer = players.get(0);
                    currentPlayer.setMyTurn(true);
                }else{
                    currentPlayer = players.get(index + 1);
                    currentPlayer.setMyTurn(true);
                }
            }
        }
        else {
            if(index == players.size() - 1) {
                if(checkEqualsStake()) {
                    changeState();
                }else {
                    currentPlayer = players.get(0);
                    currentPlayer.setMyTurn(true);
                    anotherCycle = true;
                }
            }
            else {
                currentPlayer = players.get(index + 1);
                currentPlayer.setMyTurn(true);
            }

        }
    }

    private BoardComparator boardComparator = new BoardComparator();
    private void summarizeGame() {

        // count jackpot
        jackpot = 0;
        for(PlayerImp player : players) {
            jackpot += player.getTokens();
        }

        // choose winner
        List<PlayerImp> notFoldedPlayers = new LinkedList<>();
        for(PlayerImp player : players) {
            if(player.getDecision().getType() != FOLD)
                notFoldedPlayers.add(player);
        }
        List<PlayerImp> winners = boardComparator.compare(boardCards, notFoldedPlayers);
        HandType winnerHand = HandType.rank(boardCards, winners.get(0).getCards());

        // divide jackpot, add tokens to winners
        for(PlayerImp winner : winners) {
            winner.addToTotalTokens(jackpot / winners.size());
        }

        // update database
        try {
            Statement statement = connection.createStatement();
            for(PlayerImp player : players) {
                Account account = new Account(player.getLogin(), player.getTotalTokens());
                account.setTokens(statement);
                Game game;
                if(winners.contains(player)){
                    // win game
                    game = new Game(account, WIN,jackpot / winners.size() );
                }else{
                    // lose game
                    game = new Game(account, LOSE, player.getTokens());
                }
                game.insertGame(statement);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // send summary
        sendGameSummary(winners, winnerHand);

        // resetBoard
        foldCounter = 0;
        anotherCycle = false;
        currentPlayer = null;
        tokensToCompensate = 0;
        jackpot = 0;
        for(PlayerImp player : players)
            player.setTokens(0);
        deck.reset();
        state = WAITING;

        // reset all decision
        for(PlayerImp player : players) {
            player.setDecision(new PlayerDecision(INIT));
        }
    }

    private void handelPlayerDecision() {
        PlayerDecision decision = currentPlayer.getDecision();

        switch (decision.getType()) {
            case FOLD:
                // skip player
                foldCounter++;
                break;
            case CHECK:
                currentPlayer.check(tokensToCompensate);
                break;
            case RAISE:
                currentPlayer.raise(tokensToCompensate, decision.getTokens());
                tokensToCompensate = currentPlayer.getTokens();
                break;
        }

    }

    public void addFourthCard() {
        boardCards[3] = deck.pullRandomCard();
        anotherCycle = false;
        currentPlayer = players.get(0);
        state = FOUR_CARD;
        currentPlayer.setMyTurn(true);

        Card[] toSend = new Card[boardCards.length];
        System.arraycopy(boardCards, 0, toSend, 0, toSend.length);

        sendNewCard(new BoardInfo(toSend));
    }

    public void addFifthCard() {
        boardCards[4] = deck.pullRandomCard();
        anotherCycle = false;
        currentPlayer = players.get(0);
        state = FIVE_CARD;
        currentPlayer.setMyTurn(true);

        Card[] toSend = new Card[boardCards.length];
        System.arraycopy(boardCards, 0, toSend, 0, toSend.length);

        sendNewCard(new BoardInfo(toSend));
    }

    public void startNewGame() {
        // update players for next game
        ListIterator<PlayerImp> it = players.listIterator();
        while (it.hasNext()) {
            PlayerImp player = it.next();
            if(player.isLeaveBoard())
                it.remove();
        }

        if(players.size() + waitingPlayers.size() < MIN_PLAYERS){
            waitingPlayers.addAll(players);
            players.clear();
            state = WAITING;
            return;
        }

        while (MAX_PLAYERS - players.size() > 0 && !waitingPlayers.isEmpty()) {
            PlayerImp player = waitingPlayers.poll();
            if(!player.isLeaveBoard())
                players.add(player);
        }

        startingPlayerIndex = 0;
        dealTask();
    }

    public synchronized void addPlayer(PlayerImp playerImp) {
        playerImp.notLeaveBoard();
        waitingPlayers.add(playerImp);
        if(state == WAITING && waitingPlayers.size() >= Rules.MIN_PLAYERS) {
            // start board game
            while (!waitingPlayers.isEmpty()) {
                PlayerImp player = waitingPlayers.remove();
                // probably need break if players in more
                if(!player.isLeaveBoard()){
                    players.add(player);
                }
            }
            dealTask();
        }
    }

    public void removeFromWaitingQueue(PlayerImp player) {
        if(waitingPlayers.contains(player))
            waitingPlayers.remove(player);
    }

    private void dealTask() {
        state = DEAL;
        deck.reset();
        for(PlayerImp player : players) {
            player.setCards(deck.pullRandomCard(Rules.PLAYER_CARDS));
        }
        for(int i = 0 ; i < 3 ; i++)
            boardCards[i] = deck.pullRandomCard();
        boardCards[3] = null;
        boardCards[4] = null;

        for(PlayerImp player : players) {
            player.setDecision(new PlayerDecision(INIT));
        }

        for(int i = 0 ; i < players.size(); ++i){
            players.get(i).setSeatNumber(i);
            try {
                System.out.println("i = " + getPlayers().get(i).getSeatNumber());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        currentPlayer = players.get(startingPlayerIndex);
        currentPlayer.setMyTurn(true);
        state = THREE_CARD;

        sendInitGame();

    }

    public List<PlayerImp> getPlayers() {
        return players;
    }


    public int getTokensToCompensate() {
        return tokensToCompensate;
    }

    private void sendInitGame() {
        List<PlayerInfo> playersInfo = new ArrayList<>(players.size());
        for(int i = 0 ; i < players.size(); ++i){
            playersInfo.add(i, new PlayerInfo(i, players.get(i).getUsername(), null, null));
        }

        Card[] toSend = new Card[boardCards.length];
        System.arraycopy(boardCards, 0, toSend, 0, toSend.length);
        BoardInfo boardInfo = new BoardInfo(playersInfo, toSend);

        for(PlayerImp player : players)
            player.send(boardInfo);
    }

    private void sendPlayerDecision(PlayerDecisionInfo playerDecisionInfo)  {
        for(PlayerImp player : players)
            player.send(playerDecisionInfo);

    }

    private void sendNewCard(BoardInfo boardInfo){
        for(PlayerImp player : players)
            player.send(boardInfo);

    }

    private void sendGameSummary(List<PlayerImp> winners, HandType winnerHand){
        String win = "";
        for(PlayerImp player : winners)
            win += player.getUsername() + " ";
        win +=  " [ " + winnerHand + " ]";
        List<PlayerInfo> playersInfo = new ArrayList<>(players.size());
        try {
            for(PlayerImp player : players) {
                playersInfo.add(new PlayerInfo(player.getSeatNumber(), player.getUsername(),
                        player.getCards()[0], player.getCards()[1]));
            }
            Card[] toSend = new Card[boardCards.length];
            System.arraycopy(boardCards, 0, toSend, 0, toSend.length);
            BoardInfo boardInfo = new BoardInfo(playersInfo, win, toSend);
            for(PlayerImp player : players)
                player.send(boardInfo);
        }
        catch (RemoteException e) {
            e.printStackTrace();

        }
    }

    public int getPlayersAmount(){
        return players.size();
    }

    public int getWaitingAmount(){
        return waitingPlayers.size();
    }

    public String getName() {
        return name;
    }
}
