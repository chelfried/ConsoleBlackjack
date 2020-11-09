package com.helfried.blackjack;

import javafx.util.Pair;
import java.util.List;

import static com.helfried.blackjack.Dealer.*;
import static com.helfried.blackjack.Table.*;
import static com.helfried.blackjack.Player.*;

public class TableActions {

    public TableActions() {
        throw new IllegalStateException("Utility class");
    }

    public static void dealNewHands() {
        System.out.print("New hands are dealt!\n");
        wait(1500);
        for (int i = 0; i < 2; i++) {
            drawCard(playerHand);
            drawCard(dealerHand);
        }
        printTable();
        checkForBlackjack();
    }

    public static void drawCard(List<Pair<String, Integer>> hand) {
        hand.add(deck.get(0));
        if (hand == playerHand && playingSplitHand != 0) {
            if (playingSplitHand == 1) {
                splitPlayerHand1.add(new Pair<>(playerHand.get(playerHand.size() - 1).getKey(), playerHand.get(playerHand.size() - 1).getValue()));
            } else if (playingSplitHand == 2) {
                splitPlayerHand2.add(new Pair<>(playerHand.get(playerHand.size() - 1).getKey(), playerHand.get(playerHand.size() - 1).getValue()));
            }
        }
        if (handValue(hand) > 21 && holdsA(hand)) {
            changeValueOfA(hand);
        }
        deck.remove(0);
    }

    public static void printHand(List<Pair<String, Integer>> hand) {
        System.out.println();
        if (hand == dealerHand) {
            System.out.println("Dealer");
            if (!handRevealed) {
                System.out.printf("\u001B[7m%s\u001B[0m \u001B[7m  \u001B[0m", dealerHand.get(0).getKey());
            } else {
                printCards(dealerHand);
            }
        } else {
            if (playingSplitHand != 0) {
                System.out.printf("\u001B[33m%d\033[0m\n", pot);
                System.out.printf("%s's 1st hand\n", name);
                printCards(splitPlayerHand1);
                System.out.printf("\n\n\u001B[33m%d\033[0m\n", splitPot);
                System.out.printf("%s's 1st hand\n", name);
                printCards(splitPlayerHand2);
            } else {
                System.out.printf("\u001B[33m%d\033[0m\n", pot);
                System.out.printf("%s\n", name);
                printCards(playerHand);
            }
            System.out.print("\n");
        }
    }

    public static void printCards(List<Pair<String, Integer>> hand) {
        final String REVERSE = "\u001B[7m";
        final String RESET = "\u001B[0m";
        int total = 0;
        int totalSplit1 = 0;
        int totalSplit2 = 0;
        for (Pair<String, Integer> card : hand) {
            if (hand == splitPlayerHand1) {
                totalSplit1 += card.getValue();
            } else if (hand == splitPlayerHand2) {
                totalSplit2 += card.getValue();
            } else {
                total += card.getValue();
            }
            System.out.printf("%s%s%s ", REVERSE, card.getKey(), RESET);
        }
        if (hand == splitPlayerHand1) {
            if (holdsA(splitPlayerHand1) && !handRevealed && playingSplitHand == 1) {
                System.out.printf("(%d/%d) ", totalSplit1 - 10, totalSplit1);
            } else {
                System.out.printf("(%d) ", totalSplit1);
            }
        } else if (hand == splitPlayerHand2) {
            if (holdsA(splitPlayerHand2) && !handRevealed) {
                System.out.printf("(%d/%d) ", totalSplit2 - 10, totalSplit2);
            } else {
                System.out.printf("(%d) ", totalSplit2);
            }
        } else {
            if (hand == playerHand && holdsA(hand) && !handRevealed) {
                System.out.printf("(%d/%d) ", total - 10, total);
            } else if (hand == dealerHand && holdsA(hand)) {
                System.out.printf("(%d/%d) ", total - 10, total);
            } else {
                System.out.printf("(%d) ", total);
            }
        }
    }

    public static void printTable() {
        System.out.print("\n..................................................................................");
        printHand(dealerHand);
        System.out.println();
        printHand(playerHand);
        System.out.print("..................................................................................\n");
        wait(2000);
    }

    public static void printAction(boolean player, Boolean colored, String action) {
        wait(500);
        if (Boolean.TRUE.equals(colored)) {
            if (player) {
                System.out.printf("\u001B[35m%s\033[0m ", name);
            }
            System.out.printf("\u001B[35m%s\033[0m\n", action);
        } else {
            if (player) {
                System.out.printf("%s ", name);
            }
            System.out.printf("%s\n", action);
        }
        wait(1000);
    }

