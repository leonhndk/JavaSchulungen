package de.esg.ausbildung.honl.game;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Utilities for user input prompts and saving/loading a game
 */
public class Utils {
	private static String userHome = System.getProperty("user.home");
	private static String dir = userHome + File.separator + "JavaJack";
	private static Path directoryPath = Paths.get(dir);
	private static Scanner scanner = new Scanner(System.in);
	private static final String FILE_NAME = "Kartenspiel.csv";
	private static Path filePath = Paths.get(dir, FILE_NAME);

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
	private static ArrayList<String> saveCurrentStack(Deck deck) {
		ArrayList<String> cardStack = new ArrayList<String>();
		while (deck.getDeckSize() > 0) {
			Card card = deck.drawCard();
			cardStack.add(card.toString());
		}
		return cardStack;
	}

	public static boolean gameSaveExists() {
		return Files.exists(filePath);
	}

	
	// umbau zum testen --> Pfade, Dateinamen als Parameter übergeben statt Klassenvariablen, 
	/**
	 * create directory under user files to write save data to while checking
	 * existence thereof first
	 *
	 * @return String
	 */
	public static String createAndGetDirectory() {
		if (!Files.exists(directoryPath)) {
			try {
				Files.createDirectory(directoryPath);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return dir;
	}

	/**
	 * save the current game score and stack of cards
	 *
	 * @param playerScore
	 * @param dealerScore
	 * @param deck
	 */
	public static void saveGame(int playerScore, int dealerScore, Deck deck) {
		try {
			FileWriter writer = new FileWriter(createAndGetDirectory() + File.separator + FILE_NAME);
			writer.write("Player score: " + playerScore + "\n");
			writer.write("Dealer score: " + dealerScore + "\n");
			for (String card : saveCurrentStack(deck)) {
				writer.write(card + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Game state saved at following location: " + dir);
	}

	/**
	 * load dealer and player score from save
	 *
	 * @return saved player and dealer score as int array
	 */
	public static int[] loadScore() {
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
		if (string == null) {
			return null;
		}
		// split card string along whitespace e.g. THREE | of | CLUBS
		String[] arr = string.split("\\s");
		if (arr.length != 3) {
			System.out.println("Error! Save file corrupted");
			return null;
		}
		String rankStr = arr[0];
		String suitStr = arr[2];
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
	public static Deck loadCardStack() {
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
