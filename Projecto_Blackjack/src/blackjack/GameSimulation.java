package blackjack;

public class GameSimulation extends GameBody{

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
	 * number of shuffles to perform until ending of simulation.
	 */
	private int numberOfShuffles;
	
	/**
	 * counting cars strategy to use during simulation (combinations of BS, HL, AF)
	 */
	private String strategy;
	
	//***********************************************************************************************
	
	/**
	 * Constructor for GameSimulation. The parameters of the constructor hold information for
	 * setting up the fields of this class.
	 * 
	 * @param arguments
	 */
	public GameSimulation( Arguments arguments ) {
		super( arguments );
		this.shoesize = arguments.getShoe();
		this.shufflepercent = arguments.getShuffle();
		this.numberOfShuffles = arguments.getSNumber();
		this.strategy = arguments.getStrategy();
		this.statistics = new Statistics( arguments.getBalance() , shoesize );
	}

	//***********************************************************************************************
	
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
	 * This method is used to determine the command the player should do when it is his
	 * time to play. The player's action is simulated by the card counting strategies like
	 * the Basic Strategies , High-Low Strategies and Ace-Five Strategies.
	 * 
	 * @Overriden Method from GameBody. 
	 * 
	 */
	public String determineCommand() {
		String str = "";
		
		float trueCount = this.statistics.getTrueCount();
		Hand dhand = this.dealer.getHand();
		PHand phand = this.playinghand;
		
		int acefivecount = this.statistics.getAcefiveCount();
		int minbet = this.getMinbet();
		int bet = player.getBet();
		int maxbet = this.getMaxbet();
		int cardsplayed = this.statistics.getCardsPlayed();
		
		System.out.println("");
		
		if (gamephase == 1){
			if ( strategy.equals("BS") || strategy.equals("HL") ){
				str = "b";
			}
			else{
				str = this.strategies.determineAceFiveStrategy(acefivecount, minbet, bet, maxbet, cardsplayed);				
			}
			
		}else if (gamephase == 2){
			str = "d";
		} else if ( gamephase == 3){
			if ( strategy.equals("BS") || strategy.equals("BS-AF") ){
				str = this.strategies.determineBasicStrategyCommand(phand , dhand);
			}else{
				str = this.strategies.determineHiLowStrategyCommand(trueCount, phand, dhand);
			}
		} else
			return "q";
		System.out.println(str);
		return str;
	}
	
	
	/**
	 * Plays multiple games of BlackJack until the player runs out of balance or
	 * the number of shuffles have been matched with the parameter shuffle.
	 */
	public void playGames(){
		
		int sNumber = 0;
		float shoeplayed = 0;
		
		// Creates a deck.
		this.fillShoe( this.shoesize );
		this.statistics.setShoesize(this.shoesize);		
		
		// Cycle of games.
		while ( gameover != true ){
			// Play one game of blackjack.
			this.playOneGame();
			
			//Check if it is needed to reshuffle the shoe.
			shoeplayed = (float)(this.statistics.getCardsPlayed() ) / (float)(this.shoesize * Deck.DECKSIZE ) * 100 ;
			if ( shoeplayed >= this.shufflepercent ) {
				this.reshuffleDeck();
				sNumber++;

				this.statistics.resetCardCounting();
	
			}
			
			// Check if the number of games played matches the number of shuffles performed.
			if( sNumber == this.numberOfShuffles )
				gameover = true;
			// Check if the Player still has enough balance to play.
			if ( this.hasEnoughBalance(this.getMinbet()) == false )
				gameover = true;
			
		}	
		System.out.println("");
		this.commandStatistics();

	}
	
	
	
	
	
	
}
