package de.esg.ausbildung.honl.game;

import java.util.Scanner;

public class Game {

	private Player player;
	private Player dealer;
	private Scanner scanner;
	private Deck deck;
	private boolean gameOver;

	public Game() {
		player = new Player();
		dealer = new Player();
		deck = new Deck(2, true);
		gameOver = false;
	}

	private void initalDeal() {
		System.out.print("Dealing cards...\n");
		player.addCard(deck.drawCard());
		dealer.addCard(deck.drawCard());

		player.addCard(deck.drawCard());
		dealer.addCard(deck.drawCard());
		System.out.println("Your hand:\t");
		player.printHand();
	}

	private void playerTurn() {
		Scanner scanner = new Scanner(System.in);
		boolean finishedTurn = false;

		while (!finishedTurn) {
			System.out.print("\nWould you like to draw another card?\n(yes) to hit\n(no) to stand\n");
			String choice = scanner.next().toLowerCase();
			if (choice.equals("yes")) {
				if (player.isBust()) {
					System.out.println("Bust!");
					gameOver = true;
					return;
				}
				player.addCard(deck.drawCard());
				System.out.println("Your new hand:");
				player.printHand();
				finishedTurn = true;
				if (player.isBust()) {
					System.out.println("Bust!");
					gameOver = true;
					return;
				}
			} else if (choice.equals("no")) {
				System.out.println("Your current hand:");
				player.printHand();
				finishedTurn = true;
			} else {
				System.out.println("Invalid choice. Please enter yes or no.");
			}
		}
	}

	private void dealerTurn() {
		System.out.println("\nDealer's turn:");
		System.out.println("Dealer's full hand:");
		dealer.printHand();

		// Dealer draws until hand is at least 17
		while (dealer.getHandValue() < 17) {
			dealer.addCard(deck.drawCard());
			System.out.println("Dealer draws a card:");
			dealer.printHand();
		}

		if (dealer.isBust()) {
			System.out.println("Dealer is bust! You win!");
			gameOver = true;
		}
	}

	private void checkWinner() {
		if (!gameOver) {
			System.out.println("\nFinal Hands:");
			System.out.print("Your hand: ");
			player.printHand();
			System.out.print("\nDealer's hand: ");
			dealer.printHand();

			int playerValue = player.getHandValue();
			int dealerValue = dealer.getHandValue();

			if (playerValue > dealerValue) {
				System.out.println("You win!");
			} else if (playerValue < dealerValue) {
				System.out.println("Dealer wins!");
			} else {
				System.out.println("It's a tie!");
			}
		}
	}

	public void play() {
		initalDeal();
		while (!gameOver) {
			playerTurn();
			dealerTurn();
			checkWinner();
		}
		checkWinner();
	}

}
