package de.esg.ausbildung.honl.test.test;

import de.esg.ausbildung.honl.game.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
	
    /**
     * check for correct calculation of the points value of cards on hand
     */
    @org.junit.jupiter.api.Test
    void getHandValueTest() {
        Player player = new Player();
        player.addCard(TestConstants.TEN_HEARTS);
        player.addCard(TestConstants.SIX_DIAMONDS);
        player.addCard(TestConstants.TWO_CLUBS);
        player.addCard(TestConstants.JACK_CLUBS);
        assertEquals(28, player.getHandValue());
    }

    /**
     * resetHand should clear the hand
     */
    @org.junit.jupiter.api.Test
    void resetHandTest() {
        Player player = new Player();
        player.addCard(new Card(Rank.TEN, Suit.HEARTS));
        player.resetHand(); 
        assertTrue(player.getHand().isEmpty());
    }

    /**
     * verify isBust returns true if player's hand value is greater than 21
     */
    @org.junit.jupiter.api.Test
    void isBust_TrueTest() {
        Player player = new Player();
        player.addCard(TestConstants.KING_SPADES);
        player.addCard(TestConstants.FOUR_CLUBS);
        player.addCard(TestConstants.ACE_HEARTS);
        assertTrue(player.isBust());
    }

    /**
     * verify isBust returns false if player's hand value is lower than 22
     */
    @org.junit.jupiter.api.Test
    void isBust_FalseTest() {
        Player player = new Player();
        player.addCard(new Card(Rank.JACK, Suit.HEARTS));
        player.addCard(new Card(Rank.FIVE, Suit.SPADES));
        assertFalse(player.isBust());
    }
}