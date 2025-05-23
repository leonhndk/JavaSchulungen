package de.esg.ausbildung.honl.test.test;

import de.esg.ausbildung.honl.game.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DeckTest {

    @Test
    void drawCardTest() {
        Deck deck = new Deck(1);
        // fresh deck should hold 52 cards
        assertEquals(52, deck.getDeck().size());
        Card card = deck.drawCard();
        assertNotNull(card);
        assertEquals(51, deck.getDeck().size());
    }

    /**
     * removing a card from deck and reset it. Deck should now hold 104 cards again 
     */
    @Test
    void resetDeckTest() {
        Deck deck = new Deck(2);
        // draw a card thus removing it from the deck
        deck.drawCard();
        // call resetDeck to repopulate deck
        deck.resetDeck(2);
        // card should be in the deck again
        assertEquals(104, deck.getDeck().size());
    }

}