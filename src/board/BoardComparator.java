package board;

import gameLogic.Card;
import gameLogic.HandType;
import rmi.PlayerImp;

import java.util.LinkedList;
import java.util.List;

import static gameLogic.HandType.HIGH_CARD;

public class BoardComparator {

    public List<PlayerImp> compare(Card[] boardCards, List<PlayerImp> players) {
        List<PlayerImp> winners = new LinkedList<>();
        HandType highest = HIGH_CARD;
        for(PlayerImp player : players) {
            Card[] playerCards = player.getCards();
            HandType rank = HandType.rank(boardCards, playerCards);
            if(rank.getValue() > highest.getValue()){
                winners.clear();
                winners.add(player);
                highest = rank;
            }else if(rank.getValue() == highest.getValue()) {
                winners.add(player);
            }
        }

        // multiple players with the same rank
        if(winners.size() > 1){
            SameRankComparator comparator = new SameRankComparator(boardCards, highest);
            PlayerImp best = winners.get(0);

            for(int i = 1 ; i < winners.size(); ++i) {
                PlayerImp player = winners.get(i);
                int result = comparator.compare(best.getCards(), player.getCards());
                if(result > 0) {
                    winners.remove(i);
                }else if(result < 0){
                    winners.remove(best);
                    best = player;
                }
            }
        }
        return winners;
    }
}
