package de.esg.ausbildung.honl.game;

import java.util.Scanner;

/**
 * Utilities for user input prompts and saving/loading a game
 */
public class Utils {

	private static Scanner scanner = new Scanner(System.in);

	/**
	 * universal method to prompt yes or no user input
	 *
	 * @return boolean
	 */
	public static boolean promptInput() {
		while (scanner.hasNext()) {
			String input = scanner.next().substring(0, 1).toUpperCase();
			if (!input.equals("Y") && !input.equals("N")) {
				System.out.println("Invalid choice! Please enter yes or no.");
			}
			if (input.equals("Y") || input.equals("N")) {
				return input.equals("Y");
			}
		}
		return false;
	}

	public static double promptBet() {
		double bet = 0;
		System.out.println("Please specify amount you wish to bet (Maximum bet: 2,00€).");
		while (scanner.hasNext()) {
			String input = scanner.next();
			try {
				bet = Math.round(Double.parseDouble(input) * 100.0) / 100;
				if (bet > 2.00) {
					System.out.println("Amount too high, continuing with maximum bet");
					bet = 2.00;
				}
				return bet;
			} catch (NumberFormatException nfe) {
				System.out.println("Invalid input! Please enter only the amount in the following format: x.xx");
			}
		}
		return bet;
	}

	/**
	 * close scanner (called at the end of a game session)
	 */
	public static void closeScanner() {
		scanner.close();
	}
}
