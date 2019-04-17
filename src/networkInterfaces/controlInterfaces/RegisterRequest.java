package networkInterfaces.controlInterfaces;

import board.Board;
import database.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static networkInterfaces.controlInterfaces.ErrorCode.OK;

public class RegisterRequest extends ClientRequest{
    private String username;
    private String login;
    private char[] password;

    public RegisterRequest(String username, String login, char[] password) {
        this.username = username;
        this.login = login;
        this.password = password;
    }

    @Override
    public ServerRespond handel(Connection con, List<Board> boards) throws SQLException {
        Account account = new Account(username, login, new String(password));
        Statement statement = con.createStatement();
        ErrorCode code = account.insertAccount(statement);
        if(code != OK){
            statement.close();
            return new ServerRespond(code);
        }
        statement.close();
        return new AccountInfoRequest(login).handel(con, boards);
    }
}
