package gameLogic;

import constants.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    private Random random = new Random();
    private int amount = Rules.CARDS_IN_DECK;
    private List<Card> cards = new ArrayList<>(amount);


    public Deck() {
        for(CardRank rank : CardRank.values())
            for(CardColor color : CardColor.values())
                cards.add(new Card(rank, color));
    }

    public Card pullRandomCard() {
        return cards.remove(random.nextInt(amount--));
    }

    public void reset() {
        cards.clear();
        for(CardRank rank : CardRank.values())
            for(CardColor color : CardColor.values())
                cards.add(new Card(rank, color));
        amount = Rules.CARDS_IN_DECK;
    }

    public Card[] pullRandomCard(int n) {
        Card[] c = new Card[n];
        for(int i = 0 ; i < n ; ++i)
            c[i] = cards.remove(random.nextInt(amount--));
        return c;

    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Deck:\n");
        for (Card c : cards
             ) {
            sb.append(c).append("\n");
        }
        return sb.toString();
    }
}
