package de.esg.ausbildung.honl.test.test;

import java.util.List;

import de.esg.ausbildung.honl.game.Card;
import de.esg.ausbildung.honl.game.Rank;
import de.esg.ausbildung.honl.game.Suit;

public class TestConstants {

    public static final Card ACE_HEARTS = new Card(Rank.ACE, Suit.HEARTS);
    public static final Card TEN_HEARTS = new Card(Rank.TEN, Suit.HEARTS);
    public static final Card KING_SPADES = new Card(Rank.KING, Suit.SPADES);
    public static final Card FOUR_CLUBS = new Card(Rank.FOUR, Suit.CLUBS);
    public static final Card SIX_DIAMONDS = new Card(Rank.SIX, Suit.DIAMONDS);
    public static final Card TWO_CLUBS = new Card(Rank.TWO, Suit.CLUBS);
    public static final Card JACK_CLUBS = new Card(Rank.JACK, Suit.CLUBS);
    public static final List<Card> INPUT_CARDS = List.of(ACE_HEARTS, TEN_HEARTS,
            KING_SPADES, FOUR_CLUBS, SIX_DIAMONDS, TWO_CLUBS);
    /**
	 * correct string representation of card
	 */
    public static final String CARD_AS_STRING = "ACE of HEARTS";
    
    /**
   	 * test input for bad string representation of card
   	 */
    public static final String [] CARD_STRINGS_BAD = {"ACEofHEARTS", "ACE of HEART", null, "null of null"};
   
    /**
   	 * Scoreline string in game save data file
   	 */
    public static final String PLAYER_SCORE = "Player score: ";
    }
