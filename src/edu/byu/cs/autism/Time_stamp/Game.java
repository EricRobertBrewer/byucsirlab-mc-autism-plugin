package edu.byu.cs.autism.Time_stamp;

import java.time.LocalTime;

public class Game {
    private String gameName;
    private String playerName;
    private LocalTime enterTime;

    public Game(String gameName, String playerName, LocalTime enterTime){
        this.enterTime = enterTime;
        this.gameName = gameName;
        this.playerName = playerName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public LocalTime getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(LocalTime enterTime) {
        this.enterTime = enterTime;
    }
}
