package com.luckbox.holdem.models.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by danielseetoh on 5/7/19.
 */
public class GameEvaluator {
    // to refactor and get rid of magic numbers

    public List<Player> getWinningPlayers(Player[] players, Card[] communityCards) {
        // get best combo of each player

        // compare best combos and rank the players hands

        // return a winning player or the winning players
        return null;
    }

    public static List<Card> getBestCombo(Card[] cardsArr) {

        List<Card> cardsList = Arrays.asList(cardsArr);
        Collections.sort(cardsList);
        List<Card> bestCombo = null;

        // Possible to generalize the rules for different games?

        // search for highest straight flush
        bestCombo = getHighestStraightFlush(cardsList);
        // search for quads and other highest card

        // search for highest full house
        // search for highest flush
        // search for highest straight
        // search for trips and other highest 2 cards
        // search for two pair and other highest remaining card
        // search for pair and other highest 3 cards
        // search for highest 5 cards

        return bestCombo;
    }

    public static List<Card> getHighestStraightFlush(List<Card> sortedCardsList) {
        List<Card> straightFlushCards = new ArrayList<>();
        Card currentCard = null, prevCard = null;

        straightFlushCards.add(sortedCardsList.get(sortedCardsList.size() - 1));

        // iterate through the cards from top down
        for (int i = sortedCardsList.size() - 2; i >= 0; i--) {
            currentCard = sortedCardsList.get(i);
            prevCard = straightFlushCards.get(0);
            if (currentCard.number == prevCard.number) {
                continue;
            }

            if (currentCard.suit == prevCard.suit &&
                currentCard.number.ordinal() == prevCard.number.ordinal() - 1) {
                straightFlushCards.add(0, currentCard);
            } else {
                if (straightFlushCards.size() >= 5) {
                    break;
                }
                straightFlushCards.clear();
                straightFlushCards.add(currentCard);
            }
        }

        // handle a wheel (e.g. ace to five)
        if (straightFlushCards.size() == 4) {
            currentCard = straightFlushCards.get(0);
            Card aceWheelCard = new Card(CardNumber.ace, currentCard.suit);
            if (currentCard.number == CardNumber.two && sortedCardsList.contains(aceWheelCard)) {
                straightFlushCards.add(0, aceWheelCard);
            }
        }

        int straightFlushNumCards = straightFlushCards.size();

        if (straightFlushNumCards >= 5) {
            return straightFlushCards.subList(straightFlushNumCards - 5, straightFlushNumCards);
        }

        return null;
    }
    
    public static List<Card> getQuads(List<Card> sortedCardsList) {
        int consecutiveSameNumber = 1;
        Card prevCard = sortedCardsList.get(0), currentCard = null;
        
        for (int i = 1; i < sortedCardsList.size(); i++) {
            currentCard = sortedCardsList.get(i);
            if (currentCard.number == prevCard.number) {
                consecutiveSameNumber++;
            } else {
                consecutiveSameNumber = 1;
            }
            if (consecutiveSameNumber >= 4) {
                return sortedCardsList.subList(i - 3, i + 1);
            }
            prevCard = currentCard;
        }
        
        return null;
    }
}
