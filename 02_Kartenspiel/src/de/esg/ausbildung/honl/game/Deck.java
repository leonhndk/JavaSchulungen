package de.esg.ausbildung.honl.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {

	private ArrayList<Card> deck = new ArrayList<>();
	private Random random = new Random();

	/**
	 * @param numberOfDecks: allows for multiple decks of cards in one playing deck
	 */
	public Deck(int numberOfDecks) {
		while (numberOfDecks >= 1) {
		buildBigDeck();
		numberOfDecks--;
		}
		Collections.shuffle(deck);
	}
	
	/**
	 * @param deckSize (user specified size of the deck through console input) fills
	 *                 deck with cards iterating through enums Rank and Suit 
	 *                 constructor for future use, allows modification to deck size and shuffling
	 */
	public Deck(int deckSize, boolean shuffled) {
		deck.clear();
		switch (deckSize) {
		case 32:
			buildSmallDeck();
			break;
		case 52:
			buildBigDeck();
			break;
		// not strictly necessary, deck size is passed as fixed value when instantiating
		default:
			throw new IllegalArgumentException("Invalid deck size, please specify 32 or 52");
		}
		if (shuffled) {
			Collections.shuffle(deck);
		}
	}

	private void buildSmallDeck() {
		for (Rank rank : Rank.values()) {
			if (rank.partOfSmallDeck()) {
				for (Suit suit : Suit.values()) {
					Card card = new Card(rank, suit);
					deck.add(card);
				}
			}
		}
	}

	private void buildBigDeck() {
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				Card card = new Card(rank, suit);
				deck.add(card);
			}
		}
	}

	public Card drawRandomCard() {
		if (deck.isEmpty()) {
			System.out.println("Error! There are no cards in the deck, please try again");
		}
		int randomIndex = random.nextInt(deck.size());
		return deck.remove(randomIndex);
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
