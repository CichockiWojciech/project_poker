package database;

import constants.Constants;
import networkInterfaces.controlInterfaces.ErrorCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static networkInterfaces.controlInterfaces.ErrorCode.*;

public class Account extends DatabaseFormatter {
    public static final String columnLogin = "login";
    public static final String columnUsername = "username";
    public static final String columnPassword = "password";
    public static final String columnTokens = "tokens";

    public static final int LOGIN_LENGTH = 40;
    public static final int USERNAME_LENGTH = 30;
    public static final int TOKENS_LENGTH = 24;
    public static final int SHA1_LENGTH = 40;

    private static final DatabaseFormatter tool = new DatabaseFormatter();

    private String username;
    private String login;
    private String password;
    private int tokens;

    public Account(String login, int newTokens) {
        this.login = login;
        this.tokens = newTokens;
    }

    private Account(String username, String login, String password, int tokens) {
        this.username = username;
        this.login = login;
        this.password = password;
        this.tokens = tokens;
    }

    public Account(String login, String password){
        this.login = login;
        this.password = password;
    }

    public Account(String username, String login, String password) {
        this(username, login, password, Constants.INIT_TOKENS);
    }

    public ErrorCode insertAccount(Statement sta) throws SQLException {
        ErrorCode code = canCreate(sta);
        if(code == OK){
            sta.executeUpdate(tool.format("INSERT INTO ACCOUNT(%s, %s, %s, %s) " +
                            "VALUES('%s', '%s', SHA1('%s'), '%s')", columnLogin, columnUsername,
                    columnPassword, columnTokens,
                    login, username, password, tokens));
        }
        return code;
    }

    public ErrorCode login(Statement sta) throws SQLException {
        ErrorCode code = loginExist(sta);
        if(code == OK){
            ResultSet result = sta.executeQuery(tool.format("SELECT * FROM ACCOUNT WHERE %s = '%s' AND %s = SHA1('%s')",
                    columnLogin, login, columnPassword, password));
            if(result.next())
                return OK;
            return WRONG_PASSWORD;
        }
        return code;
    }

    private ErrorCode loginExist(Statement sta) throws SQLException {
        ResultSet result = sta.executeQuery(tool.format("SELECT * FROM ACCOUNT WHERE %s = '%s' ", columnLogin, login));
        if(result.next())
            return OK;
        return UNKNOWN_LOGIN;
    }

    private ErrorCode canCreate(Statement sta) throws SQLException {
        ResultSet result = sta.executeQuery(tool.format("SELECT * FROM ACCOUNT WHERE login = '%s'", login));
        if(result.next()){
            result.close();
            return LOGIN_OCCUPIED;
        }

        result = sta.executeQuery(tool.format("SELECT * FROM ACCOUNT WHERE username = '%s'", username));
        if(result.next()){
            result.close();
            return USERNAME_OCCUPIED;
        }

        result.close();
        return OK;
    }

    public void setTokens(Statement sta) throws SQLException {
        sta.executeUpdate(tool.format("UPDATE ACCOUNT SET %s = %d WHERE %s = '%s' ",
                columnTokens, tokens, columnLogin, login));
    }

    public static String getUsernameFromLogin(Statement sta, String login) throws SQLException {
        ResultSet result = sta.executeQuery(tool.format(
                "SELECT %s FROM ACCOUNT WHERE %s = '%s'",
                columnUsername,columnLogin, login));

        if(result.next())
            return result.getString(1);
        return "";
    }

    public static int getTokensFormLogin(Statement sta, String login) throws SQLException {
        ResultSet result = sta.executeQuery(tool.format("SELECT %s FROM ACCOUNT WHERE %s = '%s'",
                columnTokens,columnLogin, login));

        if(result.next())
            return result.getInt(1);
        return -1;
    }

    public static int getWinAmountFormLogin(Statement sta, String login) throws SQLException {
        ResultSet result = sta.executeQuery(tool.format("SELECT COUNT(*) FROM GAME JOIN ACCOUNT ON %s = player_login WHERE " +
                "game_result = 'win' AND %s = '%s'", columnLogin, columnLogin, login));

        if(result.next())
            return result.getInt(1);
        return -1;
    }

    public static int getLoseAmountFromLogin(Statement sta, String login) throws SQLException {
        ResultSet result = sta.executeQuery(tool.format("SELECT COUNT(*) FROM GAME JOIN ACCOUNT ON %s = player_login WHERE " +
                "game_result = 'lose' AND %s = '%s'", columnLogin, columnLogin, login));

        if(result.next())
            return result.getInt(1);
        return -1;
    }

    public String getLogin() {
        return login;
    }
}
