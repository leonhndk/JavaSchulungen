package de.esg.ausbildung.honl.game;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	private ArrayList<Card> deck = new ArrayList<>();

	/**
	 * @param numberOfDecks: allows for multiple decks of cards in one playing deck,
	 *                       option to shuffle
	 */
	public Deck(int numberOfDecks, boolean shuffled) {
		while (numberOfDecks >= 1) {
			buildDeck();
			numberOfDecks--;
			if (shuffled) {
				Collections.shuffle(deck);
			}
		}
	}

	/**
	 * iterates through enums suit and rank to populate deck
	 */
	private void buildDeck() {
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				Card card = new Card(rank, suit);
				deck.add(card);
			}
		}
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
}
