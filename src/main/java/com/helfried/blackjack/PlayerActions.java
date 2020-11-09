package com.helfried.blackjack;

import javafx.util.Pair;

import java.util.List;
import java.util.Scanner;

import static com.helfried.blackjack.TableActions.*;
import static com.helfried.blackjack.Dealer.*;
import static com.helfried.blackjack.Table.*;
import static com.helfried.blackjack.Player.*;

public class PlayerActions {

    public PlayerActions() {
        throw new IllegalStateException("Utility class");
    }

    public static void placeBet() {
        System.out.printf("\n\u001B[33mCHIPS %d\033[0m\n\n", playerChips);
        Scanner input = new Scanner(System.in);
        int bet;
        System.out.println("Input 0 to exit the game or...");
        while (true) {
            System.out.print("Place a bet: ");
            try {
                bet = input.nextInt();
                if (bet == 0) {
                    System.exit(0);
                }
                if (bet <= playerChips && bet > 0) {
                    break;
                } else {
                    System.out.printf("Min bet is 1, max bet is %d chips.\n", playerChips);
                }
            } catch (java.util.InputMismatchException e) {
                input.nextLine();
                System.out.printf("Min bet is 1, max bet is %d chips.\n", playerChips);
            }
        }
        playerChips -= bet;
        pot += bet;
        System.out.printf("\u001B[33m%d\033[0m chips are added to the pot.\n\n", bet);
        wait(2000);
    }

    public static void hits(List<Pair<String, Integer>> hand) {
        drawCard(hand);
        if (handValue(hand) > 21 && holdsA(hand)) {
            changeValueOfA(hand);
        }
        if (hand == playerHand) {
            alreadyHit = true;
            printAction(true, true, "hits!");
            printTable();
            if (handValue(playerHand) <= 21) {
                playerMove();
            } else {
                bust(playerHand);
            }
        }
        if (hand == dealerHand) {
            printAction(false, true, "Dealer hits!");
            printTable();
        }
    }

    public static void splits() {
        printAction(true, true, "splits!");
        System.out.printf("\u001B[33m%d\033[0m chips are placed in the 2nd pot.\n", pot);
        wait(1500);
        playingSplitHand = 1;
        splitPot = pot;
        playerChips -= splitPot;
        splitPlayerHand1.add(new Pair<>(playerHand.get(0).getKey(), playerHand.get(0).getValue()));
        splitPlayerHand2.add(new Pair<>(playerHand.get(1).getKey(), playerHand.get(1).getValue()));
        playerHand.remove(1);
        System.out.println("New card is dealt to 1st hand!");
        drawCard(playerHand);
        printTable();
        playerMove();
        playingSplitHand = 2;
        alreadyHit = false;
        playerHand.clear();
        playerHand.add(new Pair<>(splitPlayerHand2.get(0).getKey(), splitPlayerHand2.get(0).getValue()));
        System.out.println("New card is dealt to 2nd hand!");
        drawCard(playerHand);
        printTable();
        playerMove();
        handRevealed = true;
        if (handValue(splitPlayerHand1) > 21 && handValue(splitPlayerHand2) > 21) {
            clearTable();
        } else {
            Dealer.plays();
        }
    }

    public static void doubles() {
        if (playingSplitHand == 1) {
            drawCard(playerHand);
            if (handValue(playerHand) > 21 && holdsA(playerHand)) {
                changeValueOfA(playerHand);
            }
            playerChips -= pot;
            pot *= 2;
            printAction(true, true, "doubles on 1st hand!");
            wait(1500);
            System.out.printf("\u001B[33m%d\033[0m chips are added to 1st pot.\n", pot / 2);
            printTable();
        } else if (playingSplitHand == 2) {
            drawCard(playerHand);
            if (handValue(playerHand) > 21 && holdsA(playerHand)) {
                changeValueOfA(playerHand);
            }
            playerChips -= splitPot;
            splitPot *= 2;
            printAction(true, true, "doubles on 2nd hand!");
            wait(1500);
            System.out.printf("\u001B[33m%d\033[0m chips are added to 2nd pot.\n", splitPot / 2);
            printTable();
            dealerRevealsHand();
        } else {
            drawCard(playerHand);
            if (handValue(playerHand) > 21 && holdsA(playerHand))
                changeValueOfA(playerHand);
            playerChips -= pot;
            pot *= 2;
            printAction(true, true, "doubles!");
            wait(1500);
            System.out.printf("\u001B[33m%d\033[0m chips are added to pot.\n", pot / 2);
            printTable();
            dealerRevealsHand();
            plays();
        }
    }

    public static void stands() {
        if (handValue(playerHand) == 22 && holdsA(playerHand) && playerHand.size() == 2) {
            changeValueOfA(playerHand);
        }
        if (playingSplitHand == 1) {
            printAction(true, true, "stands on 1st hand!");
        } else if (playingSplitHand == 2) {
            printAction(true, true, "stands on 2nd hand!");
            dealerRevealsHand();
        } else {
            printAction(true, true, "stands!");
            dealerRevealsHand();
            plays();
        }
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}
