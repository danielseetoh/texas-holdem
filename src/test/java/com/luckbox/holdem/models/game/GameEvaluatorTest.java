package com.luckbox.holdem.models.game;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameEvaluatorTest {
    
    @Test
    public void testGetBestCombo() {
        Card[][] cardsArrayOfArrays = new Card[][]{
            {
                new Card(CardNumber.ace, CardSuit.spades),
                new Card(CardNumber.ace, CardSuit.clubs),
                new Card(CardNumber.three, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.diamonds),
                new Card(CardNumber.seven, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.hearts),
            },
            {
                new Card(CardNumber.ace, CardSuit.spades),
                new Card(CardNumber.four, CardSuit.hearts),
                new Card(CardNumber.three, CardSuit.spades),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.seven, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.hearts),
            },
            {
                new Card(CardNumber.ace, CardSuit.spades),
                new Card(CardNumber.four, CardSuit.hearts),
                new Card(CardNumber.three, CardSuit.hearts),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.seven, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.hearts),
            }
        };
        
        Card[][] expectedResults = new Card[][]{
            {
                new Card(CardNumber.ace, CardSuit.spades),
                new Card(CardNumber.ace, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.clubs),
                new Card(CardNumber.ace, CardSuit.diamonds),
            },
            null,
            {
                new Card(CardNumber.three, CardSuit.hearts),
                new Card(CardNumber.four, CardSuit.hearts),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
                new Card(CardNumber.seven, CardSuit.hearts)
            }
        };
        
        List<Card> bestCombo, expectedResult;
        
        for (int i = 0; i < cardsArrayOfArrays.length; i++) {
            bestCombo = GameEvaluator.getBestCombo(cardsArrayOfArrays[i]);
            expectedResult = expectedResults[i] == null ? null : Arrays.asList(expectedResults[i]);
            assertEquals(bestCombo, expectedResult);
        }
    }
    
    @Test
    public void testGetHighestStraightFlush() {
        Card[][] cardsArrayOfArrays = new Card[][]{
            {
                new Card(CardNumber.ace, CardSuit.spades),
                new Card(CardNumber.four, CardSuit.hearts),
                new Card(CardNumber.three, CardSuit.hearts),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.seven, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.hearts),
            },
            {
                new Card(CardNumber.ace, CardSuit.spades),
                new Card(CardNumber.four, CardSuit.hearts),
                new Card(CardNumber.three, CardSuit.spades),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.seven, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.hearts),
            },
            {
                new Card(CardNumber.ace, CardSuit.hearts),
                new Card(CardNumber.two, CardSuit.hearts),
                new Card(CardNumber.three, CardSuit.hearts),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.four, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.spades),
            },
            {
                new Card(CardNumber.ace, CardSuit.hearts),
                new Card(CardNumber.two, CardSuit.hearts),
                new Card(CardNumber.three, CardSuit.hearts),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.four, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.spades),
                new Card(CardNumber.ace, CardSuit.spades),
            },
            {
                new Card(CardNumber.ace, CardSuit.hearts),
                new Card(CardNumber.jack, CardSuit.hearts),
                new Card(CardNumber.queen, CardSuit.hearts),
                new Card(CardNumber.ten, CardSuit.hearts),
                new Card(CardNumber.king, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.spades),
                new Card(CardNumber.ace, CardSuit.spades),
            },
            {
                new Card(CardNumber.two, CardSuit.hearts),
                new Card(CardNumber.two, CardSuit.spades),
                new Card(CardNumber.three, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.spades),
                new Card(CardNumber.four, CardSuit.hearts),
            },
            {
                new Card(CardNumber.ten, CardSuit.hearts),
                new Card(CardNumber.eight, CardSuit.spades),
                new Card(CardNumber.ace, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.spades),
                new Card(CardNumber.four, CardSuit.hearts),
            }
        };
        
        Card[][] expectedResults = new Card[][]{
            {
                new Card(CardNumber.three, CardSuit.hearts),
                new Card(CardNumber.four, CardSuit.hearts),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
                new Card(CardNumber.seven, CardSuit.hearts)
            },
            null,
            {
                new Card(CardNumber.two, CardSuit.hearts),
                new Card(CardNumber.three, CardSuit.hearts),
                new Card(CardNumber.four, CardSuit.hearts),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
            },
            {
                new Card(CardNumber.ace, CardSuit.hearts),
                new Card(CardNumber.two, CardSuit.hearts),
                new Card(CardNumber.three, CardSuit.hearts),
                new Card(CardNumber.four, CardSuit.hearts),
                new Card(CardNumber.five, CardSuit.hearts),
            },
            {
                new Card(CardNumber.ten, CardSuit.hearts),
                new Card(CardNumber.jack, CardSuit.hearts),
                new Card(CardNumber.queen, CardSuit.hearts),
                new Card(CardNumber.king, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.hearts),
            },
            {
                new Card(CardNumber.two, CardSuit.hearts),
                new Card(CardNumber.three, CardSuit.hearts),
                new Card(CardNumber.four, CardSuit.hearts),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
            },
            null
        };
        
        List<Card> straightFlushCombo, expectedResult;
        
        for (int i = 0; i < cardsArrayOfArrays.length; i++) {
            List<Card> cardsList = Arrays.asList(cardsArrayOfArrays[i]);
            Collections.sort(cardsList);
            
            straightFlushCombo = GameEvaluator.getHighestStraightFlush(cardsList);
            expectedResult = expectedResults[i] == null ? null : Arrays.asList(expectedResults[i]);
            assertEquals(straightFlushCombo, expectedResult);
        }
    }
    
    @Test
    public void testGetQuads() {
        Card[][] cardsArrayOfArrays = new Card[][]{
            {
                new Card(CardNumber.ace, CardSuit.spades),
                new Card(CardNumber.ace, CardSuit.clubs),
                new Card(CardNumber.three, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.diamonds),
                new Card(CardNumber.seven, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.hearts),
            },
            {
                new Card(CardNumber.ace, CardSuit.spades),
                new Card(CardNumber.four, CardSuit.hearts),
                new Card(CardNumber.three, CardSuit.spades),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.seven, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.hearts),
            },
            {
                new Card(CardNumber.ace, CardSuit.hearts),
                new Card(CardNumber.two, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.diamonds),
                new Card(CardNumber.five, CardSuit.hearts),
                new Card(CardNumber.four, CardSuit.hearts),
                new Card(CardNumber.six, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.spades),
            }
        };
        
        Card[][] expectedResults = new Card[][]{
            {
                new Card(CardNumber.ace, CardSuit.spades),
                new Card(CardNumber.ace, CardSuit.hearts),
                new Card(CardNumber.ace, CardSuit.clubs),
                new Card(CardNumber.ace, CardSuit.diamonds),
            },
            null,
            null
        };
        
        List<Card> quadsCombo, expectedResult;
        
        for (int i = 0; i < cardsArrayOfArrays.length; i++) {
            List<Card> cardsList = Arrays.asList(cardsArrayOfArrays[i]);
            Collections.sort(cardsList);
            
            quadsCombo = GameEvaluator.getQuads(cardsList);
            expectedResult = expectedResults[i] == null ? null : Arrays.asList(expectedResults[i]);
            assertEquals(quadsCombo, expectedResult);
        }
    }
}
