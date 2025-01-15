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
    public static final List<Card> inputCards = List.of(ACE_HEARTS, TEN_HEARTS,
            KING_SPADES, FOUR_CLUBS, SIX_DIAMONDS, TWO_CLUBS);
}
