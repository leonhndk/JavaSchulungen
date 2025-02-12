package de.esg.ausbildung.honl.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * universal method to prompt yes or no user input
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
    
    public static ArrayList<String> saveCurrentStack(Deck deck) {
    	ArrayList<String> cardStack = new ArrayList<String>();
    	for (Card card : deck.getDeck()) {
    		cardStack.add(card.toString());
    	}
    	return cardStack;
    }
    
    /**
     * create directory under user files to write save data to while checking existence thereof first
     */
    public static String createAndGetDirectory() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(System.getProperty("user.home"));
    	for (int i = 0; i < sb.length(); i++) {
    		if (sb.charAt(i) == '\\') {
    			sb.insert(i, '\\');
    			i++;
    		}
    	}
    	sb.append("\\CardGame");
    	String dir = sb.toString();
    	Path directoryPath = Paths.get(dir);
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
     */
    public static void saveGame (int playerScore, int dealerScore, Deck deck) {
    	String fileName = "\\\\Kartenspiel.csv";
    	try {
			FileWriter writer = new FileWriter(createAndGetDirectory() + fileName);
			writer.write("Player score: " + playerScore);
			writer.write("Dealer score: " + dealerScore);
			for (String card : saveCurrentStack(deck)) {
				writer.write(card);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    }
    
    /**
     * load dealer and player score from save
     */
    public static int[] loadScore() {
    	String filePath = createAndGetDirectory() + "\\\\Kartenspiel.csv";
    	String playerLine;
    	String dealerLine;
    	try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			playerLine = reader.readLine();
			dealerLine = reader.readLine();
			reader.close();
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
    	int playerScore = Integer.parseInt(playerLine.substring("Player score:".trim().length()));
		int dealerScore = Integer.parseInt(dealerLine.substring("Player score:".trim().length()));
    	int[] scoreArray = {playerScore, dealerScore};
    	return scoreArray;
    }
}
