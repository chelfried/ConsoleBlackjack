package com.helfried.blackjack;

import com.helfried.blackjack.types.Player;
import com.helfried.blackjack.types.PlayerDao;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {

        menu();

    }

    public static void menu() {
//        System.out.print("\nWelcome to\n");
//        wait(1000);
//        System.out.print("\033[1;91mB\033[0m");
//        wait(300);
//        System.out.print("\033[1;92m L\033[0m");
//        wait(300);
//        System.out.print("\033[1;93m A\033[0m");
//        wait(300);
//        System.out.print("\033[1;94m C\033[0m");
//        wait(300);
//        System.out.print("\033[1;95m K\033[0m");
//        wait(300);
//        System.out.print("\033[1;96m J\033[0m");
//        wait(300);
//        System.out.print("\033[1;91m A\033[0m");
//        wait(300);
//        System.out.print("\033[1;92m C\033[0m");
//        wait(300);
//        System.out.print("\033[1;93m K\033[0m");
//        System.out.print("\n\n");
//        wait(1000);
//        System.out.println("Table Rules\n♦ Blackjack pays 2 to 1\n♦ Dealer hits on soft 17\n♦ Splitting allowed once per round\n♦ Deck is reshuffled after each round\n");
//        wait(1000);
        System.out.println("[N] NEW GAME");
        System.out.println("[L] LOAD GAME\n");
        Scanner input1 = new Scanner(System.in);
        while (true) {
            System.out.print("Choice: ");
            String choice = input1.nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                System.out.print("\nStarting new game");
                wait(500);
                System.out.print(".");
                wait(500);
                System.out.print(".");
                wait(500);
                System.out.print(".\n\n");
                wait(1000);
                startNewGame();
                break;
            }
            if (choice.equals("l") || choice.equals("L")) {
                System.out.println("");
                List<Player> players = PlayerDao.listPlayers();
                for(Player player : players){
                    System.out.printf("[%d] %-15s %d chips, %d rounds played\n",
                            player.getId(), player.getPlayerName(), player.getChips(), player.getRoundsPlayed());
                }
                Scanner input2 = new Scanner(System.in);
                int chosenID;
                while (true) {
                    System.out.print("\nLoad player number: ");
                    try {
                        chosenID = input2.nextInt();
                        if (chosenID >= players.get(0).getId() && chosenID <= players.get(players.size() - 1).getId()) {
                            break;
                        } else {
                            System.out.print("Please choose a valid number.\n");
                        }
                    } catch (java.util.InputMismatchException e) {
                        input2.nextInt();
                        System.out.print("Please choose a valid number.\n");
                    }
                }
                Blackjack.Player.name = players.get(chosenID - 1).getPlayerName();
                Blackjack.Player.playerChips = players.get(chosenID - 1).getChips();
                System.out.printf("\nLoading player %s...\n", Blackjack.Player.name);
                wait(2000);
                Blackjack.Game.start();
                break;
            }
        }
    }

    public static void startNewGame() {
        System.out.print("Please enter a new player name with no spaces and max 15 characters.\n");
        wait(2000);
        Scanner keyboard = new Scanner(System.in);
        while (true) {
            System.out.print("New Username: ");
            String input = keyboard.nextLine();
            if (input.length() < 16 && !input.equals("\n") && !input.isEmpty() && !input.contains(" ")) {
                System.out.print("\nGreat! ");
                wait(1000);
                System.out.printf("Your new player name is set to %s!", input);
                wait(3000);
                System.out.print("\nYou will start with \u001B[33m10000\033[0m chips. ");
                wait(3000);
                System.out.print("Good luck!\n");
                wait(3000);
                Blackjack.Player.id = PlayerDao.createNewPlayer(input);
                Blackjack.Player.playerChips = 10000;
                Blackjack.Game.start();
                break;
            }
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

