package board;

import gameLogic.Card;
import gameLogic.CardRank;
import gameLogic.HandType;

import java.util.Arrays;
import java.util.Comparator;

public class SameRankComparator implements Comparator<Card[]> {
    private Card[] boardCard;
    private HandType commonRank;

    public SameRankComparator(Card[] boardCard, HandType commonRank) {
        this.boardCard = boardCard;
        this.commonRank = commonRank;
    }

    @Override
    public int compare(Card[] o1, Card[] o2) {
        Card[] p1 = concat(o1, boardCard);
        Card[] p2 = concat(o2, boardCard);
        CardRank p1Rank, p2Rank;

        switch (commonRank){
            case ONE_PAIR:{
                p1Rank = HandType.checkPair(p1, null);
                p2Rank = HandType.checkPair(p2, null);
                break;
            }
            case TWO_PAIR:{
                CardRank firstPairP1 = HandType.checkPair(p1, null);
                CardRank secondPairP1 = HandType.checkPair(p1, firstPairP1);

                CardRank firstPairP2 = HandType.checkPair(p2, null);
                CardRank secondPairP2 = HandType.checkPair(p2, firstPairP2);
                p1Rank =  firstPairP1.compareTo(secondPairP1) > 0  ? firstPairP1 : secondPairP1;
                p2Rank =  firstPairP2.compareTo(secondPairP2) > 0  ? firstPairP2 : secondPairP2;

                if(p1Rank.compareTo(p2Rank) == 0){
                    p1Rank = firstPairP1.compareTo(secondPairP1) > 0  ? secondPairP1 : firstPairP1;
                    p2Rank =  firstPairP2.compareTo(secondPairP2) > 0  ? secondPairP2  :  firstPairP2;
                }
                break;
            }
            case FOUR_OF_KIND:{
                p1Rank = HandType.checkFourOfKind(p1);
                p2Rank = HandType.checkFourOfKind(p2);
                break;
            }

            case THREE_OF_KIND :
            case FULL_HOUSE:{
                p1Rank = HandType.checkThreeOfKind(p1);
                p2Rank = HandType.checkThreeOfKind(p2);
                break;
            }
            default:{
                p1Rank = getHighestCard(p1);
                p2Rank = getHighestCard(p2);
            }
        }
        return p1Rank.compareTo(p2Rank);
    }

    private static CardRank getHighestCard(Card[] playerCard){
        return getHighestCard(playerCard[0].getCardRank(), playerCard[1].getCardRank());
    }

    private static CardRank getHighestCard(CardRank rank1, CardRank rank2){
        return rank1.compareTo(rank2) > 0 ? rank1 : rank2;
    }

    private static<T> T[] concat(T[] first, T[] second){
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
