package networkInterfaces.controlInterfaces;

import board.Board;
import database.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static networkInterfaces.controlInterfaces.ErrorCode.OK;

public class LoginRequest extends ClientRequest {
    private String login;
    private char[] password;

    public LoginRequest(String login, char[] password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public ServerRespond handel(Connection con, List<Board> boards) throws SQLException {
        Statement statement = con.createStatement();
        Account account = new Account(login, new String(password));
        ErrorCode code = account.login(statement);
        if(code != OK){
            statement.close();
            return new ServerRespond(code);
        }
        statement.close();

        return new AccountInfoRequest(login).handel(con, boards);
    }
}
