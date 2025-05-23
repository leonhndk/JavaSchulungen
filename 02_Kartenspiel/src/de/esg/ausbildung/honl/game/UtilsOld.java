package de.esg.ausbildung.honl.game;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Utilities for user input prompts and saving/loading a game
 */
public class UtilsOld {
	
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

	/**
	 * close scanner (called at the end of a game session)
	 */
	public static void closeScanner() {
		scanner.close();
	}

	/**
	 *
	 * @param deck
	 * @return card
	 */
	public static ArrayList<String> saveCurrentStack(Deck deck) {
		ArrayList<String> cardStack = new ArrayList<String>();
		while (deck.getDeckSize() > 0) {
			Card card = deck.drawCard();
			cardStack.add(card.saveString());
		}
		return cardStack;
	}

	public static boolean gameSaveExists() {
		return Files.exists(Constants.filePath);
	}

	
	/**
	 * create directory under user files to write save data to while checking
	 * existence thereof first
	 * @param dirPath TODO
	 *
	 * @return String
	 */
	public static String createAndGetDirectory(Path directoryPath) {
		if (!Files.exists(directoryPath)) {
			try {
				Files.createDirectory(directoryPath);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return directoryPath.toString();
	}

	/**
	 * save the current game score and stack of cards
	 *
	 * @param playerScore
	 * @param dealerScore
	 * @param deck
	 * @param dirPath 
	 */
	public static void saveGame(int playerScore, int dealerScore, Deck deck, String fileName) {
		try {
			FileWriter writer = new FileWriter(fileName);
			writer.write("Player score: " + playerScore + "\n");
			writer.write("Dealer score: " + dealerScore + "\n");
			for (String card : saveCurrentStack(deck)) {
				writer.write(card + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * load dealer and player score from save
	 *
	 * @return saved player and dealer score as int array
	 */
	public static int[] loadScore(Path filePath) {
		String playerLine;
		String dealerLine;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()));
			playerLine = reader.readLine();
			dealerLine = reader.readLine();
			reader.close();
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
		int playerScore = Integer.parseInt(playerLine.substring(14).trim());
		int dealerScore = Integer.parseInt(dealerLine.substring(14).trim());
		return new int[] { playerScore, dealerScore };
	}

	/**
	 * @param string representation of card from save file read in loadCardStack
	 *               method
	 * @return card object
	 */
	public static Card readCard(String string) {
		if (string == null || string.isEmpty()) {
			return null;
		}
		// split card string along whitespace e.g. THREE | of | CLUBS
		String[] arr = string.split("\\s");
		if (arr.length != 2) {
			System.out.println("Error! Save file corrupted");
			return null;
		}
		String rankStr = arr[0];
		String suitStr = arr[1];
		Rank rankComp = null;
		Suit suitComp = null;
		for (Rank rank : Rank.values()) {
			if (rankStr.equals(rank.name())) {
				rankComp = rank;
			}
		}
		for (Suit suit : Suit.values()) {
			if (suitStr.equals(suit.name())) {
				suitComp = suit;
			}
		}
		if (suitComp != null && rankComp != null) {
			return new Card(rankComp, suitComp);
		}
		return null;
	}

	/**
	 * load last saved card stack
	 * 
	 * @return ArrayList
	 */
	public static Deck loadCardStack(Path filePath) {
		String line;
		ArrayList<Card> cardStack = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()));
			// call readLine to skip first lines in file containing player and dealer score
			reader.readLine();
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				// get card from String representation and add it to the deck
				cardStack.add(readCard(line));
			}
			reader.close();
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}
		// create empty deck object and fill it with cards from saved
		Deck deck = new Deck(0);
		deck.getDeck().addAll(cardStack);
		return deck;
	}
}