    public static void checkForBlackjack() {
        if ((dealerHand.get(0).getValue() == 11 && dealerHand.get(1).getValue() != 10) || (dealerHand.get(0).getValue() == 10 && dealerHand.get(1).getValue() != 11)) {
            System.out.print("Dealer checks his hand for a Blackjack. ");
            wait(1500);
            System.out.println("No Blackjack.\n");
            wait(1500);
        }
        if (handValue(dealerHand) == 21 && handValue(playerHand) == 21) {
            System.out.println();
            dealerRevealsHand();
            System.out.printf("Push! Pot of %d chips is returned to %s.", pot, name);
            playerChips += pot;
            clearTable();
        } else if (handValue(playerHand) == 21) {
            dealerRevealsHand();
            System.out.printf("\n%s has a Blackjack!", name);
            playerChips += pot + pot * 2;
            clearTable();
        } else if (handValue(dealerHand) == 21) {
            System.out.print("Dealer checks his hand for a Blackjack. ");
            wait(1500);
            System.out.println("Dealer has a Blackjack!");
            dealerRevealsHand();
            System.out.printf("\n%s loses round.\n", name);
            clearTable();
        } else {
            playerMove();
        }
    }

    public static void bust(List<Pair<String, Integer>> hand) {
        if (playingSplitHand == 1 && hand == playerHand) {
            printAction(true, false, "busts on 1st hand!");
            printAction(true, false, "loses pot on 1st hand.");
            pot = 0;
        } else if (playingSplitHand == 2 && hand == playerHand) {
            printAction(true, false, "busts on 2nd hand!");
            printAction(true, false, "loses pot on 2nd hand.");
            splitPot = 0;
            dealerRevealsHand();
        } else if (playingSplitHand == 0 && hand == playerHand) {
            printAction(true, false, "busts!");
            printAction(true, false, "loses pot.");
            pot = 0;
            dealerRevealsHand();
            clearTable();
        } else {
            playerChips += pot * 2;
            printAction(false, false, "Dealer busts!");
            printAction(true, false, "wins!");
            clearTable();
        }
    }

    public static void dealerRevealsHand() {
        printAction(false, false, "Dealer reveals hand.");
        handRevealed = true;
        printTable();
    }

    public static int handValue(List<Pair<String, Integer>> hand) {
        int handValue = 0;
        for (Pair<String, Integer> card : hand) {
            handValue += card.getValue();
        }
        return handValue;
    }

    public static boolean holdsA(List<Pair<String, Integer>> hand) {
        for (Pair<String, Integer> card : hand) {
            if (card.getValue() == 11) {
                return true;
            }
        }
        return false;
    }

    public static void changeValueOfA(List<Pair<String, Integer>> hand) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue() == 11) {
                hand.set(i, new Pair<>(hand.get(i).getKey(), 1));
                if (hand == playerHand && playingSplitHand == 1) {
                    splitPlayerHand1.set(i, new Pair<>(hand.get(i).getKey(), 1));
                } else if (hand == playerHand && playingSplitHand == 2) {
                    splitPlayerHand2.set(i, new Pair<>(hand.get(i).getKey(), 1));
                }
                break;
            }
        }
    }

    public static void endRound() {
        if (playingSplitHand == 0) {
            if (handValue(dealerHand) == handValue(playerHand)) {
                playerChips += pot;
                printAction(false, false, "Push!");
            } else if (handValue(dealerHand) < handValue(playerHand)) {
                playerChips += pot * 2;
                printAction(true, false, "wins!");
            } else {
                printAction(true, false, "loses pot.");
            }
        } else {
            if (handValue(splitPlayerHand1) <= 21) {
                if (handValue(dealerHand) == handValue(splitPlayerHand1)) {
                    playerChips += pot;
                    printAction(false, false, "Push on 1st hand!");
                } else if ((handValue(dealerHand) < handValue(splitPlayerHand1)) || handValue(dealerHand) > 21) {
                    playerChips += pot * 2;
                    printAction(true, false, "wins on 1st hand!");
                } else {
                    printAction(true, false, "loses pot on 1st hand!");
                }
            }
            if (handValue(splitPlayerHand2) <= 21) {
                if (handValue(dealerHand) == handValue(splitPlayerHand2)) {
                    playerChips += splitPot;
                    printAction(false, false, "Push on 2nd hand!");
                } else if ((handValue(dealerHand) < handValue(splitPlayerHand2)) || handValue(dealerHand) > 21) {
                    playerChips += splitPot * 2;
                    printAction(true, false, "wins on 2nd hand!");
                } else {
                    printAction(true, false, "loses pot on 2nd hand!");
                }
            }
            if (handValue(splitPlayerHand1) > 21 && handValue(splitPlayerHand2) > 21) {
                dealerRevealsHand();
            }
        }
        clearTable();
    }

    public static void clearTable() {
        playingSplitHand = 0;
        alreadyHit = false;
        handRevealed = false;
        Dealer.stood = false;
        playerHand.clear();
        splitPlayerHand1.clear();
        splitPlayerHand2.clear();
        dealerHand.clear();
        pot = 0;
        splitPot = 0;
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}
