package com.luckbox.holdem.components.game;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by danielseetoh on 5/7/19.
 */
public class GameEvaluator {
    
    static final int HAND_SIZE = 5;
    
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
                if (straightFlushCards.size() >= HAND_SIZE) {
                    break;
                }
                straightFlushCards.clear();
                straightFlushCards.add(currentCard);
            }
        }

        // handle a wheel (e.g. ace to five)
        if (straightFlushCards.size() == HAND_SIZE - 1) {
            currentCard = straightFlushCards.get(0);
            Card aceWheelCard = new Card(CardNumber.ace, currentCard.suit);
            if (currentCard.number == CardNumber.two && sortedCardsList.contains(aceWheelCard)) {
                straightFlushCards.add(0, aceWheelCard);
            }
        }

        int straightFlushNumCards = straightFlushCards.size();

        if (straightFlushNumCards >= HAND_SIZE) {
            return new CardHand(CardCombo.straightFlush, new ArrayList<>(straightFlushCards.subList(straightFlushNumCards - HAND_SIZE, straightFlushNumCards)));
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
                
                // cards which are part of the combo are always at the back
                if (sortedCardsList.size() > highCardNumber) {
                    quadsList.add(0, sortedCardsList.get(highCardNumber));
                }
                return new CardHand(CardCombo.quads, quadsList);
            }
            prevCard = currentCard;
        }
        
        return null;
    }
    
    public static CardHand getHighestFullHouse(List<Card> sortedCardsList) {
        List<Card> fullHouseList = new ArrayList<>();
        int numCardsPerTrips = 3;
        int numCardsPerPair = 2;
        int lastIndexOfSortedCards = sortedCardsList.size() - 1;
    
        // add trips
        CardHand highestTrips = getHighestTrips(sortedCardsList);
        if (highestTrips == null) {
            return null;
        }
        fullHouseList.addAll(highestTrips.cards.subList(HAND_SIZE - numCardsPerTrips, HAND_SIZE));
        
        List<Card> sortedCardsWithoutHighestTrips = new ArrayList<>();
        for (Card card : sortedCardsList) {
            if (!fullHouseList.contains(card)) {
                sortedCardsWithoutHighestTrips.add(card);
            }
        }
    
        // add pair
        CardHand highestPair = getHighestPair(sortedCardsWithoutHighestTrips);
        if (highestPair == null) {
            return null;
        }
        List<Card> highestPairCards = highestPair.cards;
        fullHouseList.add(0, highestPairCards.get(highestPairCards.size() - 1));
        fullHouseList.add(0, highestPairCards.get(highestPairCards.size() - 2));
    
        if (fullHouseList.size() == HAND_SIZE) {
            return new CardHand(CardCombo.fullHouse, fullHouseList);
        }
        
        return null;
    }
    
    public static CardHand getHighestFlush(List<Card> sortedCardsList) {
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
            if (flushCards.size() >= HAND_SIZE) {
                List<Card> highestFlushCards = new ArrayList<>(flushCards.subList(flushCards.size() - HAND_SIZE, flushCards.size()));
                return new CardHand(CardCombo.flush, highestFlushCards);
            }
        }
        
        return null;
    }
    
    public static CardHand getHighestStraight(List<Card> sortedCardsList) {
        List<Card> straightCards = new ArrayList<>();
        Card currentCard = null, prevCard = null;
    
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
                if (straightCards.size() >= HAND_SIZE) {
                    break;
                }
                straightCards.clear();
                straightCards.add(currentCard);
            }
        }
    
        // handle a wheel (e.g. ace to five)
        if (straightCards.size() == HAND_SIZE - 1) {
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
    
        if (straightNumCards >= HAND_SIZE) {
            return new CardHand(CardCombo.straight, new ArrayList<>(straightCards.subList(straightNumCards - HAND_SIZE, straightNumCards)));
        }
    
        return null;
    }
    
    public static CardHand getHighestTrips(List<Card> sortedCardsList) {
        int tripsCounter = 1;
        int numSortedCards = sortedCardsList.size();
        int numRequiredCards = 3;
        int lastIndexOfSortedCards = numSortedCards - 1;
        Card prevCard = sortedCardsList.get(lastIndexOfSortedCards);
        Card currentCard;
        
        for (int i = lastIndexOfSortedCards - 1; i >= 0; i--) {
            currentCard = sortedCardsList.get(i);
            
            if (currentCard.number == prevCard.number) {
                tripsCounter++;
                if (tripsCounter >= numRequiredCards) {
                    List<Card> tripsList = new ArrayList<>(sortedCardsList.subList(i, i+numRequiredCards));
                    
                    // cards which are part of the combo are always at the back
                    for (int j = lastIndexOfSortedCards; j >= 0; j--) {
                        if (!tripsList.contains(sortedCardsList.get(j))) {
                            tripsList.add(0, sortedCardsList.get(j));
                        }
                        if (tripsList.size() >= HAND_SIZE) {
                            break;
                        }
                    }
                    return new CardHand(CardCombo.trips, tripsList);
                }
            } else {
                tripsCounter = 1;
            }
            
            prevCard = currentCard;
        }
        
        return null;
    }
    
    public static CardHand getHighestTwoPair(List<Card> sortedCardsList) {
        List<Card> pairsList = new ArrayList<>();
        int numCardsPerPair = 2;
        int lastIndexOfSortedCards = sortedCardsList.size() - 1;
    
        // add the first pair
        CardHand highestPair = getHighestPair(sortedCardsList);
        if (highestPair == null) {
            return null;
        }
        pairsList.addAll(highestPair.cards.subList(HAND_SIZE - numCardsPerPair, HAND_SIZE));
        
        List<Card> sortedCardsWithoutHighestPair = new ArrayList<>();
        for (Card card : sortedCardsList) {
            if (!pairsList.contains(card)) {
                sortedCardsWithoutHighestPair.add(card);
            }
        }
        
        // add second pair
        CardHand secondHighestPair = getHighestPair(sortedCardsWithoutHighestPair);
        if (secondHighestPair == null) {
            return null;
        }
        List<Card> secondHighestPairCards = secondHighestPair.cards;
        pairsList.add(0, secondHighestPairCards.get(secondHighestPairCards.size() - 1));
        pairsList.add(0, secondHighestPairCards.get(secondHighestPairCards.size() - 2));
    
        // cards which are part of the combo are always at the back
        for (int i = lastIndexOfSortedCards; i >= 0; i--) {
            if (!pairsList.contains(sortedCardsList.get(i))) {
                pairsList.add(0, sortedCardsList.get(i));
            }
            if (pairsList.size() >= HAND_SIZE) {
                break;
            }
        }
    
        return new CardHand(CardCombo.twoPair, pairsList);
    }
    
    public static CardHand getHighestPair(List<Card> sortedCardsList) {
        int numSortedCards = sortedCardsList.size();
        int lastIndexOfSortedCards = numSortedCards - 1;
        int numRequiredCards = 2;
        Card prevCard = sortedCardsList.get(lastIndexOfSortedCards);
        Card currentCard;
        
        for (int i = lastIndexOfSortedCards - 1; i >= 0; i--) {
            currentCard = sortedCardsList.get(i);
            if (currentCard.number == prevCard.number) {
                List<Card> pairList = new ArrayList<>(sortedCardsList.subList(i, i+numRequiredCards));
    
                // cards which are part of the combo are always at the back
                for (int j = lastIndexOfSortedCards; j >= 0; j--) {
                    if (!pairList.contains(sortedCardsList.get(j))) {
                        pairList.add(0, sortedCardsList.get(j));
                    }
                    if (pairList.size() >= HAND_SIZE) {
                        break;
                    }
                }
                return new CardHand(CardCombo.pair, pairList);
            }
            
            prevCard = currentCard;
        }
        
        return null;
    }
    
    public static CardHand getHighestFiveCards(List<Card> sortedCardsList) {
        int numSortedCards = sortedCardsList.size();
        
        List<Card> highestFiveCards =  new ArrayList<>(sortedCardsList.subList(numSortedCards - HAND_SIZE, numSortedCards));
        
        return new CardHand(CardCombo.highCard, highestFiveCards);
    }
}
