package tests;

import board.Board;
import networkInterfaces.gameInterfaces.PlayerDecision;
import org.junit.Test;
import rmi.Player;
import rmi.PlayerImp;

import java.rmi.RemoteException;
import java.util.List;

import static networkInterfaces.gameInterfaces.DecisionType.CHECK;
import static networkInterfaces.gameInterfaces.DecisionType.FOLD;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    @Test
    public void firstTest() {
        try {
            Board board = new Board("board1");
            PlayerImp player1 = new PlayerImp("1", board);
            PlayerImp player2 = new PlayerImp("2", board);
            PlayerImp player3 = new PlayerImp("3", board);
            PlayerImp player4 = new PlayerImp("4", board);
            PlayerImp player5 = new PlayerImp("5", board);
            PlayerImp player6 = new PlayerImp("6", board);

            Player client1 = player1;
            Player client2 = player2;
            Player client3 = player3;
            Player client4 = player4;
            Player client5 = player5;
            Player client6 = player6;

            // decision before join game, should ignore
            client1.makeDecision(new PlayerDecision(20));
            client2.makeDecision(new PlayerDecision(20));

            board.addPlayer(player1);
            board.addPlayer(player2);
            board.addPlayer(player3);
            board.addPlayer(player4);
            board.addPlayer(player5);
            board.addPlayer(player6);

            client1.makeDecision(new PlayerDecision(20));
            client2.makeDecision(new PlayerDecision(FOLD));
            client3.makeDecision(new PlayerDecision(20));
            client4.makeDecision(new PlayerDecision(CHECK));
            client1.makeDecision(new PlayerDecision(CHECK));

            // should change state to 4 cards
            client1.makeDecision(new PlayerDecision(CHECK));
            client3.makeDecision(new PlayerDecision(10));
            client4.makeDecision(new PlayerDecision(15));
            client1.makeDecision(new PlayerDecision(FOLD));
            client3.makeDecision(new PlayerDecision(CHECK));

            // leaving events
            client1.leaveBoard();
            client5.leaveBoard();

            // should change state to 5 cards
            client3.makeDecision(new PlayerDecision(10));
            client4.makeDecision(new PlayerDecision(15));
            client3.makeDecision(new PlayerDecision(CHECK));

            System.out.println("Check players:");
            List<PlayerImp> players = board.getPlayers();
            for(PlayerImp player : players)
                System.out.println("id: " + player.getLogin());

            // start new game
            client2.makeDecision(new PlayerDecision(CHECK));
            client3.makeDecision(new PlayerDecision(FOLD));
            client4.makeDecision(new PlayerDecision(10));
            client6.makeDecision(new PlayerDecision(40));
            client2.makeDecision(new PlayerDecision(CHECK));
            client4.makeDecision(new PlayerDecision(CHECK));

            // should change state to 4 cards
            client2.makeDecision(new PlayerDecision(CHECK));
            client4.makeDecision(new PlayerDecision(CHECK));
            client6.makeDecision(new PlayerDecision(CHECK));

            // should change state to 5 cards
            client2.makeDecision(new PlayerDecision(FOLD));
            client4.makeDecision(new PlayerDecision(CHECK));
            client6.makeDecision(new PlayerDecision(FOLD));



            // new game - every fold
            //leaving players
            client2.leaveBoard();
            client3.leaveBoard();
            client6.leaveBoard();

            client2.makeDecision(new PlayerDecision(FOLD));
            client3.makeDecision(new PlayerDecision(FOLD));
            client4.makeDecision(new PlayerDecision(FOLD));
            client6.makeDecision(new PlayerDecision(FOLD));

            // new game - waiting for new players
            board.addPlayer(player1);
            board.addPlayer(player5);
            System.out.println(board.getBoardState());

            board.addPlayer(player6);
            System.out.println(board.getBoardState());

            client4.makeDecision(new PlayerDecision(CHECK));
            client1.makeDecision(new PlayerDecision(10));
            client5.makeDecision(new PlayerDecision(CHECK));
            client6.makeDecision(new PlayerDecision(CHECK));
            client4.makeDecision(new PlayerDecision(CHECK));

            client4.makeDecision(new PlayerDecision(CHECK));
            client1.makeDecision(new PlayerDecision(FOLD));
            client5.makeDecision(new PlayerDecision(FOLD));
            client6.makeDecision(new PlayerDecision(FOLD));

            client4.makeDecision(new PlayerDecision(100));

            assertTrue(true);



        } catch (RemoteException e) {
            System.out.println("error");
            e.printStackTrace();
        }

    }

}