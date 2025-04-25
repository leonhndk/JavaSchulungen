package de.esg.ausbildung.honl.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SaveUtils {
	
	public static SaveData loadSavedGame(Path filePath) {
		ArrayList<String> savedGame = readSaveData(filePath);
		ArrayList<Card> cardStack;
		int [] scores = new int [2];
		// if validation fails print error message and return null 
		if (!validateSaveData(savedGame)) {
			System.out.println("Save file has been corrupted! Proceeding with a new game...");
			return null;
		}
		else {
			double balance = readBalance(savedGame) * 100;
			scores = readScore(savedGame);
			cardStack = loadCardStack(savedGame);
			return new SaveData((int) balance, scores, cardStack);
		}
	}
	
	public static Double readBalance (ArrayList<String> gameSave) {
		String line = gameSave.get(0).substring(14);
		String [] balance = line.trim().split("\\s");
		try {
			return Double.parseDouble(balance[0]);
		}
		catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			return 0.00;
		}
	}
	
	/**
	 * load last saved card stack
	 * 
	 * @return ArrayList
	 */
	public static ArrayList<Card> loadCardStack(List<String> gameSave) {
		ArrayList<Card> cardStack = new ArrayList<>();
			for (int i = 3; i < gameSave.size(); i++) {
				Card card = readCard(gameSave.get(i));
				if (card != null) {
					cardStack.add(card);
				}
			}
//			Deck deck = new Deck(0);
//			deck.getDeck().addAll(cardStack);
//			return deck;
			return cardStack;
	}

	/**
	 * @param string representation of card from save file read in loadCardStack
	 *               method
	 * @return card object
	 */
	public static Card readCard(String string) {
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
	 * read dealer and player score from saved game
	 * 
	 * @param gameSave
	 * @return saved player and dealer score as int array
	 */
	public static int[] readScore(ArrayList<String> gameSave) {
		int playerScore = 0;
		int dealerScore = 0;
			String player = gameSave.get(1).substring(14);
			String dealer = gameSave.get(2).substring(14);
			try {
				playerScore = Integer.parseInt(player.trim());
				dealerScore = Integer.parseInt(dealer.trim());
			} catch (NumberFormatException e) {
				System.out.println("Error when reading saved score!");
				e.printStackTrace();
				return null;
			}
		return new int[] { playerScore, dealerScore };
	}

	/**
	 * check for uncorrupted save game file
	 * 
	 * @param List  gameSave read from the file
	 * @return boolean
	 */
	public static boolean validateSaveData(ArrayList<String> gameSave) {
		boolean scoreMatches = false;
		boolean stackMatches = true;
		boolean balanceMatches = true;
		// match pattern for saved scoreline 
		if (gameSave.get(1).matches(".*score:\\s\\d+") && gameSave.get(2).matches(".*score:\\s\\d+")) {
			scoreMatches = true;
		}
		// match pattern for saved card stack
		for (int i = 3; i < gameSave.size(); i++) {
			if (!gameSave.get(i).matches("[A-Z]+ of [A-Z]+")) {
				System.out.println("Error when reading card stack!");
				stackMatches = false;
			}
		}
		// lastly match saved balance and return true if everything matches 
		if (!gameSave.get(0).matches("Your Balance:\\s\\d+\\.\\d{1,2}\\s€")) {
			balanceMatches = false; 
			System.out.println("Error when reading remaining balance!");
		}
		return scoreMatches && stackMatches && balanceMatches;
	}

	public static ArrayList<String> readSaveData (Path filePath) {
		ArrayList<String> gameSave = new ArrayList<>();
		String line;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()));) {
			while ((line = reader.readLine()) != null) {
				gameSave.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return gameSave;
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
	 * 
	 * @param directoryPath
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
	public static void saveGame(int playerScore, int dealerScore, double balance, Deck deck, String fileName) {
		try {
			FileWriter writer = new FileWriter(fileName);
			writer.write("Your Balance: " + balance + " €\n");
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

}