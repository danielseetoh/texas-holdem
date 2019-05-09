package com.luckbox.holdem.models.game;

/**
 * Created by danielseetoh on 5/5/19.
 */
public class Card {

    public final CardNumber number;
    public final CardSuit suit;

    public Card(CardNumber num, CardSuit suit) {
        this.number = num;
        this.suit = suit;
    }
}
