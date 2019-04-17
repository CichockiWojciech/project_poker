package gameLogic;

import constants.Rules;
import exceptions.InvalidBoardCardsException;
import exceptions.InvalidPlayerCardsException;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;


public enum HandType {
    HIGH_CARD {
        @Override
        public boolean is(Card[] playerCards, Card[] boardCards) {
            return true;
        }

        @Override
        public String toString() {
            return "high card";
        }
    },
    ONE_PAIR{
        @Override
        public boolean is(Card[] playerCards, Card[] boardCards) {
            Card[] cards = connectedCards(playerCards, boardCards);
            return checkPair(cards, null) != null;
        }

        @Override
        public String toString() {
            return "one pair";
        }
    },
    TWO_PAIR{
        @Override
        public boolean is(Card[] playerCards, Card[] boardCards) {
            Card[] cards = connectedCards(playerCards, boardCards);
            CardRank firstPair = checkPair(cards, null);
            if(firstPair == null)
                return false;
            if(checkPair(cards, firstPair) == null)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "two pair";
        }
    },
    THREE_OF_KIND{
        @Override
        public boolean is(Card[] playerCards, Card[] boardCards) {
            Card[] cards = connectedCards(playerCards, boardCards);
            return checkThreeOfKind(cards) != null;
        }

        @Override
        public String toString() {
            return "three of kind";
        }
    },
    STRAIGHT{
        @Override
        public boolean is(Card[] playerCards, Card[] boardCards) {
            Card[] cards = connectedCards(playerCards, boardCards);
            return HandType.checkStraight(cards);
        }

        @Override
        public String toString() {
            return "straight";
        }
    },
    FLUSH{
        @Override
        public boolean is(Card[] playerCards, Card[] boardCards) {
            Card[] cards = connectedCards(playerCards, boardCards);
            return mostCommonColor(cards).getValue() >= Rules.FLUSH_AMOUNT;

        }

        @Override
        public String toString() {
            return "flush";
        }
    },
    FULL_HOUSE{
        @Override
        public boolean is(Card[] playerCards, Card[] boardCards) {
            Card[] cards = connectedCards(playerCards, boardCards);
            CardRank threeOfKind = checkThreeOfKind(cards);
            if (threeOfKind == null)
                return false;
            return checkPair(cards, threeOfKind) != null;
        }

        @Override
        public String toString() {
            return "full house";
        }

    },
    FOUR_OF_KIND {
        @Override
        public boolean is(Card[] playerCards, Card[] boardCards) {
            Card[] cards = connectedCards(playerCards, boardCards);
            return checkFourOfKind(cards) != null;
        }

        @Override
        public String toString() {
            return "four of kind";
        }
    },
    STRAIGHT_FLUSH{
        @Override
        public boolean is(Card[] playerCards, Card[] boardCards) {
            Card[] cards = connectedCards(playerCards, boardCards);
            Card[] straight = HandType.straightColor(cards);
            if(straight != null )
                if(mostCommonColor(straight).getValue() >= Rules.FLUSH_AMOUNT)
                    return true;
            return false;
        }

        @Override
        public String toString() {
            return "straight flush";
        }
    },
    ROYAL_FLUSH{
        @Override
        public boolean is(Card[] playerCards, Card[] boardCards) {
            Card[] cards = connectedCards(playerCards, boardCards);
            return checkRoyalFlush(cards);
        }

        @Override
        public String toString() {
            return "royal flush";
        }
    };

    private int value;

    abstract public boolean is(Card[] playerCards, Card[] boardCards);



    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private static Card[] connectedCards(Card[] playerCards, Card[] boardCards){
        try {
            if(playerCards.length != Rules.PLAYER_CARDS)
                throw new InvalidPlayerCardsException();
            if(boardCards.length != Rules.BOARD_CARDS)
                throw new InvalidBoardCardsException();
        }catch(InvalidPlayerCardsException | InvalidBoardCardsException e) {
            throw new RuntimeException();
        }
        Card[] result = new Card[Rules.TOTAL_CARDS];
        int i = 0;
        while (i < Rules.PLAYER_CARDS) {
            result[i] = playerCards[i];
            i++;
        }
        while (i < Rules.TOTAL_CARDS) {
            result[i] = boardCards[i - Rules.PLAYER_CARDS];
            i++;
        }
        return result;
    }


