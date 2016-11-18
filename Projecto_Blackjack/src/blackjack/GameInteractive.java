package blackjack;

import java.util.Scanner;

public class GameInteractive extends GameBody {

	/**
	 * Input parameter - number of 52-card decks in the shoe
	 * ( 4 <= shoesize <= 8 ).
	 */
	private int shoesize;
	
	/**
	 * Input parameter - percentage of shoe played before shuffling
	 * ( 10<= shuffle <= 10).
	 */
	private int shufflepercent;
	
	/**
	 * Scanner used to read the commands from the system.input.
	 */
	private Scanner input;

	//***********************************************************************************************
	
	/**
	 * Constructor
	 * 
	 * @param arguments
	 */
	public GameInteractive( Arguments arguments ){
		super( arguments );
		this.shoesize = arguments.getShoe();
		this.shufflepercent = arguments.getShuffle();
		this.statistics = new Statistics( arguments.getBalance() , shoesize );
	}

	//***********************************************************************************************

	/**
	 * This functions reads the input from keyboard
	 * and returns it as valid command to be executed.
	 * 
	 * @param input
	 * @return
	 */
	public String determineCommand( ) {
		
		String command = "q";
		
		System.out.println("");
		
		if ( input.hasNext() ){
		command = input.nextLine();
		}
		
		System.out.println(command);
		
		return command;
	}
	
	/**
	 * Functions that fills and shuffles the shoe with shoesize number of 52-card decks.
	 * 
	 * @param shoesize	-> number of 52-card decks.
	 */
	public void fillShoe( int shoesize ){
		Deck shoe = new Deck( shoesize );
		shoe.shuffleCards();
		this.shoe = shoe;
	}

	/**
	 * Plays multiple games of BlackJack until the player decides to quit the game or he
	 * runs out of balance.
	 */
	public void playGames(){
		
		int round = 0;
		float shoeplayed = 0;
		this.gameover = false;
		
		this.input = new Scanner(System.in);		
		
		this.fillShoe( this.shoesize );
		this.statistics.setShoesize(this.shoesize);
		
		// Cycle of games.
		while ( gameover != true ){
			round++;
			// Play one game of blackjack.
			this.playOneGame();
			
			//Check if it is needed to reshuffle the shoe.
			shoeplayed = (float)(this.statistics.getCardsPlayed() ) / (float)(this.shoesize * Deck.DECKSIZE ) * 100 ;
			if ( shoeplayed >= shufflepercent) {
				this.reshuffleDeck();
				this.statistics.resetCardCounting();
			}
			
			// Check if the Player still has enough balance to play
			if ( this.hasEnoughBalance(this.getMinbet()) == false )
				gameover = true;			
		}
		
		input.close();
		
	}
	
	
}
