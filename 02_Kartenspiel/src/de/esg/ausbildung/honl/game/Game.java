package de.esg.ausbildung.honl.game;

import java.util.Scanner;

public class Game {

	private Player player;
	private Player dealer;
	private Deck deck;
	private int dealerWins;
	private int playerWins;
	public boolean gameFinished;
	Scanner scanner;

	public Game() {
		player = new Player();
		dealer = new Player();
		deck = new Deck(2, true);
		gameFinished = false;
		scanner = new Scanner(System.in);
	}

	/**
	 * deals one card each while only showing player's hand deals two more cards and
	 * shows hands while keeping the dealer's first card face down
	 */
	private void initalDeal() {
		// reset hands after each round
		player.resetHand();
		dealer.resetHand();
		System.out.println("Dealing cards...");
		player.addCard(deck.drawCard());
		// show player's hand while leaving dealer's card face down
		player.printHand();
		System.out.println("\nDealer's first card is face down");
		dealer.addCard(deck.drawCard());
		player.addCard(deck.drawCard());
		// show player's full hand after 2nd draw, only dealer's 2nd card is face up
		System.out.println("\nYour initial hand:\t");
		player.printHand();
		dealer.addCard(deck.drawCard());
		System.out.println("\nDealer's visible hand:\t");
		dealer.printHand(1);
	}

	/**
	 * checks player's hand for bust before asking to hit or stand adds card to the
	 * player's hand if he chose to stay, otherwise prints stand message finally
	 * prints player's hand after completing the turn
	 */
	private void playerTurn() {
		while (!player.isBust()) {
			// ask player whether to hit or stand and print hand
			System.out.print("\nWould you like to draw another card?\n(yes) to hit\n(no) to stand\n");
			String choice = scanner.next().toLowerCase();
			if (choice.equals("yes")) {
				player.addCard(deck.drawCard());
				System.out.println("Your new hand:");
				player.printHand();
				// check for bust after drawing card
				if (player.isBust()) {
					System.out.println("Bust! You lose.");
					dealerWins++;
					return;
				}
				// if player is not bust, ask again for hit or stand
				continue;
			} else if (choice.equals("no")) {
				System.out.println("You chose to stand.");
				player.printHand();
				return;
			} else {
				System.out.println("Invalid choice. Please enter yes or no.");
			}
		}
	}

	/**
	 * checks player's hand for bust before dealer's turn dealer has to keep drawing
	 * cards until value of cards on hand is greater than 17 dealer can draw no more
	 * cards once value of cards on hand is greater than 17 check for bust after the
	 * dealer's turn
	 */
	private void dealerTurn() {
		if (!player.isBust()) {
			System.out.print("\nDealer's turn...");
			System.out.println("\nDealer's full hand:");
			dealer.printHand();
			// Dealer draws until hand is at least 17, stops drawing cards if hand value > 17
			while (dealer.getHandValue() < 17) {
				dealer.addCard(deck.drawCard());
				System.out.print("\nDealer draws a card...\t");
				dealer.printHand();
			}
			if (dealer.isBust()) {
				System.out.println("\nDealer is bust! You win!");
				playerWins++;
			}
		}
	}

	/**
	 * method is only called if neither players nor dealer have bust or a blackjack
	 * on hand calculate the points value of the cards on hand, determine winner and
	 * add points to game total
	 */
	private void checkWinner() {
		int playerHandValue = player.getHandValue();
		int dealerHandValue = dealer.getHandValue();
		if (!player.isBust() && !dealer.isBust()) {
			System.out.println("\n\nFinal Hands:");
			System.out.print("Your hand: ");
			player.printHand();
			System.out.print("\nDealer's hand: ");
			dealer.printHand();
			if (playerHandValue > dealerHandValue) {
				System.out.println("\nYou win!");
				playerWins++;
			} else if (playerHandValue < dealerHandValue) {
				System.out.println("\nDealer wins!");
				dealerWins++;
			} else {
				System.out.println("It's a tie!");
			}
		}

	}

	private void printScore() {
		System.out.println("You have won " + playerWins + " round(s)\tDealer has won " + dealerWins + " round(s)");
	}

	/**
	 * starts game and checks for blackjack (Ace and any ten-point-card on hand) if
	 * player or dealer have blackjack on hand round is aborted and round points
	 * allocated otherwise game proceeds normally and winner is determined after
	 * turns are completed finally prints round-based score
	 */
	private void playRound() {
		initalDeal();
		// check for player blackjack
		if (player.hasBlackjack()) {
			System.out.println("\nBlackjack! You win this round");
			playerWins++;
			printScore();
			return;
		}
		// check for dealer blackjack
		if (dealer.hasBlackjack()) {
			System.out.print("Dealer's full hand:\t");
			dealer.printHand();
			System.out.println("Blackjack! Dealer wins this round");
			dealerWins++;
			printScore();
			return;
		}
		// tied round if both player and dealer have blackjack
		if (player.hasBlackjack() && dealer.hasBlackjack()) {
			System.out.println("It's a tie!");
			return;
		}
		playerTurn();
		dealerTurn();
		checkWinner();
		printScore();
	}

	/**
	 * contains logic to play multiple rounds and tallies score allows player to
	 * decide to continue or quit after each round
	 */
	public void playGame() {
		System.out.println("Welcome to Blackjack at ESG Casino ");
		// reset player and dealer score before playing a new game
		dealerWins = 0;
		playerWins = 0;
		// play rounds as long as player hasn't decided to quit 
		while (!gameFinished) {
			playRound();
			//ask players whether to continue or quit after each round played
			while (true) {
				System.out.println("Would you like to continue playing or leave the table? (c) to continue, (q) to quit");
				String choice = scanner.next().toLowerCase();
				if (choice.equals("c")) {
					gameFinished = false;
					break;
				} else if (choice.equals("q")) {
					gameFinished = true;
					break;
				} else {
					System.out.println("Invalid choice. Please enter (c) or (q).");
				}
			}
		}
		System.out.print("Thank you for playing!\nThe final score is: ");
		printScore();
		scanner.close();
	}
}
