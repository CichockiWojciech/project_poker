package networkInterfaces.controlInterfaces;

import board.Board;
import database.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import static networkInterfaces.controlInterfaces.ErrorCode.*;

public class AccountInfoRequest extends ClientRequest {
    private String login;

    public AccountInfoRequest(String login) {
        this.login = login;
    }

    public ServerRespond handel(Connection con, List<Board> board){
        try {
            Statement statement = con.createStatement();
            String username = Account.getUsernameFromLogin(statement, login);
            if(username.equals(""))
                return new ServerRespond(UNKNOWN_LOGIN);
            int tokens = Account.getTokensFormLogin(statement, login);
            int win = Account.getWinAmountFormLogin(statement, login);
            int lose = Account.getLoseAmountFromLogin(statement, login);

            List<String> boardName = new LinkedList<>();
            List<Integer> players = new LinkedList<>();
            List<Integer> waiting = new LinkedList<>();
            for(Board b : board){
                boardName.add(b.getName());
                players.add(b.getPlayersAmount());
                waiting.add(b.getWaitingAmount());
            }

            statement.close();
            return new AccountInfoRespond(OK, username, tokens, win, lose, new BoardListRespond(OK, boardName, players, waiting));
        } catch (SQLException e) {
            e.printStackTrace();
            return new ServerRespond(DB_ERROR);
        }



    }
}
