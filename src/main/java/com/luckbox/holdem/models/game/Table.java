package com.luckbox.holdem.models.game;

import java.util.ArrayList;

/**
 * Created by danielseetoh on 5/5/19.
 */
public class Table {

    public Deck deck;
    public ArrayList<Player> players;
    public int maxPlayers;

    public Table(int maxPlayers) {
        this.deck = new Deck();
        this.maxPlayers = maxPlayers;
    }

    public void addPlayer(Player player) {
        if (players.size() < maxPlayers) {
            players.add(player);
        } else {
            // TODO: handle error
            // add logging
        }
    }

    public void removePlayer(Player player) {
        // TODO
    }
}
