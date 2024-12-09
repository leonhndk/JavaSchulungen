package de.esg.ausbildung.honl.game;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	private ArrayList<Card> dealerHand = new ArrayList<>();
	private ArrayList<Card> playerHand = new ArrayList<>();
	private Scanner scanner;
	private Deck deck;
	boolean gameOver;

	public Game() {
		playerHand = new ArrayList<>();
		dealerHand = new ArrayList<>();
		scanner = new Scanner(System.in);
		this.deck = new Deck(2);
		gameOver = false;
	}

	private void initalDeal() {
		System.out.print("Dealing...\n");
		playerHand.add(deck.drawRandomCard());
		dealerHand.add(deck.drawRandomCard());
		playerHand.add(deck.drawRandomCard());
		dealerHand.add(deck.drawRandomCard());
	}

	private void showInitalHands() {
		int initialHandValue = 0;
		System.out.print("Your hand:\t");
		for (Card card : playerHand) {
			initialHandValue += card.getCardValue();
			System.out.print(card);
		}
		System.out.print("\tYour hand has a value of " + initialHandValue + " points.\t");
		System.out.print("\nDealer's visible hand:\t" + dealerHand.get(1));
		initialHandValue = dealerHand.get(1).getCardValue();
		System.out.print("\tThe Dealer's visible hand has a value of " + initialHandValue + " points.\t");
	}

	private int getHandValue(ArrayList<Card> hand) {
		int handValue = 0;
		for (Card card : hand) {
			handValue += card.getCardValue();
		}
		return handValue;
	}

	private boolean isBust(ArrayList<Card> hand) {
		return getHandValue(hand) > 21;
	}

	public void printHand(ArrayList<Card> hand) {
		for (Card card : hand) {
			System.out.print(card);
		}
		System.out.print("Points value: " + getHandValue(hand) + "\t");
	}

	/**
	 * creates a new deck passing prompted input as parameter and prints deck
	 */
	public void playRound() {
//		System.out.println("Welcome to ESG Casino");
		dealerHand.clear();
		playerHand.clear();
		initalDeal();
		showInitalHands();
		playerTurn();
		System.out.println("\nThank you for playing at ESG Casino");
	}

	/**
	 * player is asked whether he would like to draw another card, check for bust after drawing another
	 */
	private void playerTurn() {
		while (!gameOver) {
			System.out.print("\nWould you like to draw another card?\n(yes) to hit\n(no) to stand\n");
			String choice = scanner.next().toLowerCase();
			if (choice.equals("yes")) {
				playerHand.add(deck.drawRandomCard());
				System.out.println("Your new hand:");
				printHand(playerHand);
				if (isBust(playerHand)) {
					System.out.println("Bust!");
					checkWinner();
					break;
				}
			} else if (choice.equals("no")) {
				System.out.println("Your final hand:");
				printHand(playerHand);
				dealerTurn();
				break;
			} else {
				System.out.println("Invalid choice. Please enter yes or no.");
			}
		}
	}

	private void dealerTurn() {
		// Reveal dealer's full initial hand
		System.out.println("\nDealer's full initial hand:");
		printHand(dealerHand);
		// Dealer draws cards until hand value is at least 17
		if (getHandValue(dealerHand) < 17) {
			dealerHand.add(deck.drawRandomCard());
			System.out.println("\nDealer draws a card:");
			printHand(dealerHand);
		}
		// Check winner after dealer completes their turn
		checkWinner();
	}

	private void checkWinner() {
		int playerPoints = getHandValue(playerHand);
		int dealerPoints = getHandValue(dealerHand);
		System.out.println("\nFinal Hands:");
		System.out.println("Your hand:");
		printHand(playerHand);
		System.out.println("\nDealer's hand:");
		printHand(dealerHand);

		if (isBust(playerHand)) {
			System.out.println("Dealer wins! You busted.");
		} else if (isBust(dealerHand)) {
			System.out.println("You win! Dealer busted.");
		} else if (playerPoints > dealerPoints) {
			System.out.println("You win!");
		} else if (playerPoints == 21) {
			System.out.println("Dealer wins!");
		} else if (playerPoints < dealerPoints) {
			System.out.println("Dealer wins!");
		} else {
			System.out.println("Tie!");
		}
		gameOver = true;
	}

}
