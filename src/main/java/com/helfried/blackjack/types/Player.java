package com.helfried.blackjack.types;

import javax.persistence.*;

@Entity(name = "Player")
@Table(name = "player")

public class Player {

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

    public Player() {
    }

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public Player(int id, String playerName, int chips, int roundsPlayed, int remainingHints) {
        this.id = id;
        this.playerName = playerName;
        this.chips = chips;
        this.roundsPlayed = roundsPlayed;
        this.remainingHints = remainingHints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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



