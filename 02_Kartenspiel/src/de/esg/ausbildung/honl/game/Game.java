package de.esg.ausbildung.honl.game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
	
	/**
	 * prints messages to the console prompts user input; returns it as deck size if
	 * valid
	 */
	private int promptInput() {
		int deckSize = 0;
		Scanner scanner = new Scanner(System.in);
		System.out.print("Welcome to ESG Casino\n");
		// prompts user to specify the deck size, repeats prompt if input isn't 32 or 52
		while (!isValidInput(deckSize)) {

			try {
				System.out.println("Please specify the desired deck size (32 or 52 cards)");
				deckSize = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Non-integer input detected! Please enter an integer");
				scanner.nextLine();
			}
			if (isValidInput(deckSize)) {
				break;
			}
		}
		scanner.close();
		System.out.println("Your chosen deck size is " + deckSize + " cards\n");
		System.out.println("This is the deck before shuffling\n");
		return deckSize;
	}

	/**
	 * @param input; verifies that user input is valid deck size of 32 or 52
	 */
	private boolean isValidInput(int input) {
		if (input == 32 || input == 52) {
			return true;
		}
		return false;

	}
	/**
	 * creates a new deck passing prompted input as paramater and prints deck 
	 */
	public void startGame() {
		Deck deck = new Deck (promptInput());
		deck.printDeck();
	}

}
