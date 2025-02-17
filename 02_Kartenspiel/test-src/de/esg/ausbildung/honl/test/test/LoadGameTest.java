package de.esg.ausbildung.honl.test.test;

import de.esg.ausbildung.honl.game.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.Test;

class LoadGameTest extends TestInit {

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
    
    @Test
    void loadScoreTest() {
    	assertEquals(TestConstants.ACE_HEARTS, card);
    	// check strings with mismatching pattern correct return of null for mismatched string pattern
    	for (String str : TestConstants.CARD_STRINGS_BAD) {
    		assertTrue(Objects.equals(null, Utils.readCard(str)));
    	}
    }
}