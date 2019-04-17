package networkInterfaces.controlInterfaces;

import networkInterfaces.NetworkInterface;

public class GameRequest extends NetworkInterface {
    private String boardName;
    private String login;
    private String username;

    public GameRequest(String boardName, String login, String username) {
        this.boardName = boardName;
        this.login = login;
        this.username = username;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getLogin() {
        return login;
    }

    public String getUsername() {
        return username;
    }

}
