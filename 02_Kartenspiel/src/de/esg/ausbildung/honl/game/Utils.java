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

	public static int promptBet() {
		double bet = 0;
		System.out.println("Please specify amount you wish to bet (Maximum bet: 2,00 €).");
		while (scanner.hasNext()) {
			String input = scanner.next();
			if(!validateInput(input)) {
				System.out.println("Invalid input! Please enter only the amount in the following format: x.xx");
				continue;
			}
			try {
				bet = Double.parseDouble(input);
				if (bet > 2.00) {
					System.out.println("Amount exceeds maximum bet. Continuing with bet of 2,00 €.");
					return 200;
				}
				return (int) (bet * 100);
			} catch (NumberFormatException nfe) {
				System.out.println("Invalid input! Please enter only the amount in the following format: x,xx");
			}
		}
		return (int) bet * 100;
	}

	public static boolean validateInput (String input) {
		return input.trim().matches("\\d(\\.\\d{1,2})?");
	}

	/**
	 * close scanner (called at the end of a game session)
	 */
	public static void closeScanner() {
		scanner.close();
	}
}
