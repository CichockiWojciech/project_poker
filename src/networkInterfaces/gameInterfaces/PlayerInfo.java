package networkInterfaces.gameInterfaces;

import gameLogic.Card;
import guiComponents.game.GamePanel;

public class PlayerInfo extends GameInfo {
    private int seat;
    private String username;
    private Card firstCard;
    private Card secondCard;

    public PlayerInfo(int seat, String username, Card firstCard, Card secondCard) {
        this.seat = seat;
        this.username = username;
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }

    @Override
    public void changeView(GamePanel gamePanel) {
        gamePanel.setPlayer(username, firstCard, secondCard, seat);
    }

}
