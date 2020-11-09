package com.helfried.blackjack;

import static com.helfried.blackjack.PlayerActions.*;
import static com.helfried.blackjack.TableActions.*;
import static com.helfried.blackjack.Table.*;
import static com.helfried.blackjack.Player.*;

public class Dealer {

    static boolean handRevealed;
    static boolean stood;

    public static void plays() {
        while (true) {
            if (handValue(dealerHand) <= 16) {
                hits(dealerHand);
            } else if (handValue(dealerHand) == 17 && holdsA(dealerHand)) {
                changeValueOfA(dealerHand);
                hits(dealerHand);
            } else if (handValue(dealerHand) > 21 && holdsA(dealerHand)) {
                changeValueOfA(dealerHand);
            } else if (handValue(dealerHand) <= 21) {
                printAction(false, true, "Dealer stands!");
                stood = true;
                endRound();
                break;
            } else {
                if (playingSplitHand == 0) {
                    bust(dealerHand);
                } else {
                    printAction(false, false, "Dealer busts!");
                    endRound();
                }
                break;
            }
        }
    }

}
