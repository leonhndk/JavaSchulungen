package de.esg.ausbildung.honl.game;

/**
 * holds constructor for card and methods to format String output
 */
public class Card {

	private Suit suit;
	private Rank rank;

	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	/**
	 * @param cardDescription 
	 * returns a string that is exactly 19 characters long by adding whitespace if needed
	 *
	 */
	private String normalizeString(String cardDescription) {
		if (cardDescription.length() > 19) {
			return null;
		}
		StringBuilder normalizedDescription = new StringBuilder(cardDescription);
		while (normalizedDescription.length() < 19) {
			normalizedDescription.append(" ");
		}
		return normalizedDescription.toString();
	}

	@Override
	public String toString() {
		return normalizeString(rank + " of " + suit);
	}

}
