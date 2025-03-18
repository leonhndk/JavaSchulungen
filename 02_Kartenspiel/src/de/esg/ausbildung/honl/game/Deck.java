package de.esg.ausbildung.honl.game;

import java.util.*;

/**
 * logic to create deck of cards
 * stack of cards from which cards are dealt is also represented as an object of type Deck (one or more decks in stack)
 */
public class Deck {

    private ArrayList<Card> deck;

    /**
     * @param numberOfDecks: allows for multiple decks of cards in one playing deck,
     */
    public Deck(int numberOfDecks) {
        this.deck = new ArrayList<Card>();
        while (numberOfDecks > 0) {
           buildDeck();
           numberOfDecks--;
        }
    }

    /**
     * resets the deck and calls method to build deck
     */
    public void resetDeck(int numberOfDecks) {
        deck.clear();
        while (numberOfDecks > 0) {
            buildDeck();
            numberOfDecks--;
        }
    }

    public ArrayList<Card> buildDeck() {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                Card card = new Card(rank, suit);
                // soft ace (value = 1) should not be in deck 
                if (card.getCardValue() != 1) {
                	deck.add(card);
                }
            }
        }
        return deck;
    }
    
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    /**
     * draws card from the top of the deck, removes it from deck
     */
    public Card drawCard() {
        if (deck.isEmpty()) {
            System.out.println("Error! There are no cards left in the deck.");
        }
        return deck.remove(0);
    }

    /**
     * prints contents of the deck to the console with all four suits of respective
     * rank in one line
     */
    public void printDeck() {
        int i = 1;
        for (Card card : deck) {
            System.out.print(card);
            // line break after every 4 printed elements
            if (i % 4 == 0) {
                System.out.println();
            }
            i++;
        }
    }

    public int getDeckSize() {
        return deck.size();
    }

	public ArrayList<Card> getDeck() {
		return deck;
	}

}