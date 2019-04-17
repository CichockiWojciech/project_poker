package networkInterfaces.controlInterfaces;

import board.Board;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import static networkInterfaces.controlInterfaces.ErrorCode.OK;

public class BoardListRequest extends ClientRequest {


    @Override
    public BoardListRespond handel(Connection connection, List<Board> board){
        List<String> boardName = new LinkedList<>();
        List<Integer> players = new LinkedList<>();
        List<Integer> waiting = new LinkedList<>();
        for(Board b : board){
            boardName.add(b.getName());
            players.add(b.getPlayersAmount());
            waiting.add(b.getWaitingAmount());
        }
        return new BoardListRespond(OK, boardName, players, waiting);
    }
}
