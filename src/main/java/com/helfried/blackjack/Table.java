package com.helfried.blackjack;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Table {

    private Table() {
        throw new IllegalStateException("Utility class");
    }

    static final List<Pair<String, Integer>> dealerHand = new ArrayList<>();
    static final List<Pair<String, Integer>> playerHand = new ArrayList<>();
    static final List<Pair<String, Integer>> splitPlayerHand1 = new ArrayList<>();
    static final List<Pair<String, Integer>> splitPlayerHand2 = new ArrayList<>();
    static final List<Pair<String, Integer>> deck = new ArrayList<>();
    static int pot;
    static int splitPot;

    public static void initializeNewDeck() {
        deck.clear();
        deck.add(new Pair<>("2♠", 2));
        deck.add(new Pair<>("2♥", 2));
        deck.add(new Pair<>("2♦", 2));
        deck.add(new Pair<>("2♣", 2));
        deck.add(new Pair<>("3♠", 3));
        deck.add(new Pair<>("3♥", 3));
        deck.add(new Pair<>("3♦", 3));
        deck.add(new Pair<>("3♣", 3));
        deck.add(new Pair<>("4♠", 4));
        deck.add(new Pair<>("4♥", 4));
        deck.add(new Pair<>("4♦", 4));
        deck.add(new Pair<>("4♣", 4));
        deck.add(new Pair<>("5♠", 5));
        deck.add(new Pair<>("5♥", 5));
        deck.add(new Pair<>("5♦", 5));
        deck.add(new Pair<>("5♣", 5));
        deck.add(new Pair<>("6♠", 6));
        deck.add(new Pair<>("6♥", 6));
        deck.add(new Pair<>("6♦", 6));
        deck.add(new Pair<>("6♣", 6));
        deck.add(new Pair<>("7♠", 7));
        deck.add(new Pair<>("7♥", 7));
        deck.add(new Pair<>("7♦", 7));
        deck.add(new Pair<>("7♣", 7));
        deck.add(new Pair<>("8♠", 8));
        deck.add(new Pair<>("8♥", 8));
        deck.add(new Pair<>("8♦", 8));
        deck.add(new Pair<>("8♣", 8));
        deck.add(new Pair<>("9♠", 9));
        deck.add(new Pair<>("9♥", 9));
        deck.add(new Pair<>("9♦", 9));
        deck.add(new Pair<>("9♣", 9));
        deck.add(new Pair<>("J♠", 10));
        deck.add(new Pair<>("J♥", 10));
        deck.add(new Pair<>("J♦", 10));
        deck.add(new Pair<>("J♣", 10));
        deck.add(new Pair<>("Q♠", 10));
        deck.add(new Pair<>("Q♥", 10));
        deck.add(new Pair<>("Q♦", 10));
        deck.add(new Pair<>("Q♣", 10));
        deck.add(new Pair<>("K♠", 10));
        deck.add(new Pair<>("K♥", 10));
        deck.add(new Pair<>("K♦", 10));
        deck.add(new Pair<>("K♣", 10));
        deck.add(new Pair<>("A♠", 11));
        deck.add(new Pair<>("A♥", 11));
        deck.add(new Pair<>("A♦", 11));
        deck.add(new Pair<>("A♣", 11));
        Collections.shuffle(deck);
    }

}