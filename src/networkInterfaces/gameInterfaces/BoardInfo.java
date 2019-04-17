package networkInterfaces.gameInterfaces;

import constants.Rules;
import gameLogic.Card;
import guiComponents.game.GamePanel;

import java.util.List;

public class BoardInfo extends GameInfo {
    private List<PlayerInfo> players;
    private String winner;
    private Card[] cards;

    public BoardInfo(List<PlayerInfo> players, Card[] cards) {
        this.players = players;
        this.cards = cards;
    }

    public BoardInfo(Card[] cards){
        this.cards = cards;
    }

    public BoardInfo(List<PlayerInfo> players, String winner, Card[] cards) {
        this.players = players;
        this.winner = winner;
        this.cards = cards;
    }

    @Override
    public void changeView(GamePanel gamePanel) {
        gamePanel.setBoardCards(cards);

        if(players != null){
            int i = 0;
            gamePanel.setHandType("");
            while(i < players.size()){
                players.get(i).changeView(gamePanel);
                gamePanel.setPlayerTokens("", false,i);
                ++i;
            }
            while (i < Rules.MAX_PLAYERS){
                gamePanel.setFreeSeat(i);
                gamePanel.setPlayerTokens("", false,i);
                ++i;
            }
        }

        if(winner != null)
            gamePanel.setWinner(winner);
        else
            gamePanel.setWinner("");
    }

    public Card[] getCards() {
        return cards;
    }

    public boolean isEndGame(){
        return winner != null;
    }
}
