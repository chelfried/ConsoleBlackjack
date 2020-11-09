package com.helfried.blackjack;

import java.util.Scanner;

import static com.helfried.blackjack.TableActions.*;
import static com.helfried.blackjack.Table.*;
import static com.helfried.blackjack.PlayerActions.*;

public class Player{

    static String name;
    static int id;
    static int playerChips;
    static int playingSplitHand;
    static boolean alreadyHit;
    static int remainingHints;

    public Player(String name, int playerChips) {
        super();
        name = name;
        playerChips = playerChips;
    }

    public static void playerMove() {
        System.out.print("[H] Hit   ");

        if (!alreadyHit && ((playingSplitHand <= 1 && playerChips >= pot) || (playingSplitHand == 2 && playerChips >= splitPot))) {
            System.out.print("[D] Double   ");
        }
        if (playingSplitHand == 0 && playerHand.size() == 2 && (handValue(playerHand) == playerHand.get(0).getValue() * 2 || (handValue(playerHand) == 12 && holdsA(playerHand)))) {
            System.out.print("[P] Split   ");
        }
        System.out.print("[S] Stand      ");
        if (remainingHints > 0) {
            System.out.printf("[I] Hint (%d)", remainingHints);
        }
        Scanner keyboard = new Scanner(System.in);
        while (true) {
            if (playingSplitHand == 1) {
                System.out.print("\nPick action on 1st hand: ");
            } else if (playingSplitHand == 2) {
                System.out.print("\nPick action on 2nd hand: ");
            } else {
                System.out.print("\nPick action: ");
            }
            String input = keyboard.nextLine();
            if (input.equals("h") || input.equals("H")) {
                System.out.println();
                hits(playerHand);
                break;
            }
            if ((input.equals("d") || input.equals("D")) && !alreadyHit && ((playingSplitHand <= 1 && playerChips >= pot) || (playingSplitHand == 2 && playerChips >= splitPot))) {
                System.out.println();
                doubles();
                if (handValue(playerHand) > 21) {
                    bust(playerHand);
                }
                break;
            }
            if ((input.equals("p") || input.equals("P")) && playingSplitHand == 0 && playerHand.size() == 2 && (handValue(playerHand) == playerHand.get(0).getValue() * 2) && playerChips >= pot) {
                System.out.println();
                splits();
                break;
            }
            if (input.equals("s") || input.equals("S")) {
                System.out.println();
                stands();
                break;
            }
            if ((input.equals("i") || input.equals("I")) && remainingHints > 0) {
                System.out.println();
                hint();
            }
        }
    }

    public static void hint() {
        System.out.print("\033[0;34mAccording to basic strategy you should \033[4;34m");
        if (playingSplitHand == 0 && playerHand.size() == 2 && (handValue(playerHand) == playerHand.get(0).getValue() * 2)) {
            String hint = BasicStrategyTable.splitHand[playerHand.get(0).getValue() - 2][dealerHand.get(0).getValue() - 2];
            System.out.printf("\033[0;34m%s.\033[0m\n", hint);
        } else if (holdsA(playerHand)) {
            String hint = BasicStrategyTable.softHand[handValue(playerHand) - 13][dealerHand.get(0).getValue() - 2];
            System.out.printf("\033[0;34m%s.\033[0m\n", hint);
        } else {
            String hint = BasicStrategyTable.hardHand[handValue(playerHand) - 4][dealerHand.get(0).getValue() - 2];
            System.out.printf("\033[0;34m%s.\033[0m\n", hint);
        }
        remainingHints -= 1;
    }

}
