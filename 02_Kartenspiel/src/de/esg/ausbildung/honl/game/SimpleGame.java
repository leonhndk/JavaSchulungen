package de.esg.ausbildung.honl.game;

/**
 * controls game flow and determines winner. Simplified BlackJack rules
 */
public class SimpleGame {
	private Player player;
	private Player dealer;
	private Deck deck;
	private int numberOfDecks;
	private int dealerWins;
	private int playerWins;
	public boolean gameFinished;

	public SimpleGame(int numberOfDecks) {
		dealerWins = 0;
		playerWins = 0;
		this.player = new Player();
		this.dealer = new Player();
		this.numberOfDecks = numberOfDecks;
		this.deck = new Deck(0);
	}

	/**
	 * deals first to cards without evaluation for bust
	 */
	private void initialDeal() {
		System.out.println("Initial deal with two cards each...");
		for (int i = 0; i < 2; i++) {
			System.out.println("\nDealing card to player");
			player.addCard(deck.drawCard());
			player.printHand();
			System.out.println("\nDealing card to dealer");
			dealer.addCard(deck.drawCard());
			dealer.printHand();
		}
		System.out.println("\nYour initial hand:");
		player.printHand();
		System.out.println("\nDealer's initial hand:");
		dealer.printHand();
	}

	/**
	 * prints game flow statements and prompts user to draw another card or stand
	 */
	private void playerTurn() {
		System.out.println("\nPlayer's turn...\nYour current hand:");
		player.printHand();
		if (player.getHandValue() >= 17) {
			System.out.println("\nWould you like to draw another card?\n(Y)es to hit\n(N)o to stand");
			// ask player whether to hit or stand and print hand
			if (Utils.promptInput()) {
				System.out.println("Player draws a card...\t");
				player.addCard(deck.drawCard());
				System.out.println("Your new hand:");
				player.printHand();
			} else {
				System.out.println("Player decides to stand");
			}
		} else {
			System.out.println("\nPlayer draws a card...");
			player.addCard(deck.drawCard());
			System.out.println("\nYour new hand:");
			player.printHand();
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
	 * reset hands of dealer and player and starts round with a shuffled deck checks
	 * for bust after each turn, in case of bust increments overall score of other
	 * player; round is terminated if dealer decides to stand and score evaluated
	 * with checkWinner
	 */
	private void playRound() {
		player.resetHand();
		dealer.resetHand();
		if (deck.getDeckSize() < numberOfDecks * 52 * 0.2) {
			deck.resetDeck(numberOfDecks);
			System.out.println("Card stack depleted, resetting deck...");
		}
		deck.shuffleDeck();
		initialDeal();
		player.doubleAce();
		dealer.doubleAce();
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
	 * evaluate winner in case of neither player nor dealer going bust and allocate
	 * points
	 */
	private void determineWinner() {
		if (player.getHandValue() > dealer.getHandValue()) {
			playerWins++;
			System.out.println("Player has won this round with a score of " + player.getHandValue() + " to "
					+ dealer.getHandValue());
		}
		if (dealer.getHandValue() > player.getHandValue()) {
			dealerWins++;
			System.out.println("Dealer has won this round with a score of " + dealer.getHandValue() + " to "
					+ player.getHandValue());
		}
		if (player.getHandValue() == dealer.getHandValue()) {
			System.out.println("Tie! Score: " + dealer.getHandValue() + " to " + player.getHandValue());
		}
	}

	/**
	 * contains logic to play multiple rounds and tallies score allows player to
	 * decide to continue or quit after each round
	 */
	public void playGame() {
		boolean loadGame = false;
		// check for existence of directory, prompt user to load save or start new game
		if (SaveUtils.gameSaveExists()) {
			System.out.println("Saved game available. Would you like to load the saved game state?");
			loadGame = Utils.promptInput();
		}
		if (loadGame) {
			SaveData saveData = SaveUtils.loadSavedGame(Constants.filePath);
			playerWins = saveData.score()[0];
			dealerWins = saveData.score()[1];
			// read stack of cards from save and assign it to deck
			deck = new Deck(0);
			deck.getDeck().addAll(saveData.cardStack());
			System.out.println(deck.getDeck());
			System.out.println(deck.toString());
			player.setBalance(saveData.balance());
			System.out.println("Successfully loaded game save. Current scoreline: Player wins: " + playerWins
					+ "\tDealer wins: " + dealerWins);
			System.out.println(player.getBalance() + " €");
		}
		// if user chooses to start anew, reset round wins and supply fresh stack of
		// cards
		if (!loadGame || deck == null) {
			playerWins = 0;
			dealerWins = 0;
			this.deck = new Deck(numberOfDecks);
		}
		// play rounds until user decides to quit
		while (!gameFinished) {
			playRound();
			System.out.println("Current overall score: \tPlayer: " + playerWins + "\tDealer: " + dealerWins);
			System.out.println("Would you like to play another round?\n(Y)es to keep playing\n(N)o to quit");
			gameFinished = !Utils.promptInput();
		}
		// print final score and save game state
		System.out.println("Final score: \tPlayer: " + playerWins + "\tDealer: " + dealerWins);
		System.out.println("Thank you for playing!");
		SaveUtils.saveGame(playerWins, dealerWins, player.getBalance(), deck, Constants.FILE_PATH_SAFE);
		System.out.println("Game state saved successfully at following location: " + Constants.FILE_PATH_SAFE);
		Utils.closeScanner();
	}
}