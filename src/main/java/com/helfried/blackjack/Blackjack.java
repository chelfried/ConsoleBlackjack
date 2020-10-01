package com.helfried.blackjack;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Blackjack {

    //TODO disallow double and split when not enough chips
    //TODO implement surrender functionality
    //TODO implement end game functionality before placing a bet

    public static void main(String[] args) {

        Menu.loadMenu();

    }

    public static class Player extends Actions {

        static String name;
        static int id;
        static int playerChips;
        static int playingSplitHand;
        static boolean alreadyHit;

        public Player(String name, int playerChips) {
            Player.name = name;
            Player.playerChips = playerChips;
        }

        public static void playerMove() {
            System.out.print("[H] Hit   ");
            if (!alreadyHit) {
                System.out.print("[D] Double   ");
            }
            if (Player.playingSplitHand == 0 && Table.playerHand.size() == 2 && (handValue(Table.playerHand) == Table.playerHand.get(0).getValue() * 2 || (handValue(Table.playerHand) == 12 && holdsA(Table.playerHand)))) {
                System.out.print("[P] Split   ");
            }
            System.out.print("[S] Stand      ");
            System.out.print("[I] Hint");
            Scanner keyboard = new Scanner(System.in);
            while (true) {
                if (Player.playingSplitHand == 1) {
                    System.out.print("\nPick action on 1st hand: ");
                } else if (Player.playingSplitHand == 2) {
                    System.out.print("\nPick action on 2nd hand: ");
                } else {
                    System.out.print("\nPick action: ");
                }
                String input = keyboard.nextLine();
                if (input.equals("h") || input.equals("H")) {
                    System.out.println();
                    hits(Table.playerHand);
                    break;
                }
                if ((input.equals("d") || input.equals("D")) && !alreadyHit) {
                    System.out.println();
                    doubles();
                    if (handValue(Table.playerHand) > 21) {
                        bust(Table.playerHand);
                    }
                    break;
                }
                if ((input.equals("p") || input.equals("P")) && Player.playingSplitHand == 0 && Table.playerHand.size() == 2 && (handValue(Table.playerHand) == Table.playerHand.get(0).getValue() * 2)) {
                    System.out.println();
                    splits();
                    break;
                }
                if (input.equals("s") || input.equals("S")) {
                    System.out.println();
                    stands();
                    break;
                }
                if (input.equals("i") || input.equals("I")) {
                    System.out.println();
                    hint();
                }
            }
        }

        public static void hint() {
            System.out.print("\033[0;34mAccording to basic strategy you should \033[4;34m");
            if (Player.playingSplitHand == 0 && Table.playerHand.size() == 2 && (handValue(Table.playerHand) == Table.playerHand.get(0).getValue() * 2)) {
                String hint = BasicStrategyTable.splitHand[Table.playerHand.get(0).getValue() - 2][Table.dealerHand.get(0).getValue() - 2];
                System.out.printf("\033[0;34m%s.\n\033[4;34m", hint);
            }
            else if (holdsA(Table.playerHand)) {
                String hint = BasicStrategyTable.softHand[handValue(Table.playerHand) - 13][Table.dealerHand.get(0).getValue() - 2];
                System.out.printf("\033[0;34m%s.\n\033[4;34m", hint);
            }
            else {
                String hint = BasicStrategyTable.hardHand[handValue(Table.playerHand) - 4][Table.dealerHand.get(0).getValue() - 2];
                System.out.printf("\033[0;34m%s.\033[0m\n", hint);
            }
        }

    }

    public static class Dealer extends Actions {

        static boolean handRevealed;
        static boolean stood;

        public static void plays() {
            while (true) {
                if (handValue(Table.dealerHand) <= 16) {
                    hits(Table.dealerHand);
                } else if (handValue(Table.dealerHand) == 17 && holdsA(Table.dealerHand)) {
                    changeValueOfA(Table.dealerHand);
                    hits(Table.dealerHand);
                } else if (handValue(Table.dealerHand) > 21 && holdsA(Table.dealerHand)) {
                    changeValueOfA(Table.dealerHand);
                } else if (handValue(Table.dealerHand) <= 21) {
                    printAction(false, true, "Dealer Stands!");
                    stood = true;
                    endRound();
                    break;
                } else {
                    if (Player.playingSplitHand == 0) {
                        bust(Table.dealerHand);
                    } else {
                        printAction(false, false, "Dealer busts!");
                        endRound();
                    }
                    break;
                }
            }
        }

    }

    public static class Actions {

        public static void dealNewHands() {
            System.out.print("New hands are dealt!\n");
            wait(1500);
            for (int i = 0; i < 2; i++) {
                drawCard(Table.playerHand);
                drawCard(Table.dealerHand);
            }
            printTable();
            checkForBlackjack();
        }

        public static void drawCard(List<Pair<String, Integer>> hand) {
            hand.add(Table.deck.get(0));
            if (hand == Table.playerHand && Player.playingSplitHand != 0) {
                if (Player.playingSplitHand == 1) {
                    Table.splitPlayerHand1.add(new Pair<>(Table.playerHand.get(Table.playerHand.size() - 1).getKey(), Table.playerHand.get(Table.playerHand.size() - 1).getValue()));
                } else if (Player.playingSplitHand == 2) {
                    Table.splitPlayerHand2.add(new Pair<>(Table.playerHand.get(Table.playerHand.size() - 1).getKey(), Table.playerHand.get(Table.playerHand.size() - 1).getValue()));
                }
            }
            if (handValue(hand) > 21 && holdsA(hand)) {
                changeValueOfA(hand);
            }
            Table.deck.remove(0);
        }

        public static void printHand(List<Pair<String, Integer>> hand) {
            System.out.println();
            if (hand == Table.dealerHand) {
                System.out.println("Dealer");
                if (!Dealer.handRevealed) {
                    System.out.printf("\u001B[7m%s\u001B[0m \u001B[7m  \u001B[0m", Table.dealerHand.get(0).getKey());
                } else {
                    printCards(Table.dealerHand);
                }
            } else {
                if (Player.playingSplitHand != 0) {
                    System.out.printf("\u001B[33m%d\033[0m\n", Table.pot);
                    System.out.printf("%s's 1st hand\n", Player.name);
                    printCards(Table.splitPlayerHand1);
                    System.out.printf("\n\n\u001B[33m%d\033[0m\n", Table.splitPot);
                    System.out.printf("%s's 1st hand\n", Player.name);
                    printCards(Table.splitPlayerHand2);
                } else {
                    System.out.printf("\u001B[33m%d\033[0m\n", Table.pot);
                    System.out.printf("%s\n", Player.name);
                    printCards(Table.playerHand);
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
                if (hand == Table.splitPlayerHand1) {
                    totalSplit1 += card.getValue();
                } else if (hand == Table.splitPlayerHand2) {
                    totalSplit2 += card.getValue();
                } else {
                    total += card.getValue();
                }
                System.out.printf("%s%s%s ", REVERSE, card.getKey(), RESET);
            }
            if (hand == Table.splitPlayerHand1) {
                if (holdsA(Table.splitPlayerHand1) && !Dealer.handRevealed && Player.playingSplitHand == 1) {
                    System.out.printf("(%d/%d) ", totalSplit1 - 10, totalSplit1);
                } else {
                    System.out.printf("(%d) ", totalSplit1);
                }
            } else if (hand == Table.splitPlayerHand2) {
                if (holdsA(Table.splitPlayerHand2) && !Dealer.handRevealed) {
                    System.out.printf("(%d/%d) ", totalSplit2 - 10, totalSplit2);
                } else {
                    System.out.printf("(%d) ", totalSplit2);
                }
            } else {
                if (hand == Table.playerHand && holdsA(hand) && !Dealer.handRevealed) {
                    System.out.printf("(%d/%d) ", total - 10, total);
                } else if (hand == Table.dealerHand && holdsA(hand)) {
                    System.out.printf("(%d/%d) ", total - 10, total);
                } else {
                    System.out.printf("(%d) ", total);
                }
            }
        }

        public static void printTable() {
            System.out.print("\n..................................................................................");
            printHand(Table.dealerHand);
            System.out.println();
            printHand(Table.playerHand);
            System.out.print("..................................................................................\n");
            wait(2000);
        }

        public static void printAction(boolean player, Boolean colored, String action) {
            wait(500);
            if (colored) {
                if (player) {
                    System.out.printf("\u001B[35m%s\033[0m ", Player.name);
                }
                System.out.printf("\u001B[35m%s\033[0m\n", action);
            } else {
                if (player) {
                    System.out.printf("%s ", Player.name);
                }
                System.out.printf("%s\n", action);
            }
            wait(1000);
        }

        public static void placeBet() {
            System.out.printf("\n\u001B[33mCHIPS %d\033[0m\n\n", Player.playerChips);
            Scanner input = new Scanner(System.in);
            int bet;
            while (true) {
                System.out.print("Place a bet: ");
                try {
                    bet = input.nextInt();
                    if (bet <= Player.playerChips && bet > 0) {
                        break;
                    } else {
                        System.out.printf("Min bet is 1, max bet is %d chips.\n", Player.playerChips);
                    }
                } catch (java.util.InputMismatchException e) {
                    input.nextLine();
                    System.out.printf("Min bet is 1, max bet is %d chips.\n", Player.playerChips);
                }
            }
            Player.playerChips -= bet;
            Table.pot += bet;
            System.out.printf("\u001B[33m%d\033[0m chips are added to the pot.\n\n", bet);
            wait(2000);
        }

        public static void checkForBlackjack() {
            if ((Table.dealerHand.get(0).getValue() == 11 && Table.dealerHand.get(1).getValue() != 10) || (Table.dealerHand.get(0).getValue() == 10 && Table.dealerHand.get(1).getValue() != 11)) {
                System.out.print("Dealer checks his hand for a Blackjack. ");
                wait(1500);
                System.out.println("No Blackjack.\n");
                wait(1500);
            }
            if (handValue(Table.dealerHand) == 21 && handValue(Table.playerHand) == 21) {
                System.out.println();
                dealerRevealsHand();
                System.out.printf("Push! Pot of %d chips is returned to %s.", Table.pot, Player.name);
                Player.playerChips += Table.pot;
                clearTable();
            } else if (handValue(Table.playerHand) == 21) {
                dealerRevealsHand();
                System.out.printf("\n%s has a Blackjack!", Player.name);
                Player.playerChips += Table.pot + Table.pot * 2;
                clearTable();
            } else if (handValue(Table.dealerHand) == 21) {
                System.out.print("Dealer checks his hand for a Blackjack. ");
                wait(1500);
                System.out.println("Dealer has a Blackjack!");
                dealerRevealsHand();
                System.out.printf("\n%s loses round.\n", Player.name);
                clearTable();
            } else {
                Player.playerMove();
            }
        }

        public static void hits(List<Pair<String, Integer>> hand) {
            drawCard(hand);
            if (handValue(hand) > 21 && holdsA(hand)) {
                changeValueOfA(hand);
            }
            if (hand == Table.playerHand) {
                Player.alreadyHit = true;
                printAction(true, true, "hits!");
                printTable();
                if (handValue(Table.playerHand) <= 21) {
                    Player.playerMove();
                } else {
                    bust(Table.playerHand);
                }
            }
            if (hand == Table.dealerHand) {
                printAction(false, true, "Dealer hits!");
                printTable();
            }
        }

        public static void splits() {
            printAction(true, true, "splits!");
            System.out.printf("\u001B[33m%d\033[0m chips are placed in the 2nd pot.\n", Table.pot);
            wait(1500);
            Player.playingSplitHand = 1;
            Table.splitPot = Table.pot;
            Player.playerChips -= Table.splitPot;
            Table.splitPlayerHand1.add(new Pair<>(Table.playerHand.get(0).getKey(), Table.playerHand.get(0).getValue()));
            Table.splitPlayerHand2.add(new Pair<>(Table.playerHand.get(1).getKey(), Table.playerHand.get(1).getValue()));
            Table.playerHand.remove(1);
            System.out.println("New card is dealt to 1st hand!");
            drawCard(Table.playerHand);
            printTable();
            Player.playerMove();
            Player.playingSplitHand = 2;
            Player.alreadyHit = false;
            Table.playerHand.clear();
            Table.playerHand.add(new Pair<>(Table.splitPlayerHand2.get(0).getKey(), Table.splitPlayerHand2.get(0).getValue()));
            System.out.println("New card is dealt to 2nd hand!");
            drawCard(Table.playerHand);
            printTable();
            Player.playerMove();
            Dealer.handRevealed = true;
            if (handValue(Table.splitPlayerHand1) > 21 && handValue(Table.splitPlayerHand2) > 21) {
                clearTable();
            } else {
                Dealer.plays();
            }
        }

        public static void doubles() {
            if (Player.playingSplitHand == 1) {
                drawCard(Table.playerHand);
                if (handValue(Table.playerHand) > 21 && holdsA(Table.playerHand)) {
                    changeValueOfA(Table.playerHand);
                }
                Player.playerChips -= Table.pot;
                Table.pot *= 2;
                printAction(true, true, "doubles on 1st hand!");
                wait(1500);
                System.out.printf("\u001B[33m%d\033[0m chips are added to 1st pot.\n", Table.pot / 2);
                printTable();
            } else if (Player.playingSplitHand == 2) {
                drawCard(Table.playerHand);
                if (handValue(Table.playerHand) > 21 && holdsA(Table.playerHand)) {
                    changeValueOfA(Table.playerHand);
                }
                Player.playerChips -= Table.splitPot;
                Table.splitPot *= 2;
                printAction(true, true, "doubles on 2nd hand!");
                wait(1500);
                System.out.printf("\u001B[33m%d\033[0m chips are added to 2nd pot.\n", Table.splitPot / 2);
                printTable();
                dealerRevealsHand();
            } else {
                drawCard(Table.playerHand);
                if (handValue(Table.playerHand) > 21 && holdsA(Table.playerHand))
                    changeValueOfA(Table.playerHand);
                Player.playerChips -= Table.pot;
                Table.pot *= 2;
                printAction(true, true, "doubles!");
                wait(1500);
                System.out.printf("\u001B[33m%d\033[0m chips are added to pot.\n", Table.pot / 2);
                printTable();
                dealerRevealsHand();
                Dealer.plays();
            }
        }

        public static void stands() {
            if (handValue(Table.playerHand) == 22 && holdsA(Table.playerHand) && Table.playerHand.size() == 2) {
                changeValueOfA(Table.playerHand);
            }
            if (Player.playingSplitHand == 1) {
                printAction(true, true, "stands on 1st hand!");
            } else if (Player.playingSplitHand == 2) {
                printAction(true, true, "stands on 2nd hand!");
                dealerRevealsHand();
            } else {
                printAction(true, true, "stands!");
                dealerRevealsHand();
                Dealer.plays();
            }
        }

        public static void bust(List<Pair<String, Integer>> hand) {
            if (Player.playingSplitHand == 1 && hand == Table.playerHand) {
                printAction(true, false, "busts on 1st hand!");
                printAction(true, false, "loses pot on 1st hand.");
                Table.pot = 0;
            } else if (Player.playingSplitHand == 2 && hand == Table.playerHand) {
                printAction(true, false, "busts on 2nd hand!");
                printAction(true, false, "loses pot on 2nd hand.");
                Table.splitPot = 0;
                dealerRevealsHand();
            } else if (Player.playingSplitHand == 0 && hand == Table.playerHand) {
                printAction(true, false, "busts!");
                printAction(true, false, "loses pot.");
                Table.pot = 0;
                dealerRevealsHand();
                clearTable();
            } else {
                Player.playerChips += Table.pot * 2;
                printAction(false, false, "Dealer busts!");
                printAction(true, false, "wins!");
                clearTable();
            }
        }

        public static void dealerRevealsHand() {
            printAction(false, false, "Dealer reveals hand.");
            Dealer.handRevealed = true;
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
                    if (hand == Table.playerHand && Player.playingSplitHand == 1) {
                        Table.splitPlayerHand1.set(i, new Pair<>(hand.get(i).getKey(), 1));
                    } else if (hand == Table.playerHand && Player.playingSplitHand == 2) {
                        Table.splitPlayerHand2.set(i, new Pair<>(hand.get(i).getKey(), 1));
                    }
                    break;
                }
            }
        }

        public static void endRound() {
            if (Player.playingSplitHand == 0) {
                if (handValue(Table.dealerHand) == handValue(Table.playerHand)) {
                    Player.playerChips += Table.pot;
                    printAction(false, false, "Push! Pot goes back to Player.");
                } else if (handValue(Table.dealerHand) < handValue(Table.playerHand)) {
                    Player.playerChips += Table.pot * 2;
                    printAction(true, false, "wins!");
                } else {
                    printAction(true, false, "loses pot.");
                }
            } else {
                if (handValue(Table.splitPlayerHand1) <= 21) {
                    if (handValue(Table.dealerHand) == handValue(Table.splitPlayerHand1)) {
                        Player.playerChips += Table.pot;
                        printAction(false, false, "Push on 1st hand!");
                    } else if ((handValue(Table.dealerHand) < handValue(Table.splitPlayerHand1)) || handValue(Table.dealerHand) > 21) {
                        Player.playerChips += Table.pot * 2;
                        printAction(true, false, "wins on 1st hand!");
                    } else {
                        printAction(true, false, "loses pot on 1st hand!");
                    }
                }
                if (handValue(Table.splitPlayerHand2) <= 21) {
                    if (handValue(Table.dealerHand) == handValue(Table.splitPlayerHand2)) {
                        Player.playerChips += Table.splitPot;
                        printAction(false, false, "Push on 2nd hand!");
                    } else if ((handValue(Table.dealerHand) < handValue(Table.splitPlayerHand2)) || handValue(Table.dealerHand) > 21) {
                        Player.playerChips += Table.splitPot * 2;
                        printAction(true, false, "wins on 2nd hand!");
                    } else {
                        printAction(true, false, "loses pot on 2nd hand!");
                    }
                }
                if (handValue(Table.splitPlayerHand1) > 21 && handValue(Table.splitPlayerHand2) > 21) {
                    dealerRevealsHand();
                }
            }
            clearTable();
        }

        public static void clearTable() {
            Player.playingSplitHand = 0;
            Player.alreadyHit = false;
            Dealer.handRevealed = false;
            Dealer.stood = false;
            Table.playerHand.clear();
            Table.splitPlayerHand1.clear();
            Table.splitPlayerHand2.clear();
            Table.dealerHand.clear();
            Table.pot = 0;
            Table.splitPot = 0;
        }

        public static void wait(int ms) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public static class Table {

        static final List<Pair<String, Integer>> dealerHand = new ArrayList<>();
        static final List<Pair<String, Integer>> playerHand = new ArrayList<>();
        static final List<Pair<String, Integer>> splitPlayerHand1 = new ArrayList<>();
        static final List<Pair<String, Integer>> splitPlayerHand2 = new ArrayList<>();
        static final List<Pair<String, Integer>> deck = new ArrayList<>();
        static int pot;
        static int splitPot;

        public static void initializeNewDeck() {
            Table.deck.clear();
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
            Collections.shuffle(Table.deck);
        }

    }

    public static class Game {

        public static void start() {
            int roundNo = 0;
            while (true) {
                roundNo++;
                System.out.printf("\n•••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••• ROUND %d •••\n", roundNo);
                Table.initializeNewDeck();
                Player.placeBet();
                Dealer.dealNewHands();
            }
        }

    }


}



