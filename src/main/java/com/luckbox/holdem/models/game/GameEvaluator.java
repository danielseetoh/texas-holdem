package com.luckbox.holdem.models.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
//        Card[][] cardsArrArr = new Card[][]{
//            {
//                new Card(CardNumber.ace, CardSuit.spades),
//                new Card(CardNumber.four, CardSuit.hearts),
//                new Card(CardNumber.three, CardSuit.hearts),
//                new Card(CardNumber.five, CardSuit.hearts),
//                new Card(CardNumber.seven, CardSuit.hearts),
//                new Card(CardNumber.six, CardSuit.hearts),
//                new Card(CardNumber.ace, CardSuit.hearts),
//            },
//            {
//                new Card(CardNumber.ace, CardSuit.spades),
//                new Card(CardNumber.four, CardSuit.hearts),
//                new Card(CardNumber.three, CardSuit.spades),
//                new Card(CardNumber.five, CardSuit.hearts),
//                new Card(CardNumber.seven, CardSuit.hearts),
//                new Card(CardNumber.six, CardSuit.hearts),
//                new Card(CardNumber.ace, CardSuit.hearts),
//            },
//            {
//                new Card(CardNumber.ace, CardSuit.hearts),
//                new Card(CardNumber.two, CardSuit.hearts),
//                new Card(CardNumber.three, CardSuit.hearts),
//                new Card(CardNumber.five, CardSuit.hearts),
//                new Card(CardNumber.four, CardSuit.hearts),
//                new Card(CardNumber.six, CardSuit.hearts),
//                new Card(CardNumber.ace, CardSuit.spades),
//            },
//            {
//                new Card(CardNumber.ace, CardSuit.hearts),
//                new Card(CardNumber.two, CardSuit.hearts),
//                new Card(CardNumber.three, CardSuit.hearts),
//                new Card(CardNumber.five, CardSuit.hearts),
//                new Card(CardNumber.four, CardSuit.hearts),
//                new Card(CardNumber.six, CardSuit.spades),
//                new Card(CardNumber.ace, CardSuit.spades),
//            },
//            {
//                new Card(CardNumber.ace, CardSuit.hearts),
//                new Card(CardNumber.jack, CardSuit.hearts),
//                new Card(CardNumber.queen, CardSuit.hearts),
//                new Card(CardNumber.ten, CardSuit.hearts),
//                new Card(CardNumber.king, CardSuit.hearts),
//                new Card(CardNumber.six, CardSuit.spades),
//                new Card(CardNumber.ace, CardSuit.spades),
//            },
//            {
//                new Card(CardNumber.two, CardSuit.hearts),
//                new Card(CardNumber.two, CardSuit.spades),
//                new Card(CardNumber.three, CardSuit.hearts),
//                new Card(CardNumber.six, CardSuit.hearts),
//                new Card(CardNumber.five, CardSuit.hearts),
//                new Card(CardNumber.six, CardSuit.spades),
//                new Card(CardNumber.four, CardSuit.hearts),
//            },
//            {
//                new Card(CardNumber.ten, CardSuit.hearts),
//                new Card(CardNumber.eight, CardSuit.spades),
//                new Card(CardNumber.ace, CardSuit.hearts),
//                new Card(CardNumber.six, CardSuit.hearts),
//                new Card(CardNumber.five, CardSuit.hearts),
//                new Card(CardNumber.six, CardSuit.spades),
//                new Card(CardNumber.four, CardSuit.hearts),
//            }
//        };
        List<Card> cardsList = Arrays.asList(cardsArr);
        Collections.sort(cardsList);
        List<Card> bestCombo = null;

        // Possible to generalize the rules for different games?

        // search for highest straight flush
        bestCombo = getHighestStraightFlush(cardsList);
//        for (Card[] cards : cardsArrArr) {
//            List<Card> cardsList = Arrays.asList(cards);
//            Collections.sort(cardsList);
//
//            bestCombo = getHighestStraightFlush(cardsList);
//        }

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

    public static List<Card> getHighestStraightFlush(List<Card> cardsList) {
        List<Card> straightFlushCards = new ArrayList<>();
        Card currentCard = null, prevCard = null;

        straightFlushCards.add(cardsList.get(cardsList.size() - 1));

        // iterate through the cards from top down
        for (int i = cardsList.size() - 2; i >= 0; i--) {
            currentCard = cardsList.get(i);
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
            if (currentCard.number == CardNumber.two && cardsList.contains(aceWheelCard)) {
                straightFlushCards.add(0, aceWheelCard);
            }
        }

        int straightFlushNumCards = straightFlushCards.size();

        if (straightFlushNumCards >= 5) {
            return straightFlushCards.subList(straightFlushNumCards - 5, straightFlushNumCards);
        }

        return null;
    }
}
