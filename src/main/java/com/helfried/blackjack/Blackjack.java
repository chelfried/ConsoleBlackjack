package com.helfried.blackjack;

import com.helfried.blackjack.types.PlayerDao;

import static com.helfried.blackjack.Player.*;
import static com.helfried.blackjack.PlayerActions.placeBet;
import static com.helfried.blackjack.Table.initializeNewDeck;
import static com.helfried.blackjack.TableActions.dealNewHands;

public class Blackjack {

    //TODO implement surrender functionality

    public static void main(String[] args) {

        Menu.loadMenu();

    }

    public static class Game {

        public static void start() {
            int roundNo = 0;
            while (true) {
                roundNo++;
                System.out.printf("\n•••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••• ROUND %d •••\n", roundNo);
                initializeNewDeck();
                placeBet();
                dealNewHands();
                PlayerDao.updatePlayerStats(id, playerChips, roundNo, remainingHints);
            }
        }

    }


}



