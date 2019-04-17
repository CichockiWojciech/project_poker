package tests;

import gameLogic.Card;
import gameLogic.HandType;
import org.junit.Test;

import static gameLogic.CardColor.*;
import static gameLogic.CardRank.*;
import static gameLogic.HandType.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HandTypeTest {
    @Test
    public void flush() throws Exception {
        Card[] playerCards =  {new Card(ACE, HEARTS), new Card(QUEEN, PIKES)};
        Card[] boardCards = {new Card(ACE, PIKES), new Card(TWO, HEARTS), new Card(JACK, HEARTS),
                            new Card(KING, HEARTS), new Card(QUEEN, HEARTS)};


        boolean flush = FLUSH.is(playerCards, boardCards);
        assertTrue(flush);
    }
    @Test
    public void onePair() throws Exception {
        Card[] playerCards =  {new Card(ACE, HEARTS), new Card(ACE, PIKES)};
        Card[] boardCards = {new Card(QUEEN, PIKES), new Card(JACK, HEARTS), new Card(TEN, HEARTS),
                new Card(NINE, HEARTS), new Card(QUEEN, HEARTS)};



        boolean onePair = ONE_PAIR.is(playerCards, boardCards);
        assertTrue(onePair);
    }

    @Test
    public void twoPair() {
        Card[] playerCards =  {new Card(ACE, HEARTS), new Card(QUEEN, PIKES)};
        Card[] boardCards = {new Card(KING, PIKES), new Card(JACK, HEARTS), new Card(TEN, HEARTS),
                new Card(NINE, HEARTS), new Card(QUEEN, HEARTS)};



        boolean onePair = ONE_PAIR.is(playerCards, boardCards);
        assertTrue(onePair);
    }

    @Test
    public void notTwoPair() {
        Card[] playerCards =  {new Card(QUEEN, HEARTS), new Card(QUEEN, PIKES)};
        Card[] boardCards = {new Card(KING, PIKES), new Card(JACK, HEARTS), new Card(TEN, HEARTS),
                new Card(NINE, HEARTS), new Card(QUEEN, HEARTS)};



        boolean onePair = ONE_PAIR.is(playerCards, boardCards);
        assertFalse(onePair);
    }

    @Test
    public void highCardOnly() {
        Card[] playerCards = {new Card(KING, PIKES), new Card(QUEEN, HEARTS)};
        Card[] boardCards = {new Card(THREE, TILES), new Card(FOUR, CLOVERS), new Card(FIVE, TILES),
                             new Card(SIX, PIKES), new Card(TEN, CLOVERS)};



        boolean result;
        for(HandType h : HandType.values()) {
            result = h.is(playerCards, boardCards);
            if(h == HIGH_CARD)
                assertTrue(result);
            else
                assertFalse(result);
        }
    }

    @Test
    public void threePairAndFull() {
        Card[] playerCards = {new Card(KING, PIKES), new Card(QUEEN, HEARTS)};
        Card[] boardCards = {new Card(KING, TILES), new Card(QUEEN, CLOVERS), new Card(FIVE, TILES),
                new Card(SIX, PIKES), new Card(KING, CLOVERS)};



        boolean result = FULL_HOUSE.is(playerCards, boardCards);
        assertTrue(result);

        result = THREE_OF_KIND.is(playerCards, boardCards);
        assertTrue(result);
    }

    @Test
    public void sequenceHandType() {
        {
            Card[] playerCards = {new Card(KING, PIKES), new Card(QUEEN, HEARTS)};
            Card[] boardCards = {new Card(JACK, TILES), new Card(ACE, CLOVERS), new Card(TEN, TILES),
                    new Card(SIX, PIKES), new Card(THREE, CLOVERS)};



            boolean result = STRAIGHT.is(playerCards, boardCards);
            assertTrue(result);
        }
        {   // close to sequence
            Card[] playerCards = {new Card(ACE, PIKES), new Card(KING, HEARTS)};
            Card[] boardCards = {new Card(QUEEN, TILES), new Card(JACK, CLOVERS), new Card(TWO, HEARTS),
                    new Card(THREE, TILES), new Card(FOUR, HEARTS)};



            boolean result = STRAIGHT.is(playerCards, boardCards);
            assertFalse(result);
        }
        //close to STRAIGHT_FLUSH
        {
            Card[] playerCards = {new Card(KING, PIKES), new Card(QUEEN, PIKES)};
            Card[] boardCards = {new Card(JACK, CLOVERS), new Card(ACE, PIKES), new Card(TEN, CLOVERS),
                    new Card(SIX, PIKES), new Card(THREE, PIKES)};



            boolean result = STRAIGHT_FLUSH.is(playerCards, boardCards);
            assertFalse(result);
        }
        //STRAIGHT_FLUSH
        {
            Card[] playerCards = {new Card(KING, PIKES), new Card(QUEEN, PIKES)};
            Card[] boardCards = {new Card(JACK, PIKES), new Card(ACE, PIKES), new Card(TEN, PIKES),
                    new Card(SIX, CLOVERS), new Card(THREE, CLOVERS)};



            boolean result = STRAIGHT_FLUSH.is(playerCards, boardCards);
            assertTrue(result);
        }

    }

    @Test
    public void royalFlush() {
        // close to royalFlush - lack of one card in correct color
        {
            Card[] playerCards = {new Card(KING, HEARTS), new Card(QUEEN, HEARTS)};
            Card[] boardCards = {new Card(JACK, HEARTS), new Card(ACE, HEARTS), new Card(TEN, CLOVERS),
                    new Card(SIX, PIKES), new Card(THREE, PIKES)};



            boolean result = ROYAL_FLUSH.is(playerCards, boardCards);
            assertFalse(result);
        }
        // close to royalFlush - not HEARTS color
        {
            Card[] playerCards = {new Card(KING, PIKES), new Card(QUEEN, PIKES)};
            Card[] boardCards = {new Card(JACK, PIKES), new Card(ACE, PIKES), new Card(TEN, PIKES),
                    new Card(SIX, TILES), new Card(THREE, TILES)};



            boolean result = ROYAL_FLUSH.is(playerCards, boardCards);
            assertFalse(result);
        }
        // royalFlush
        {
            Card[] playerCards = {new Card(KING, HEARTS), new Card(QUEEN, HEARTS)};
            Card[] boardCards = {new Card(JACK, HEARTS), new Card(ACE, HEARTS), new Card(TEN, HEARTS),
                    new Card(SIX, PIKES), new Card(THREE, PIKES)};



            boolean result = ROYAL_FLUSH.is(playerCards, boardCards);
            assertTrue(result);
        }
    }

    @Test
    public void strightFix() {
        {
            Card[] playerCards = new Card[] {new Card(ACE, PIKES), new Card(TWO, HEARTS)};
            Card[] boardCards = new Card[]
                    {new Card(QUEEN, PIKES), new Card(KING, TILES), new Card(NINE, CLOVERS),
                            new Card(SEVEN, CLOVERS), new Card(FIVE, HEARTS)};


            boolean result = STRAIGHT.is(playerCards, boardCards);
            assertFalse(result);
        }
    }


}