package de.esg.ausbildung.honl.test.test;

import de.esg.ausbildung.honl.game.*;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

	/**
     * check for correct calculation of the face value of a card
     */
    @org.junit.jupiter.api.Test
    void getCardValueTest() {
    	for (Card card : TestConstants.inputCards) {
    		assertEquals(card.getRank().getCardValue(), card.getCardValue());
    	}
    }

}