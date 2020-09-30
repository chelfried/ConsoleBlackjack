package com.helfried.blackjack.types;

import javax.persistence.*;

@Entity(name = "Player")
@Table(name = "players")

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

    @Column(name = "right_moves")
    private int rightMoves;

    @Column(name = "wrong_moves")
    private int wrongMoves;

    public Player() {
    }

    public Player(int id, String playerName, int chips, int roundsPlayed) {
        this.id = id;
        this.playerName = playerName;
        this.chips = chips;
        this.roundsPlayed = roundsPlayed;
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

    public int getRightMoves() {
        return rightMoves;
    }

    public void setRightMoves(int rightMoves) {
        this.rightMoves = rightMoves;
    }

    public int getWrongMoves() {
        return wrongMoves;
    }

    public void setWrongMoves(int wrongMoves) {
        this.wrongMoves = wrongMoves;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", playerName='" + playerName + '\'' +
                ", chips=" + chips +
                ", roundsPlayed=" + roundsPlayed +
                ", rightMoves=" + rightMoves +
                ", wrongMoves=" + wrongMoves +
                '}';
    }


}

