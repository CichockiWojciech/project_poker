package tests;


import gameLogic.Card;
import gameLogic.HandType;
import org.junit.Test;

import static gameLogic.CardColor.*;
import static gameLogic.CardRank.*;
import static gameLogic.HandType.STRAIGHT;
import static org.junit.Assert.assertFalse;

public class HandTest {

    @Test
    public void notStraightTest() {
        {
            Card[] playerCards = new Card[] {new Card(ACE, PIKES), new Card(TWO, HEARTS)};
            Card[] boardCards = new Card[]
                    {new Card(QUEEN, PIKES), new Card(KING, TILES), new Card(NINE, CLOVERS),
                            new Card(SEVEN, CLOVERS), new Card(FIVE, HEARTS)};


            HandType rank = HandType.rank(boardCards, playerCards);
            System.out.println("rank is : " + rank.name());
            assertFalse(rank == STRAIGHT);
        }
    }

}