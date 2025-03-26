package de.esg.ausbildung.honl.game;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Player {

    private List<Card> hand;
    private double balance;
    private NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.GERMANY);
	private DecimalFormat df = new DecimalFormat("#,##");

    /**
     * constructor for players (dealer is also a player)
     * each player has their own unique hand
     */
    public Player() {
        this.hand = new ArrayList<>();
        // maybe field name for future expansion?
    }

    /**
     * get the points value of all cards of a hand
     */
    public int getHandValue() {
        int handValue = 0;
        for (Card card : hand) {
            handValue += card.getCardValue();
        }
        return handValue;
    }

    /**
     * @param card add card to hand
     */
    public void addCard(Card card) {
        if(card != null) {
            hand.add(card);
        }
    }

    /**
     * check for bust
     */
    public boolean isBust() {
        return getHandValue() > 21;
    }
    
    public void doubleAce() {
		if(hand.get(0).getRank() == Rank.ACE && hand.get(1).getRank() == Rank.ACE) {
			hand.remove(1);
			hand.add(new Card(Rank.SOFT_ACE, Suit.SPADES));
		}
    }

    /**
     * method to check for blackjack after inital deal
     */
    public boolean hasBlackjack() {
        // ensures call is only possible immediately after initial deal
        if (hand.size() > 2) {
            return false;
        }
        boolean ace = false;
        boolean tenPoints = false;
        for (Card card : hand) {
            // check for ace on hand
            if (card.getRank() == Rank.ACE) {
                ace = true;
            }
            // check for any card worth 10 points (better to use getRank instead?)
            if (card.getCardValue() == 10) {
                tenPoints = true;
            }
        }
        // return true if both ace and 10-point card on hand
        return ace && tenPoints;
    }

    /**
     * method to ensure hands are empty before initial deal
     */
    public void resetHand() {
        hand.clear();
    }

    /**
     * print all cards on a player's hand
     */
    public void printHand() {
        for (Card card : hand) {
            System.out.print(card);
        }
        System.out.print("Points value: " + getHandValue() + "\t");
    }

    /**
     * overloaded method to print only one card at index instead of full hand
     * used to show one of the dealer's cards during initial deal
     */
    public void printHand(int index) {
        Card card = hand.get(index);
        System.out.println(card);
        System.out.println("Points value: " + card.getCardValue() + "\t");
    }

    public List<Card> getHand() {
        return hand;
    }
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void charge(double d) {
		if (balance - d < 0) {
			System.out.println("Wager exceeds balance, continuing with wager of 0,00 € !");
		} else {
			balance -= d;
		}
	}

	public void printBalance() {

		nf.setRoundingMode(RoundingMode.DOWN);
		String balance = nf.format(this.balance);
		System.out.println("Your balance is: " + balance);
	}
}