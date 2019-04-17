package constants;

import gameLogic.Card;
import gameLogic.CardColor;
import gameLogic.CardRank;
import gameLogic.HandType;

import java.util.Comparator;

public class Rules {
    public static final String RULES_NAME = "Texas Hold'em";
    public static final int PLAYER_CARDS = 2;
    public static final int BOARD_CARDS = 5;
    public static final int TOTAL_CARDS = PLAYER_CARDS + BOARD_CARDS;
    public static final int STRAIGHT_SEQUENCE = 4;
    public static final int FLUSH_AMOUNT = 5;
    public static final Comparator<Card> RANK_COMPARATOR;

    public static final int CARDS_IN_DECK = CardColor.values().length * CardRank.values().length;

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 6;

    static {
        HandType.HIGH_CARD.setValue(1);
        HandType.ONE_PAIR.setValue(2);
        HandType.TWO_PAIR.setValue(3);
        HandType.THREE_OF_KIND.setValue(4);
        HandType.STRAIGHT.setValue(5);
        HandType.FLUSH.setValue(6);
        HandType.FULL_HOUSE.setValue(7);
        HandType.FOUR_OF_KIND.setValue(8);
        HandType.STRAIGHT_FLUSH.setValue(9);
        HandType.ROYAL_FLUSH.setValue(10);

        RANK_COMPARATOR = (Card o1, Card o2) -> o1.getCardRank().getValue() - o2.getCardRank().getValue();
    }
}
