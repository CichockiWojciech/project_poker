package networkInterfaces.gameInterfaces;

import guiComponents.game.GamePanel;
import networkInterfaces.NetworkInterface;

public abstract class GameInfo extends NetworkInterface {
    public abstract void changeView(GamePanel gamePanel);
}
