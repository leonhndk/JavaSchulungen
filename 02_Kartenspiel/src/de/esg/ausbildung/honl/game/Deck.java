package de.esg.ausbildung.honl.game;

import java.util.ArrayList;

public class Deck {

	private ArrayList<Card> deck = new ArrayList<>();

	/**
	 * @param deckSize (user specified size of the deck through console input) fills
	 *                 deck with cards iterating through enums Rank and Suit
	 */
	private Deck(int deckSize) {
		int modifier = 0;
		// for deck size of 32 first card to populate deck has to be of rank seven,
		// moves "pointer" to that position
		if (deckSize == 32) {
			modifier = 5;
		}
		for (int i = 0; i < 13 - modifier; i++) {
			Card.Rank rank = Card.Rank.values()[i + modifier];
			for (int j = 0; j < 4; j++) {
				Card.Suit suit = Card.Suit.values()[j];
				// creates new card and adds it to the deck
				deck.add(new Card(rank, suit));
			}
		}
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

	public static Deck buildDeck(int deckSize) {
		return new Deck(deckSize);
	}
}
