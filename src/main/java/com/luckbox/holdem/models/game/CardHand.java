package com.luckbox.holdem.models.game;

import java.util.List;

public class CardHand {
    public CardCombo combo;
    public List<Card> cards;
    
    public CardHand(CardCombo combo, List<Card> cards) {
        this.combo = combo;
        this.cards = cards;
    }
}
