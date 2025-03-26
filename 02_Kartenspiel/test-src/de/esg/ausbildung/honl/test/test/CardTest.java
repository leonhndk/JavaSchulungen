package de.esg.ausbildung.honl.test.test;

import de.esg.ausbildung.honl.game.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardTest {

	/**
     * check for correct calculation of the face value of a card
     */
    @Test
    void getCardValueTest() {
    	for (Card card : TestConstants.INPUT_CARDS) {
    		assertEquals(card.getRank().getCardValue(), card.getCardValue());
    	}
    }

}