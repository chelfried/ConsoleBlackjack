package com.helfried.blackjack.types;

import javax.persistence.*;

@Entity(name = "PlayerData")
@Table(name = "playerdata")

public class PlayerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "chips")
    private int chips;

    @Column(name = "rounds_played")
    private int roundsPlayed;

    @Column(name = "remaining_hints")
    private int remainingHints;

    public PlayerData() {
    }

    public PlayerData(String playerName) {
        this.playerName = playerName;
    }

    public int getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public void setRoundsPlayed(int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public int getRemainingHints() {
        return remainingHints;
    }

    public void setRemainingHints(int remainingHints) {
        this.remainingHints = remainingHints;
    }

}



