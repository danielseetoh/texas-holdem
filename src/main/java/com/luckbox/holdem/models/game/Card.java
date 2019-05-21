package com.luckbox.holdem.models.game;

/**
 * Created by danielseetoh on 5/5/19.
 */
public class Card implements Comparable<Card> {

    public final CardNumber number;
    public final CardSuit suit;

    public Card(CardNumber num, CardSuit suit) {
        this.number = num;
        this.suit = suit;
    }

    @Override
    public int compareTo(Card otherCard) {
        CardNumber otherCardNumber = otherCard.number;
        CardSuit otherCardSuit = otherCard.suit;

        return this.number.compareTo(otherCardNumber) == 0 ? this.suit.compareTo(otherCardSuit) : this.number.compareTo(otherCardNumber);
    }

    @Override
    public String toString() {
        return "Number: " + this.number + "      Suit: " + this.suit;
    }
}
