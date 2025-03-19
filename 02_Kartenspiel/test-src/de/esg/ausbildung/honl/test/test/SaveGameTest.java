package de.esg.ausbildung.honl.test.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import de.esg.ausbildung.honl.game.Card;
import de.esg.ausbildung.honl.game.Deck;
import de.esg.ausbildung.honl.game.SaveUtils;

class SaveGameTest {

	@Before
	void deleteFile() {
		try {
			Files.deleteIfExists(TestConstants.SAVE_TEST_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	Deck testDeck = new Deck(0);

	/**
	 * save a the current stack of cards
	 */
	@Test
	void saveStackTest() {
		testDeck.getDeck().addAll(TestConstants.INPUT_CARDS);
		ArrayList<String> cardStack = SaveUtils.saveCurrentStack(testDeck);
		int i = 0;
		for (Card testCard : testDeck.getDeck()) {
			assertEquals(testCard.toString(), cardStack.get(i));
			i++;
		}
	}

	/**
	 * call saveGame method and check for file existence and content
	 */
	@Test
	void saveGameTest() {
		String playerLine = "";
		String dealerLine = "";
		testDeck.getDeck().addAll(TestConstants.INPUT_CARDS);
		SaveUtils.saveGame(TestConstants.PLAYER_SCORE, TestConstants.DEALER_SCORE, testDeck, TestConstants.SAVE_FILE);
		assertTrue(Files.exists(TestConstants.TEST_FILE_PATH));
		try {
			BufferedReader reader = new BufferedReader(new FileReader(TestConstants.TEST_FILE_PATH.toString()));
			playerLine = reader.readLine();
			dealerLine = reader.readLine();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int playerScore = Integer.parseInt(playerLine.substring(14).trim());
		int dealerScore = Integer.parseInt(dealerLine.substring(14).trim());
		assertEquals(TestConstants.PLAYER_SCORE, playerScore);
		assertEquals(TestConstants.DEALER_SCORE, dealerScore);
	}

}