package de.esg.ausbildung.honl.game;

/**
 * holds ranks and suits of card deck
 */
public class Card {

	enum Rank {
		TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;
	}

	enum Suit {
		HEARTS, SPADES, DIAMONDS, CLUBS;
	}

	private Suit suit;
	private Rank rank;

	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	/**
	 * @param string, passed by overridden toString method 
	 * returns a string that is exactly 19 characters long by adding whitespace if needed
	 * returns null if string exceeds length of 19 
	 *
	 */
	private String normalizeString(String string) {
		if (string.length() > 19) {
			return null;
		}
		while (string.length() < 19) {
			string = string + " ";
		}
		return string;
	}

	@Override
	public String toString() {
		return normalizeString(rank + " of " + suit);
	}

}
