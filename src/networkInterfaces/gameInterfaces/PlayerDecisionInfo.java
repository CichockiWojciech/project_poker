package networkInterfaces.gameInterfaces;

import guiComponents.game.GamePanel;

public class PlayerDecisionInfo extends GameInfo {
    private int seat;
    private int nextSeat;
    private PlayerDecision decision;
    private int jackpot;
    private int tokensToCompensate;

    public PlayerDecisionInfo(int seat, int nextSeat, PlayerDecision decision, int jackpot, int tokensToCompensate) {
        this.seat = seat;
        this.nextSeat = nextSeat;
        this.decision = decision;
        this.jackpot = jackpot;
        this.tokensToCompensate = tokensToCompensate;
    }

    @Override
    public void changeView(GamePanel gamePanel) {
        DecisionType type = decision.getType();
        switch (type){
            case RAISE:{
                gamePanel.setPlayerTokens("RAISE: " + tokensToCompensate, true, seat);
                break;
            }
            case CHECK:{
                gamePanel.setPlayerTokens("CHECK: " + tokensToCompensate,
                        tokensToCompensate > 0, seat);
                break;
            }
            case FOLD:{
                gamePanel.setPlayerTokens("PASS", false, seat);
            }
        }

        gamePanel.setJackpot(jackpot);
        gamePanel.setPlayerTurn(nextSeat);
    }
}
