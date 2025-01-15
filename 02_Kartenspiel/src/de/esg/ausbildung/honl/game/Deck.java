package de.esg.ausbildung.honl.game;

import java.util.*;

public class Deck {

    private ArrayList<Card> deck;

    /**
     * @param numberOfDecks: allows for multiple decks of cards in one playing deck,
     *                       option to shuffle
     */
    public Deck(int numberOfDecks) {
        this.deck = new ArrayList<Card>();
        while (numberOfDecks > 0) {
            for (Rank rank : Rank.values()) {
                for (Suit suit : Suit.values()) {
                    Card card = new Card(rank, suit);
                    deck.add(card);
                }
            }
            numberOfDecks--;
        }
    }


    /**
     * resets the deck and calls method to build another deck
     */
    public void resetDeck() {
        deck.clear();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                Card card = new Card(rank, suit);
                deck.add(card);
                deck.add(card);
            }
        }
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


	public ArrayList<Card> getDeck() {
		return deck;
	}

    
    
    
}
