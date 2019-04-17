package networkInterfaces.gameInterfaces;


import java.io.Serializable;

import static networkInterfaces.gameInterfaces.DecisionType.FOLD;
import static networkInterfaces.gameInterfaces.DecisionType.RAISE;

public class PlayerDecision implements Serializable {
    public static DecisionType TIMEOUT = FOLD;

    private DecisionType type = TIMEOUT;
    private int tokens = 0;

    public PlayerDecision(DecisionType decision) {
        this.type = decision;
        this.tokens = 0;
    }

    public PlayerDecision(int tokens) {
        this.type = RAISE;
        this.tokens = tokens;
    }

    public DecisionType getType() {
        return type;
    }

    public int getTokens() {
        return tokens;
    }
}
