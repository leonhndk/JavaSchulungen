package de.esg.ausbildung.honl.game;

import java.util.ArrayList;

public class Player {

	private ArrayList<Card> hand;

	/**
	 * constructor for players (dealer is also a player)
	 * each player has their own unique hand
	 */
	public Player() {
		hand = new ArrayList<Card>();
		// maybe field name for future expansion?
	}

	/**
	 * get the points value of all cards of a hand
	 */
	public int getHandValue() {
		int handValue = 0;
		for (Card card : hand) {
			handValue += card.getCardValue();
		}
		return handValue;
	}
	
	/**
	 * @param card
	 * add card to hand 
	 */
	public void addCard(Card card) {
		hand.add(card);
	}

	/**
	 * check for bust
	 */
	public boolean isBust() {
		return getHandValue() > 21;
	}

	/**
	 * method to check for blackjack after inital deal
	 */
	public boolean hasBlackjack() {
		// ensures call is only possible immediately after initial deal
		if (hand.size() > 2) {
			return false;
		}
		boolean ace = false;
		boolean tenPoints = false;
		for (Card card : hand) {
			// check for ace on hand
			if (card.getRank() == Rank.ACE) {
				ace = true;
			}
			// check for any card worth 10 points (better to use getRank instead?)
			if (card.getCardValue() == 10) {
				tenPoints = true;
			}
		}
		// return true if both ace and 10-point card on hand
		return ace && tenPoints;
	}

	/**
	 * method to ensure hands are empty before initial deal
	 */
	public void resetHand() {
		hand.clear();
	}

	/**
	 * print all cards on a player's hand
	 */
	public void printHand() {
		for (Card card : hand) {
			System.out.print(card);
		}
		System.out.print("Points value: " + getHandValue() + "\t");
	}

	/**
	 * overloaded method to print only one card at index instead of full hand 
	 * used to show one of the dealer's cars during inital deal
	 */
	public void printHand(int index) {
		Card card = hand.get(index);
		System.out.print(card);
		System.out.print("Points value: " + card.getCardValue() + "\t");
	}

}