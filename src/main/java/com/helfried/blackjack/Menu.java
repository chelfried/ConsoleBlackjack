package com.helfried.blackjack;

import com.helfried.blackjack.types.PlayerDao;
import com.helfried.blackjack.types.PlayerData;
import static com.helfried.blackjack.Player.*;
import static com.helfried.blackjack.types.PlayerDao.*;
import static com.helfried.blackjack.types.PlayerData.*;

import java.util.List;
import java.util.Scanner;

public class Menu {

    public static void loadMenu() {
        System.out.println("B L A C K J A C K\n");
        System.out.println("Table Rules\n♦ Blackjack pays 2 to 1\n♦ Dealer hits on soft 17\n♦ Splitting allowed once per round\n♦ Deck is reshuffled after each round\n");
        System.out.println("[N] NEW GAME");
        System.out.println("[L] LOAD GAME\n");
        Scanner input1 = new Scanner(System.in);
        while (true) {
            System.out.print("Choice: ");
            String choice = input1.nextLine();
            if (choice.equals("n") || choice.equals("N")) {
                startNewGame();
                break;
            } else if (choice.equals("l") || choice.equals("L")) {
                System.out.println();
                List<PlayerData> players = PlayerDao.listPlayers();
                for(PlayerData player : players){
                    System.out.printf("[%d] %-15s %d chips, %d rounds played, %d remaining hints\n",
                            player.getId(), player.getPlayerName(), player.getChips(), player.getRoundsPlayed(), player.getRemainingHints());
                }
                Scanner input2 = new Scanner(System.in);
                int chosenID;
                while (true) {
                    System.out.print("\nLoad player number: ");
                    try {
                        chosenID = input2.nextInt();
                        if (checkForId(chosenID)) {
                            break;
                        } else {
                            System.out.print("Please choose a valid number.\n");
                        }
                    } catch (java.util.InputMismatchException e) {
                        input2.nextLine();
                        System.out.print("Please choose a valid number.\n");
                    }
                }
                id = players.get(chosenID - 1).getId();
                name = players.get(chosenID - 1).getPlayerName();
                playerChips = players.get(chosenID - 1).getChips();
                remainingHints = players.get(chosenID - 1).getRemainingHints();
                Blackjack.Game.start();
                break;
            }
        }
    }

    public static void startNewGame() {
        System.out.print("Please enter a new player name with no spaces and max 15 characters.\n");
        Scanner keyboard = new Scanner(System.in);
        while (true) {
            System.out.print("New Username: ");
            String playerName = keyboard.nextLine();
            if (playerName.length() < 16 && !playerName.equals("\n") && !playerName.isEmpty() && !playerName.contains(" ")) {
                System.out.print("\nGreat! ");
                System.out.printf("Your new player name is set to %s!", playerName);
                System.out.print("\nYou will start with \u001B[33m10000\033[0m chips. ");
                id = createNewPlayer(playerName);
                name = playerName;
                playerChips = 10000;
                remainingHints = 5;
                Blackjack.Game.start();
                break;
            }
        }
    }

}

