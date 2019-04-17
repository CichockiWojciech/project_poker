package networkInterfaces.controlInterfaces;

import java.util.List;

public class BoardListRespond extends ServerRespond {
    private List<String> boards;
    private List<Integer> players;
    private List<Integer> waiting;

    public BoardListRespond(ErrorCode code, List<String> boards, List<Integer> players, List<Integer> waiting) {
        super(code);
        this.boards = boards;
        this.players = players;
        this.waiting = waiting;
    }

    public List<String> getBoards() {
        return boards;
    }

    public List<Integer> getPlayers() {
        return players;
    }

    public List<Integer> getWaiting() {
        return waiting;
    }
}
