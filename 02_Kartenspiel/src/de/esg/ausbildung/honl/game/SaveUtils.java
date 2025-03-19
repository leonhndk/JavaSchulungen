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

	/**
	 * load last saved card stack
	 * 
	 * @return ArrayList
	 */
	public static ArrayList<Card> loadCardStack(List<String> gameSave) {
		ArrayList<Card> cardStack = new ArrayList<>();
		if (validateSaveData(gameSave, "[A-Z]+ of [A-Z]+", false)) {
			for (String cardStr : gameSave) {
				Card card = readCard(cardStr);
				if (card != null) {
					cardStack.add(card);
				}
			}
//			Deck deck = new Deck(0);
//			deck.getDeck().addAll(cardStack);
//			return deck;
			return cardStack;
		} else return null;
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
	 * read dealer and player score from saved game
	 * 
	 * @param gameSave
	 * @return saved player and dealer score as int array
	 */
	public static int[] readScore(List<String> gameSave) {
		int playerScore = 0;
		int dealerScore = 0;
		if (validateSaveData(gameSave, ".*score:\\s\\d+", true)) {
			String player = gameSave.get(0).substring(14);
			String dealer = gameSave.get(1).substring(14);
			try {
				playerScore = Integer.parseInt(player.trim());
				dealerScore = Integer.parseInt(dealer.trim());
			} catch (NumberFormatException e) {
				System.out.println("Error when reading saved score!");
				e.printStackTrace();
				return null;
			}
		}
		return new int[] { playerScore, dealerScore };
	}

	/**
	 * check for uncorrupted save game file
	 * 
	 * @param score true when validating score, false when validating card stack
	 * @param List  gameSave read from the file
	 * @paran String pattern: the pattern of the first two lines to validate against
	 * @return gameSave if validation successful
	 */
	public static boolean validateSaveData(List<String> gameSave, String pattern, boolean score) {
		int size = gameSave.size();
		if (score) {
			if (gameSave.get(0).matches(pattern) && gameSave.get(1).matches(pattern)) {
				return true;
			} else
				return false;
		} else {
			for (int i = 2; i < size; i++) {
				if (!gameSave.get(i).matches(pattern)) {
					return false;
				}
			}
			return true;
		}
	}

//	/**
//	 * check for uncorrupted save game file
//	 * 
//	 * @param score true when validating score, false when validating card stack
//	 * @param List  gameSave read from the file
//	 * @paran String pattern: the pattern of the first two lines to validate against
//	 * @return gameSave if validation successful
//	 */
//	public static boolean validateSaveData(List<String> gameSave, String pattern, boolean score) {
//		int size = gameSave.size();
//		if (score) {
//			if (gameSave.get(0).matches(pattern) && gameSave.get(1).matches(pattern)) {
//				return gameSave.subList(0, 2);
//			} else
//				return null;
//		} else {
//			for (int i = 2; i < size; i++) {
//				if (!gameSave.get(i).matches(pattern)) {
//					System.out.println("Error! Saved card stack has been corrupted.");
//					return null;
//				}
//			}
//			return gameSave.subList(2, size - 1);
//		}
//	}

	public static ArrayList<String> readSaveData (Path filePath) {
		ArrayList<String> gameSave = new ArrayList<String>();
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
	
	public static SaveData loadSavedGame (Path filePath) {
		ArrayList<String> gameSave = readSaveData(filePath);
		int [] scores = readScore(gameSave);
		ArrayList<Card> cardStack = loadCardStack(gameSave);
		return new SaveData(scores, cardStack);
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

}
