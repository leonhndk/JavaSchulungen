package de.esg.ausbildung.honl.game;

import java.util.ArrayList;

public class Player {

	private ArrayList<Card> hand;
	
	public Player() {
		hand = new ArrayList<Card>();
	}

	public int getHandValue() {
		int handValue = 0;
		for (Card card : hand) {
			handValue += card.getCardValue();
		}
		return handValue;
	}
	
	public void addCard(Card card) {
		hand.add(card);
	}
	
	public ArrayList<Card> getHand(){
		return hand;
	}

	public boolean isBust() {
		return getHandValue() > 21;
	}

	public void printHand() {
		for (Card card : hand) {
			System.out.print(card);
		}
		System.out.print("Points value: " + getHandValue() + "\t");
	}
	
}