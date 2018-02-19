package general;

public class GameSession {
    //Player[] players;
    private boolean mode; // true = real-time, false = turn-based

    GameSession(boolean mode) {
        //this.players = players;
        this.mode = mode;
    }
    public boolean getGameMode() {
        return mode;
    }
}
