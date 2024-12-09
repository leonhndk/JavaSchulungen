package de.esg.ausbildung.honl.game;

public enum Rank {
	TWO(false, 2), THREE(false, 3), FOUR(false, 4), FIVE(false, 5), SIX(false, 6), SEVEN(true, 7), EIGHT(true, 8), NINE(true , 9), TEN(true, 10),
	JACK(true, 10), QUEEN(true, 10), KING(true, 10), ACE(true, 11);

	private final boolean smallDeck;
	private final int cardValue;

	private Rank(boolean smallDeck, int cardValue) {
		this.smallDeck = smallDeck;
		this.cardValue = cardValue;
	}

	public boolean partOfSmallDeck() {
		return smallDeck;
	}
	
	public int getCardValue(){
		return cardValue;
	}
}
