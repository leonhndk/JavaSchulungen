package de.esg.ausbildung.honl.game;

public enum Rank {
	TWO(false), THREE(false), FOUR(false), FIVE(false), SIX(false), SEVEN(true), EIGHT(true), NINE(true), TEN(true),
	JACK(true), QUEEN(true), KING(true), ACE(true);

	private boolean smallDeck;

	private Rank(boolean smallDeck) {
		this.smallDeck = smallDeck;
	}

	public boolean partOfSmallDeck() {
		return smallDeck;
	}
}
