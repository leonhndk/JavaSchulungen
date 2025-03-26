package de.esg.ausbildung.honl.test.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.esg.ausbildung.honl.game.Card;
import de.esg.ausbildung.honl.game.Rank;
import de.esg.ausbildung.honl.game.Suit;
import de.esg.ausbildung.honl.game.Utils;

public class TestConstants {

	public static final Card ACE_HEARTS = new Card(Rank.ACE, Suit.HEARTS);
	public static final Card TEN_HEARTS = new Card(Rank.TEN, Suit.HEARTS);
	public static final Card KING_SPADES = new Card(Rank.KING, Suit.SPADES);
	public static final Card FOUR_CLUBS = new Card(Rank.FOUR, Suit.CLUBS);
	public static final Card SIX_DIAMONDS = new Card(Rank.SIX, Suit.DIAMONDS);
	public static final Card TWO_CLUBS = new Card(Rank.TWO, Suit.CLUBS);
	public static final Card JACK_CLUBS = new Card(Rank.JACK, Suit.CLUBS);
	public static final List<Card> INPUT_CARDS = List.of(ACE_HEARTS, TEN_HEARTS, KING_SPADES, FOUR_CLUBS, SIX_DIAMONDS,
			TWO_CLUBS, JACK_CLUBS, SIX_DIAMONDS, TWO_CLUBS, TEN_HEARTS, JACK_CLUBS, ACE_HEARTS, FOUR_CLUBS);
	/**
	 * correct string representation of card
	 */
	public static final String CARD_AS_STRING = "ACE of HEARTS";

	/**
	 * test input for bad string representation of card
	 */
	public static final String[] CARD_STRINGS_BAD = { "ACEofHEARTS", "ACE of HEART", null, "null of null" };
	static final String TEST_DIRECTORY = System.getProperty("user.home") + File.separator + "KartenspielTest";
	static final String TEST_FILE_NAME = "KartenspielTest.csv";
	static final String SAVE_FILE = Utils.createAndGetDirectory(Paths.get(TEST_DIRECTORY)) + File.separator
			+ "SaveTest.csv";
	static final Path TEST_FILE_PATH = Paths.get(TEST_DIRECTORY, TEST_FILE_NAME);
	static final Path SAVE_TEST_PATH = Paths.get(TEST_DIRECTORY, "SaveTest.csv");
	static final int PLAYER_SCORE = 100;
	static final int DEALER_SCORE = 4;
	static final int[] SCORE_ARRAY = { PLAYER_SCORE, DEALER_SCORE };
}
