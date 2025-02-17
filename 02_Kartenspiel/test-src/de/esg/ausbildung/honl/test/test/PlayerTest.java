package de.esg.ausbildung.honl.test.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.esg.ausbildung.honl.game.Card;
import de.esg.ausbildung.honl.game.Player;
import de.esg.ausbildung.honl.game.Rank;
import de.esg.ausbildung.honl.game.Suit;			

class PlayerTest {
	
    /**
     * check for correct calculation of the points value of cards on hand
     */
    @Test
    void getHandValueTest() {
        Player player = new Player();
        player.addCard(TestConstants.TEN_HEARTS);
        player.addCard(TestConstants.TWO_CLUBS);
        player.addCard(TestConstants.JACK_CLUBS);
        assertEquals(22, player.getHandValue());
    }

    /**
     * resetHand should clear the hand
     */
    @Test
    void resetHandTest() {
        Player player = new Player();
        player.addCard(new Card(Rank.TEN, Suit.HEARTS));
        player.resetHand(); 
        assertTrue(player.getHand().isEmpty());
    }

    /**
     * verify isBust returns true if player's hand value is greater than 21
     */
    @Test	
    void isBust_TrueTest() {
        Player player = new Player();
        player.addCard(TestConstants.KING_SPADES);
        player.addCard(TestConstants.JACK_CLUBS);
        player.addCard(TestConstants.TWO_CLUBS);
        assertTrue(player.isBust());
    }

    /**
     * verify isBust returns false if player's hand value is lower than 22
     */
    @Test
    void isBust_FalseTest() {
        Player player = new Player();
        player.addCard(TestConstants.ACE_HEARTS);
        player.addCard(TestConstants.KING_SPADES);
        assertFalse(player.isBust());
    }
}