package com.luckbox.holdem.components.game;

import java.util.ArrayList;

/**
 * Created by danielseetoh on 5/5/19.
 */
public class Deck {

    public ArrayList<Card> deck;

    public Deck() {
        this.deck = new ArrayList<>();
        for(CardNumber num : CardNumber.values()) {
            for(CardSuit suit : CardSuit.values()) {
                this.deck.add(new Card(num, suit));
            }
        }
    }

    public Card dealCard() {
        // truely randomly pick a card, remove from deck, and return it
        return null;
    }
}
