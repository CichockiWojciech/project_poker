package tests;

import board.Board;
import board.BoardComparator;
import gameLogic.Card;
import org.junit.Test;
import rmi.PlayerImp;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import static gameLogic.CardColor.*;
import static gameLogic.CardRank.*;
import static org.junit.Assert.assertTrue;

public class BoardComparatorTest {

    @Test
    public void oneWinner() throws RemoteException {
        BoardComparator boardComparator = new BoardComparator();
        Board board = new Board("board");
        PlayerImp player1 = new PlayerImp("1", board);
        PlayerImp player2 = new PlayerImp("2", board);
        PlayerImp player3 = new PlayerImp("3", board);
        List<PlayerImp> players = new LinkedList<>();

        players.add(player1);
        players.add(player2);
        players.add(player3);

        // 1 para, wysoka karta, strit
        Card[] boardCards = new Card[]
                {new Card(QUEEN, PIKES), new Card(KING, TILES), new Card(NINE, CLOVERS),
                        new Card(SEVEN, CLOVERS), new Card(FIVE, HEARTS)};
        player1.setCards(new Card[] {new Card(QUEEN, PIKES), new Card(TEN, HEARTS)});
        player2.setCards(new Card[] {new Card(ACE, PIKES), new Card(TWO, HEARTS)});
        player3.setCards(new Card[] {new Card(TEN, PIKES), new Card(JACK, HEARTS)});




        List<PlayerImp> winners = boardComparator.compare(boardCards, players);
        for(PlayerImp winner : winners) {
            System.out.println(winner.getLogin());
        }
        assertTrue(winners.size() == 1 && winners.get(0).getLogin() == player3.getLogin());
    }

    @Test
    public void onePlayerInTest() throws RemoteException {
        BoardComparator boardComparator = new BoardComparator();
        Board board = new Board("board");
        PlayerImp player1 = new PlayerImp("1", board);
        List<PlayerImp> players = new LinkedList<>();

        players.add(player1);

        // 1 para, wysoka karta, strit
        Card[] boardCards = new Card[]
                {new Card(QUEEN, PIKES), new Card(KING, TILES), new Card(NINE, CLOVERS),
                        new Card(SEVEN, CLOVERS), new Card(FIVE, HEARTS)};
        player1.setCards(new Card[] {new Card(QUEEN, PIKES), new Card(TEN, HEARTS)});


        List<PlayerImp> winners = boardComparator.compare(boardCards, players);
        for(PlayerImp winner : winners) {
            System.out.println(winner.getLogin());
        }
        assertTrue(winners.size() == 1 && winners.get(0).getLogin() == player1.getLogin());
    }

    @Test
    public void manyWinners() throws RemoteException {
        BoardComparator boardComparator = new BoardComparator();
        Board board = new Board("board");
        PlayerImp player1 = new PlayerImp("1", board);
        PlayerImp player2 = new PlayerImp("2", board);
        PlayerImp player3 = new PlayerImp("3", board);
        List<PlayerImp> players = new LinkedList<>();

        players.add(player1);
        players.add(player2);
        players.add(player3);

        // 1 para, wysoka karta, strit
        Card[] boardCards = new Card[]
                {new Card(QUEEN, PIKES), new Card(KING, TILES), new Card(NINE, CLOVERS),
                        new Card(SEVEN, CLOVERS), new Card(FIVE, HEARTS)};
        player1.setCards(new Card[] {new Card(QUEEN, PIKES), new Card(TEN, HEARTS)});
        player2.setCards(new Card[] {new Card(ACE, PIKES), new Card(TWO, HEARTS)});
        player3.setCards(new Card[] {new Card(QUEEN, PIKES), new Card(TEN, HEARTS)});



        List<PlayerImp> winners = boardComparator.compare(boardCards, players);
        for(PlayerImp winner : winners) {
            System.out.println(winner.getLogin());
        }
        assertTrue(winners.size() == 2);
    }


}