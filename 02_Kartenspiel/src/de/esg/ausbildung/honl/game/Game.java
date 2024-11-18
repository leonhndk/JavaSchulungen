package de.esg.ausbildung.honl.game;

import java.util.Scanner;

public class Game {
	
	/**
	 * prints messages to the console prompts user input; returns it as deck size if valid
	 */
	private static int promptInput() {
		int deckSize = 0;
		Scanner scanner = new Scanner(System.in);
		System.out.print("Welcome to ESG Casino\n");
		// prompts user to specify the deck size, repeats prompt if input isn't 32 or 52
		while (!isValidInput(deckSize)) {
			System.out.println("Please specify the desired deck size (32 or 52 cards)\n");
			deckSize = scanner.nextInt();
			if (isValidInput(deckSize)) {
				break;
			}
		}
		scanner.close();
		System.out.println("Your chosen deck size is " + deckSize + " cards\n");
		return deckSize;
	}

	/**
	 * @param input verifies that user input is valid deck size of 32 or 52
	 */
	private static boolean isValidInput(int input) {
		if (input == 32 || input == 52) {
			return true;
		}
		return false;

	}

	/**
	 * starts new game with specified deck size and prints deck
	 */
	public static void playGame() {
		int deckSize = Game.promptInput();
		Deck deck = Deck.buildDeck(deckSize);
		deck.printDeck();
	}
}
