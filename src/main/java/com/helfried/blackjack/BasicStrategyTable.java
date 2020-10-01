package com.helfried.blackjack;

public class BasicStrategyTable {

    static String hh = "hit";
    static String ss = "stand";
    static String dh = "double if allowed, otherwise hit";
    static String ds = "double if allowed, otherwise stand";
    static String pp = "split";

    static String[] hard04 = {hh, hh, hh, hh, hh, hh, hh, hh, hh, hh};
    static String[] hard05 = {hh, hh, hh, hh, hh, hh, hh, hh, hh, hh};
    static String[] hard06 = {hh, hh, hh, hh, hh, hh, hh, hh, hh, hh};
    static String[] hard07 = {hh, hh, hh, hh, hh, hh, hh, hh, hh, hh};
    static String[] hard08 = {hh, hh, hh, dh, dh, hh, hh, hh, hh, hh};
    static String[] hard09 = {dh, dh, dh, dh, dh, hh, hh, hh, hh, hh};
    static String[] hard10 = {dh, dh, dh, dh, dh, dh, dh, dh, hh, hh};
    static String[] hard11 = {dh, dh, dh, dh, dh, dh, dh, dh, dh, dh};
    static String[] hard12 = {hh, hh, ss, ss, ss, hh, hh, hh, hh, hh};
    static String[] hard13 = {ss, ss, ss, ss, ss, hh, hh, hh, hh, hh};
    static String[] hard14 = {ss, ss, ss, ss, ss, hh, hh, hh, hh, hh};
    static String[] hard15 = {ss, ss, ss, ss, ss, hh, hh, hh, hh, hh};
    static String[] hard16 = {ss, ss, ss, ss, ss, hh, hh, hh, hh, hh};
    static String[] hard17 = {ss, ss, ss, ss, ss, ss, ss, ss, ss, ss};
    static String[] hard18 = {ss, ss, ss, ss, ss, ss, ss, ss, ss, ss};
    static String[] hard19 = {ss, ss, ss, ss, ss, ss, ss, ss, ss, ss};
    static String[] hard20 = {ss, ss, ss, ss, ss, ss, ss, ss, ss, ss};
    static String[] hard21 = {ss, ss, ss, ss, ss, ss, ss, ss, ss, ss};

    static String[] soft13 = {hh, hh, dh, dh, dh, hh, hh, hh, hh, hh};
    static String[] soft14 = {hh, hh, dh, dh, dh, hh, hh, hh, hh, hh};
    static String[] soft15 = {hh, hh, dh, dh, dh, hh, hh, hh, hh, hh};
    static String[] soft16 = {hh, hh, dh, dh, dh, hh, hh, hh, hh, hh};
    static String[] soft17 = {dh, dh, dh, dh, dh, hh, hh, hh, hh, hh};
    static String[] soft18 = {ss, ds, ds, ds, ds, ss, ss, hh, hh, hh};
    static String[] soft19 = {ss, ss, ss, ss, ds, ss, ss, ss, ss, ss};
    static String[] soft20 = {ss, ss, ss, ss, ss, ss, ss, ss, ss, ss};
    static String[] soft21 = {ss, ss, ss, ss, ss, ss, ss, ss, ss, ss};

    static String[] split02 = {pp, pp, pp, pp, pp, pp, hh, hh, hh, hh};
    static String[] split03 = {pp, pp, pp, pp, pp, pp, pp, hh, hh, hh};
    static String[] split04 = {hh, hh, pp, pp, pp, hh, hh, hh, hh, hh};
    static String[] split05 = {dh, dh, dh, dh, dh, dh, dh, dh, hh, hh};
    static String[] split06 = {pp, pp, pp, pp, pp, pp, hh, hh, hh, hh};
    static String[] split07 = {pp, pp, pp, pp, pp, pp, pp, hh, ss, hh};
    static String[] split08 = {pp, pp, pp, pp, pp, pp, pp, pp, pp, pp};
    static String[] split09 = {pp, pp, pp, pp, pp, ss, pp, pp, ss, pp};
    static String[] split10 = {ss, ss, ss, ss, ss, ss, ss, ss, ss, ss};
    static String[] split11 = {pp, pp, pp, pp, pp, pp, pp, pp, pp, pp};

    static String[][] hardHand = {hard04, hard05, hard06, hard07, hard08, hard09, hard10, hard11, hard12, hard13, hard14, hard15, hard16, hard17, hard18, hard19, hard20, hard21};
    static String[][] softHand = {soft13, soft14, soft15, soft16, soft17, soft18, soft19, soft20, soft21};
    static String[][] splitHand = {split02, split03, split04, split05, split06, split07, split08, split09, split10, split11};

}
