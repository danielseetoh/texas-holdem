package com.luckbox.holdem.models.game;

import java.util.Arrays;
import java.util.List;

/**
 * Created by danielseetoh on 5/7/19.
 */
public class GameEvaluator {

    public Player[] getWinningPlayers(Player[] players, Card[] communityCards) {
        // get best combo of each player

        // compare best combos and rank the players hands

        // return a winning player or the winning players
        return null;
    }

    public Card[] getBestCombo(Player player, Card[] communityCards) {

        List<Card> list = Arrays.asList(communityCards);
        list.addAll(Arrays.asList(player.hand));

        // search for highest straight flush
        // search for quads and other highest card
        // search for highest full house
        // search for highest flush
        // search for highest straight
        // search for trips and other highest 2 cards
        // search for two pair and other highest remaining card
        // search for pair and other highest 3 cards
        // search for highest 5 cards

        return null;
    }
}
