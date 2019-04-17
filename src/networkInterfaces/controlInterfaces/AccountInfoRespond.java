package networkInterfaces.controlInterfaces;

public class AccountInfoRespond extends ServerRespond {
    private String username;
    private int totalTokens;

    private int winAmount;
    private int loseAmount;

    private BoardListRespond boardList;

    public AccountInfoRespond(ErrorCode code, String username, int totalTokens, int winAmount, int loseAmount, BoardListRespond boardList) {
        super(code);
        this.username = username;
        this.totalTokens = totalTokens;
        this.winAmount = winAmount;
        this.loseAmount = loseAmount;
        this.boardList = boardList;
    }

    public String getUsername() {
        return username;
    }

    public int getTotalTokens() {
        return totalTokens;
    }

    public int getWinAmount() {
        return winAmount;
    }

    public int getLoseAmount() {
        return loseAmount;
    }

    public BoardListRespond getBoardList() {
        return boardList;
    }
}
