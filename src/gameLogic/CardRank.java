package gameLogic;

public enum CardRank implements Comparable<CardRank> {
    TWO(1),
    THREE(2),
    FOUR(3),
    FIVE(4),
    SIX(5),
    SEVEN(6),
    EIGHT(7),
    NINE(8),
    TEN(9),
    JACK(10),
    QUEEN(11),
    KING(12),
    ACE(13);

    private int value;
    CardRank(int value) {
        this.value = value;
    }

    public boolean isSequence(CardRank cr){
        return this.getValue() + 1 == cr.getValue();
    }

    public int getValue() {
        return value;
    }


}
