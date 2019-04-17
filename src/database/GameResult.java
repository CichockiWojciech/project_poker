package database;

public enum GameResult {
    WIN,
    LOSE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
