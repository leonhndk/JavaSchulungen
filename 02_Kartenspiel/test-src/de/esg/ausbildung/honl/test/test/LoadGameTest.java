package de.esg.ausbildung.honl.test.test;

import de.esg.ausbildung.honl.game.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.Test;

class LoadGameTest {

	Card card = Utils.readCard(TestConstants.CARD_AS_STRING);
	Card nullCard = new Card(null, null);
	
	/**
     * check for correct computation of card object from string representation
     */
    @Test
    void readCardTest() {
    	assertEquals(TestConstants.ACE_HEARTS, card);
    	// check strings with mismatching pattern correct return of null for mismatched string pattern
    	for (String str : TestConstants.CARD_STRINGS_BAD) {
    		assertTrue(Objects.equals(null, Utils.readCard(str)));
    	}
    }
    
    /**
     * loadScore should return correct scoreline when reading from file
     */
    @Test
    void loadScoreTest() {
    	int [] scoreRead = Utils.loadScore(TestConstants.TEST_FILE_PATH);
    	assertEquals(TestConstants.SCORE_ARRAY[0], scoreRead[0]);
    	assertEquals(TestConstants.SCORE_ARRAY[1], scoreRead[1]);
     }
    
    /**
     * check cards in card stack returned by loadCardStack
     */
    @Test
    void loadCardStackTest() {
    	Deck testDeck = new Deck(0);
    	testDeck.getDeck().addAll(TestConstants.INPUT_CARDS);
    	Deck deck = Utils.loadCardStack(TestConstants.TEST_FILE_PATH);
    	assertEquals(testDeck.getDeckSize(), deck.getDeckSize());
    	for (int i = 0; i < deck.getDeckSize(); i++) {
    		assertEquals(testDeck.getDeck().get(i), deck.getDeck().get(i));
    	}
     }
}