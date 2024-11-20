package de.esg.ausbildung.honl.game;

import java.util.ArrayList;

public class Deck {

	private ArrayList<Card> deck = new ArrayList<>();

	/**
	 * @param deckSize (user specified size of the deck through console input) fills
	 * deck with cards iterating through enums Rank and Suit
	 */
	public Deck(int deckSize) {
		if (deckSize == 32) {
			for (Rank rank : Rank.values()) {
				if (rank.partOfSmallDeck()) {
					for (Suit suit : Suit.values()) {
						Card card = new Card(rank, suit);
						deck.add(card);
					}
				}
			}
		}
		if (deckSize == 52) {
			for (Rank rank : Rank.values()) {
				for (Suit suit : Suit.values()) {
					Card card = new Card(rank, suit);
					deck.add(card);
				}
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
}