    private static CardRank checkSameRank(Card[] cards, CardRank except, int amount) {
        for(CardRank rank : CardRank.values()) {
            int i = 0;
            for(Card card : cards) {
                if(card.getCardRank() == rank)
                    if(except == null || rank != except)
                        ++i;
            }
            if(i == amount)
                return rank;
        }
        return null;
    }

    public static CardRank checkPair(Card[] cards, CardRank except) {
        return checkSameRank(cards, except, 2);
    }

    public static CardRank checkThreeOfKind(Card[] cards) {
        return checkSameRank(cards, null, 3);
    }

    public static CardRank checkFourOfKind(Card[] cards) {
        return checkSameRank(cards, null, 4);
    }

    private static Pair<CardColor, Integer> mostCommonColor(Card[] cards) {
        EnumMap<CardColor, Integer> amountMap = new EnumMap<>(CardColor.class);
        for(Card card : cards) {
            int previousAmount = amountMap.getOrDefault(card.getCardColor(), 0);
            //int previousAmount = amountMap.get(card.getCardColor());
            amountMap.put(card.getCardColor(), previousAmount + 1);
        }

        CardColor mostCommon = CardColor.HEARTS;
        int largest = 0;
        for(Map.Entry<CardColor, Integer> entry : amountMap.entrySet()) {
            int amount = entry.getValue();
            if(amount > largest) {
                largest = amount;
                mostCommon = entry.getKey();
            }
        }
        return new Pair(mostCommon, largest);
    }

    private static boolean checkStraight(Card[] cards) {
        Arrays.sort(cards, Rules.RANK_COMPARATOR);
        int sequence = 0;
        for(int i = 1 ; i < cards.length; ++i) {
            if(cards[i-1].getCardRank().isSequence(cards[i].getCardRank()))
                sequence++;
            else
                sequence = 0;
            if(sequence == Rules.STRAIGHT_SEQUENCE)
                return true;
        }
        return false;
    }


    private static Card[] straightColor(Card[] cards) {
        Arrays.sort(cards, Rules.RANK_COMPARATOR);
        int sequence = 0;
        for(int i = 1 ; i < cards.length; ++i) {
            if(cards[i-1].getCardRank().isSequence(cards[i].getCardRank()))
                sequence++;
            else
                sequence = 0;
            if(sequence == Rules.STRAIGHT_SEQUENCE) {
                //[i - STRAIGHT_SEQUENCE + 1  :  i]
                Card[] straight = new Card[Rules.FLUSH_AMOUNT];
                System.arraycopy(cards,i-Rules.FLUSH_AMOUNT+1,straight,0,Rules.FLUSH_AMOUNT);
                return straight;
            }
        }
        return null;
    }


    private static boolean checkRoyalFlush(Card[] cards) {

        Pair<CardColor, Integer> pair = mostCommonColor(cards);
        if(pair.getValue() < Rules.FLUSH_AMOUNT & pair.getKey() != CardColor.HEARTS)
            return false;
        EnumMap<CardRank, Boolean> map = new EnumMap<>(CardRank.class);
        for(Card card : cards) {
            if(card.getCardColor() == CardColor.HEARTS)
                map.put(card.getCardRank(), true);
        }
        return
            map.getOrDefault(CardRank.ACE, false) &
            map.getOrDefault(CardRank.KING, false) &
            map.getOrDefault(CardRank.QUEEN, false) &
            map.getOrDefault(CardRank.JACK, false) &
            map.getOrDefault(CardRank.TEN, false);
    }

    public static HandType rank(Card[] boardCards, Card[] playerCards) {
        HandType type = HIGH_CARD;
        for(HandType rank : HandType.values()) {
            if(rank.is(playerCards, boardCards))
                type = rank;
        }
        return type;
    }

    @Override
    abstract public String toString();
}
