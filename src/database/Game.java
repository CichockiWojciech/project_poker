package database;

import java.sql.SQLException;
import java.sql.Statement;

public class Game {
    public static final String columnId = "id";
    public static final String columnPlayerLogin = "player_login";
    public static final String columnGameResult = "game_result";
    public static final String columnTokens = "tokens";

    private static final DatabaseFormatter tool = new DatabaseFormatter();

    private String playerLogin;
    private GameResult gameResult;
    private int tokens;

    public Game(Account account, GameResult gameResult, int tokens){
        this(account.getLogin(), gameResult, tokens);
    }

    public Game(String playerLogin, GameResult gameResult, int tokens) {
        this.playerLogin = playerLogin;
        this.gameResult = gameResult;
        this.tokens = tokens;
    }

    public void insertGame(Statement sta) throws SQLException {
        sta.executeUpdate(tool.format("INSERT INTO GAME(%s, %s, %s, %s) VALUES (%d, '%s', '%s', %d)",
                columnId, columnPlayerLogin, columnGameResult, columnTokens, 0, playerLogin, gameResult, tokens));
    }
}
