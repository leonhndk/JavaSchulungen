package de.esg.ausbildung.honl.game;

/**
 * controls game flow and determines winner. Simplified BlackJack rules
 */
public class SimpleGame {
    private Player player;
    private Player dealer;
    private Deck deck;
    private int dealerWins;
    private int playerWins;
    public boolean gameFinished;


    public SimpleGame() {
//        this.deck = new Deck(2);
        dealerWins = 0;
        playerWins = 0;
        this.player = new Player();
        this.dealer = new Player();
    }

    /**
     * deals first to cards without evaluation for bust
     */
    private void initialDeal() {
        System.out.println("Initial Deal...");
        player.addCard(deck.drawCard());
        System.out.println("Your initial hand:");
        player.printHand();
        dealer.addCard(deck.drawCard());
        System.out.println("\nDealer's initial hand:");
        dealer.printHand();
    }

    /**
     * prints game flow statements and prompts user to draw another card or stand
     */
    private void playerTurn() {

        System.out.println("\nPlayer's turn...\nYour current hand:");
        player.printHand();
        System.out.println("\nWould you like to draw another card?\n(Y)es to hit\n(N)o to stand");
        // ask player whether to hit or stand and print hand
        if (Utils.promptInput()) {
            System.out.print("Player draws a card...\t");
            player.addCard(deck.drawCard());
            System.out.println("Your new hand:");
            player.printHand();
        } else {
            System.out.println("Player decides to stand");
        }
    }

    /**
     * contains dealer game logic, returns false if dealer stands
     *
     * @return boolean
     */
    private boolean dealerTurn() {
        System.out.println("\nDealer's turn...");
        if (player.getHandValue() <= dealer.getHandValue()) {
            return false;
        }
        if (player.getHandValue() > dealer.getHandValue()) {
            System.out.println("Dealer draws a card...");
            System.out.println("Dealer's new hand:");
            dealer.addCard(deck.drawCard());
            dealer.printHand();
        }
        return true;
    }

    /**
     * resets hands of dealer and player and starts round with a shuffled deck
     * checks for bust after each turn, in case of bust increments overall score of other player,
     * terminates round
     * round is terminted if dealer decides to stand and score evaluated with checkWinner
     */
    private void playRound() {
        player.resetHand();
        dealer.resetHand();
        deck.resetDeck();
        deck.shuffleDeck();
        initialDeal();
        while (true) {
            playerTurn();
            if (player.isBust()) {
                System.out.println("Player is bust! Dealer wins!");
                dealerWins++;
                return;
            }
            boolean dealerDraws = dealerTurn();
            if (dealer.isBust()) {
                System.out.println("Dealer is bust! You win!");
                playerWins++;
                return;
            }
            if (!dealerDraws) {
                System.out.println("Dealer decided to stand. Checking winner now...");
                determineWinner();
                break;
            }
        }
    }
    /**
     * evaluate winner in case of neither player nor dealer going bust and allocate points
     */
    private void determineWinner() {
        if (player.getHandValue() > dealer.getHandValue()) {
            playerWins++;
            System.out.println("Player has won this round with a score of " + player.getHandValue() +
                    " to " + dealer.getHandValue());
        }
        if (dealer.getHandValue() > player.getHandValue()) {
            dealerWins++;
            System.out.println("Dealer has won this round with a score of " + dealer.getHandValue() +
                    " to " + player.getHandValue());
        }
        if (player.getHandValue() == dealer.getHandValue()) {
            System.out.println("Tie! Score: " + dealer.getHandValue() +
                    " to " + player.getHandValue());
        }
    }


    /**
     * contains logic to play multiple rounds and tallies score
     * allows player to decide to continue or quit after each round
     */
    public void playGame(int numOfDecks) {
    	System.out.println("Saved game available. Would you like to load the saved game state?");
    	boolean loadGame = Utils.promptInput();
    	if (loadGame) {
    		playerWins = Utils.loadScore()[0];
    		dealerWins = Utils.loadScore()[1];
    	}
    	else {
    		playerWins = 0;
    		dealerWins = 0;
    		this.deck = new Deck(numOfDecks);
    	}
  
        while (!gameFinished) {
            playRound();
            System.out.println("Current overall score: \tPlayer: " + playerWins + "\tDealer: " + dealerWins);
            System.out.println("Would you like to play another round?\n(Y)es to keep playing\n(N)o to quit");
            gameFinished = !Utils.promptInput();
        }
        System.out.println("Final score: \nPlayer: " + playerWins + "\tDealer: " + dealerWins);
        System.out.println("Thank you for playing");
        Utils.saveGame(playerWins, dealerWins, deck);
        Utils.closeScanner();
    }
}
