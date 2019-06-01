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

    public static CardHand getBestCombo(Card[] cardsArr) {

        List<Card> cardsList = Arrays.asList(cardsArr);
        Collections.sort(cardsList);
    
        // optimizing can be done on this
        // search for highest combo
        CardHand bestHand = Stream.<Supplier<CardHand>> of (
            () -> getHighestStraightFlush(cardsList),
            () -> getQuads(cardsList),
            () -> getHighestFullHouse(cardsList),
            () -> getHighestFlush(cardsList),
            () -> getHighestStraight(cardsList),
            () -> getHighestTrips(cardsList),
            () -> getHighestTwoPair(cardsList),
            () -> getHighestPair(cardsList),
            () -> getHighestFiveCards(cardsList))
            .map(Supplier::get)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);

        return bestHand;
    }

    public static CardHand getHighestStraightFlush(List<Card> sortedCardsList) {
        List<Card> straightFlushCards = new ArrayList<>();
        Card currentCard = null, prevCard = null;
        int numRequiredCards = 5;
        
        // check for flush
        CardHand flushHand = getHighestFlush(sortedCardsList);
        CardSuit flushSuit = null;
        if (flushHand == null) {
            return null;
        } else {
            flushSuit = flushHand.cards.get(0).suit;
        }
        
        // iterate through the cards from top down
        for (int i = sortedCardsList.size() - 1; i >= 0; i--) {
            currentCard = sortedCardsList.get(i);
            if (currentCard.suit == flushSuit) {
                if (straightFlushCards.isEmpty()) {
                    straightFlushCards.add(currentCard);
                    continue;
                }
            } else {
                continue;
            }
            
            prevCard = straightFlushCards.get(0);

            if (currentCard.number.ordinal() == prevCard.number.ordinal() - 1) {
                straightFlushCards.add(0, currentCard);
            } else {
                if (straightFlushCards.size() >= numRequiredCards) {
                    break;
                }
                straightFlushCards.clear();
                straightFlushCards.add(currentCard);
            }
        }

        // handle a wheel (e.g. ace to five)
        if (straightFlushCards.size() == numRequiredCards - 1) {
            currentCard = straightFlushCards.get(0);
            Card aceWheelCard = new Card(CardNumber.ace, currentCard.suit);
            if (currentCard.number == CardNumber.two && sortedCardsList.contains(aceWheelCard)) {
                straightFlushCards.add(0, aceWheelCard);
            }
        }

        int straightFlushNumCards = straightFlushCards.size();

        if (straightFlushNumCards >= numRequiredCards) {
            return new CardHand(CardCombo.straightFlush, new ArrayList<>(straightFlushCards.subList(straightFlushNumCards - numRequiredCards, straightFlushNumCards)));
        }

        return null;
    }
    
    public static CardHand getQuads(List<Card> sortedCardsList) {
        int consecutiveSameNumber = 1;
        int numRequiredCards = 4;
        Card prevCard = sortedCardsList.get(0), currentCard = null;
        
        // check for consecutive 4 cards of same number from bottom up
        for (int i = 1; i < sortedCardsList.size(); i++) {
            currentCard = sortedCardsList.get(i);
            if (currentCard.number == prevCard.number) {
                consecutiveSameNumber++;
            } else {
                consecutiveSameNumber = 1;
            }
            if (consecutiveSameNumber >= numRequiredCards) {
                // check if quads is the last card, and handle the high card accordingly
                int highCardNumber = i == sortedCardsList.size() - 1 ? i - numRequiredCards : sortedCardsList.size() - 1;
                List<Card> quadsList = new ArrayList<>(sortedCardsList.subList(i - (numRequiredCards - 1), i + 1));
                quadsList.add(sortedCardsList.get(highCardNumber));
                return new CardHand(CardCombo.quads, quadsList);
            }
            prevCard = currentCard;
        }
        
        return null;
    }
    
    public static CardHand getHighestFullHouse(List<Card> sortedCardsList) {
        
        return null;
    }
    
    public static CardHand getHighestFlush(List<Card> sortedCardsList) {
        int numRequiredCards = 5;
        List<List<Card>> flushList = new ArrayList<>();
        for (int i = 0; i < CardSuit.values().length; i++) {
            flushList.add(new ArrayList<Card>());
        }
        
        // run through the sorted cards, add the sorted card to the respective array list
        // according to suit
        for (Card card : sortedCardsList) {
            flushList.get(card.suit.ordinal()).add(card);
        }
        
        // run through the arraylists
        // if there is an arraylist with 5 or more cards, trim to 5 and return in CardHand
        for (List<Card> flushCards : flushList) {
            if (flushCards.size() >= numRequiredCards) {
                List<Card> highestFlushCards = new ArrayList<>(flushCards.subList(flushCards.size() - numRequiredCards, flushCards.size()));
                return new CardHand(CardCombo.flush, highestFlushCards);
            }
        }
        
        return null;
    }
    
    public static CardHand getHighestStraight(List<Card> sortedCardsList) {
        List<Card> straightCards = new ArrayList<>();
        Card currentCard = null, prevCard = null;
        int numRequiredCards = 5;
    
        // iterate through the cards from top down
        for (int i = sortedCardsList.size() - 1; i >= 0; i--) {
            currentCard = sortedCardsList.get(i);
            if (straightCards.isEmpty()) {
                straightCards.add(currentCard);
                continue;
            }
        
            prevCard = straightCards.get(0);
            if (prevCard.number == currentCard.number) {
                continue;
            }
        
            if (currentCard.number.ordinal() == prevCard.number.ordinal() - 1) {
                straightCards.add(0, currentCard);
            } else {
                if (straightCards.size() >= numRequiredCards) {
                    break;
                }
                straightCards.clear();
                straightCards.add(currentCard);
            }
        }
    
        // handle a wheel (e.g. ace to five)
        if (straightCards.size() == numRequiredCards - 1) {
            currentCard = straightCards.get(0);
            if (currentCard.number == CardNumber.two) {
                for (Card card : sortedCardsList) {
                    if (card.number == CardNumber.ace) {
                        straightCards.add(0, card);
                    }
                }
            }
        }
    
        int straightNumCards = straightCards.size();
    
        if (straightNumCards >= numRequiredCards) {
            return new CardHand(CardCombo.straight, new ArrayList<>(straightCards.subList(straightNumCards - numRequiredCards, straightNumCards)));
        }
    
        return null;
    }
    
    public static CardHand getHighestTrips(List<Card> sortedCardsList) {
        
        return null;
    }
    
    public static CardHand getHighestTwoPair(List<Card> sortedCardsList) {
        
        return null;
    }
    
    public static CardHand getHighestPair(List<Card> sortedCardsList) {
        
        return null;
    }
    
    public static CardHand getHighestFiveCards(List<Card> sortedCardsList) {
        int numSortedCards = sortedCardsList.size();
        int numRequiredCards = 5;
        
        List<Card> highestFiveCards =  new ArrayList<>(sortedCardsList.subList(numSortedCards - numRequiredCards, numSortedCards));
        
        return new CardHand(CardCombo.highCard, highestFiveCards);
    }
}
