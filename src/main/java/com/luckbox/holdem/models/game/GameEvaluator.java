package com.luckbox.holdem.models.game;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by danielseetoh on 5/7/19.
 */
public class GameEvaluator {
    public List<Player> getWinningPlayers(Player[] players, Card[] communityCards) {
        // get best combo of each player

        // compare best combos and rank the players hands

        // return a winning player or the winning players
        return null;
    }

    public static List<Card> getBestCombo(Card[] cardsArr) {

        List<Card> cardsList = Arrays.asList(cardsArr);
        Collections.sort(cardsList);

        // Possible to generalize the rules for different games?

        // search for highest combo
        List<Card> bestCombo = Stream.<Supplier<List<Card>>> of (
            () -> getHighestStraightFlush(cardsList),
            () -> getQuads(cardsList))
            .map(Supplier::get)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);

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
        int indexOfSecondCardFromTop = sortedCardsList.size() - 2;
        int STRAIGHT_FLUSH_MIN_CARDS = 5;

        straightFlushCards.add(sortedCardsList.get(sortedCardsList.size() - 1));

        // iterate through the cards from top down
        for (int i = indexOfSecondCardFromTop; i >= 0; i--) {
            currentCard = sortedCardsList.get(i);
            prevCard = straightFlushCards.get(0);
            if (currentCard.number == prevCard.number) {
                continue;
            }

            if (currentCard.suit == prevCard.suit &&
                currentCard.number.ordinal() == prevCard.number.ordinal() - 1) {
                straightFlushCards.add(0, currentCard);
            } else {
                if (straightFlushCards.size() >= STRAIGHT_FLUSH_MIN_CARDS) {
                    break;
                }
                straightFlushCards.clear();
                straightFlushCards.add(currentCard);
            }
        }

        // handle a wheel (e.g. ace to five)
        if (straightFlushCards.size() == STRAIGHT_FLUSH_MIN_CARDS - 1) {
            currentCard = straightFlushCards.get(0);
            Card aceWheelCard = new Card(CardNumber.ace, currentCard.suit);
            if (currentCard.number == CardNumber.two && sortedCardsList.contains(aceWheelCard)) {
                straightFlushCards.add(0, aceWheelCard);
            }
        }

        int straightFlushNumCards = straightFlushCards.size();

        if (straightFlushNumCards >= STRAIGHT_FLUSH_MIN_CARDS) {
            return straightFlushCards.subList(straightFlushNumCards - STRAIGHT_FLUSH_MIN_CARDS, straightFlushNumCards);
        }

        return null;
    }
    
    public static List<Card> getQuads(List<Card> sortedCardsList) {
        int consecutiveSameNumber = 1;
        int QUADS_NUM_CARDS = 4;
        Card prevCard = sortedCardsList.get(0), currentCard = null;
        
        for (int i = 1; i < sortedCardsList.size(); i++) {
            currentCard = sortedCardsList.get(i);
            if (currentCard.number == prevCard.number) {
                consecutiveSameNumber++;
            } else {
                consecutiveSameNumber = 1;
            }
            if (consecutiveSameNumber >= QUADS_NUM_CARDS) {
                return sortedCardsList.subList(i - (QUADS_NUM_CARDS - 1), i + 1);
            }
            prevCard = currentCard;
        }
        
        return null;
    }
}
