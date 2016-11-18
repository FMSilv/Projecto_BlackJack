package blackjack;

import java.util.Scanner;

/**
 * Class representative of the body of a BlackJack game.
 * The game is characterized by having a Player, a Dealer, and a shoe.
 * The class also has some fields that represent fixed restrictions to the game
 * like the minimum and maximum bets allowed.
 * Some fields are used as flags to indicate information about the state of the game
 * at a given time, like gamephase, cansiderules, stopdealer and gameover.
 * 
 * Statistics from the game are present and card-counting strategies are also employed
 * with the help of statistics.
 * 
 * This class hold all methods necessary to run a basic game of BlackJack.
 * 
 * Fields are all protected instead of private so we can extend the class while having direct
 * access to the fields in the sub-class, otherwise the number of methods ( setters and getters )
 * would be unnecessarily large.
 * 
 * @author Filipe Silvério
 * @author Miguel Laranjeira
 *
 */
public abstract class GameBody {

	public enum Result { WINBYBJ() , WIN() , TIE() , TIEBYBJ() , LOSE() , LOSEBYBJ() };
	
	//***********************************************************************************************
	
	/**
	 * [Field] Shoe for the current game.
	 */
	protected Deck shoe;
	
	/**
	 * [Field] Dealer for the game.
	 */
	protected Dealer dealer;
	
	/**
	 * [Field] Player for the game.
	 */
	protected Player player;
	
	/**
	 * [Field] Indicates which of the player's hand the game is focused on a
	 * given time of the blackjack game. Player can naturally have more than 
	 * one hand if he splits at some point in the game.
	 */
	protected PHand playinghand;
		
	/**
	 * [Field] Input Parameter - Minimum bet allowed.
	 */
	private int minbet;
	
	/**
	 * [Field] Input Parameter - Maximum bet allowed.
	 */
	private int maxbet;
			
	/**
	 * [Field] Indicates the stage at which the player is when he is playing.
	 * During the player's turn to play there are 3 stages.
	 */
	protected int gamephase;
	
	/**
	 * [Field] Flag used to indicate if side rules can be used by the player at
	 * a certain stage of the player's turn to play.
	 */
	protected boolean cansiderules;
	
	/**
	 * [Field] Flag used to indicate if the dealer needs to play after the player
	 * ends his turn. If stopdealer == true the dealer is stopped and must not play
	 * (take cards from the deck). This flag is useful for when the player goes bust
	 * or surrenders, therefore the dealer does not play.
	 */
	protected boolean stopdealer;
		
	/**
	 * Flag that indicates if the interactive mode games should end/stop.
	 */
	protected boolean gameover;
	
	/**
	 * [Field] Statistics for the game. Includes Card counting counters.
	 */
	protected Statistics statistics;

	/**
	 * [Field] Strategies that can be use in the blackjack game to determine
	 * the best results. If the player asks for advice these strategies will
	 * be checked to determine the best course of action to be taken by the
	 * player at a given play.
	 */
	protected Strategies strategies;

	//***********************************************************************************************
	
	/**
	 * GameBody Constructor
	 * 
	 * Initiates the class fields.
	 */
	public GameBody( Arguments arguments ) {
		this.minbet = arguments.getMinBet();
		this.maxbet = arguments.getMaxBet();
		this.dealer = new Dealer();
		this.player = new Player( arguments.getBalance() );
		this.playinghand = null;
		this.gamephase = 0;
		this.cansiderules = true;
		this.shoe = null;
		this.gameover = false;
		this.statistics = new Statistics( arguments.getBalance() , 1 );
		this.strategies = new Strategies();
	}
	
	
	//***********************************************************************************************
	
	/**
	 * Getter for minbet.
	 * 
	 * @return	Minimum bet allowed.
	 */
	public int getMinbet(){
		return this.minbet;
	}
	
	/**
	 * Getter for maxbet.
	 * 
	 * @return	Maximum bet allowed.
	 */
	public int getMaxbet(){
		return this.maxbet;
	}
	
	
	/**
	 * Check if there is enough balance to cover for a specific bet.
	 * 
	 * @param 	bet		value of the bet
	 * @return	true 	if there is enough balance.
	 */
	public boolean hasEnoughBalance( int bet ){
		if ( player.getBalance() > bet )
			return true;
		else
			return false;
	}
	
	
	/**
	 * Reshuffle the shoe.
	 * Creates a new Shoe with the same number of cards of the previous shoe
	 * and shuffles the cards.
	 * The old shoe is eventually collected by the garbage collector.
	 */
	public void reshuffleDeck(){
		int shoesize = shoe.getShoeSize();
		this.shoe = new Deck(shoesize);
		this.shoe.shuffleCards();
	}
	
	
	/**
	 * Deal the first 2 cards from the shoe to the dealer
	 * followed by 2 cards from the shoe to the player.
	 * The 2nd Card dealt to the dealer is hidden (holecard).
	 */
	public void dealFirstCards(){		
		
		Card card;
		// Deal the following 2 cards to the dealer.
		card = shoe.retrieveCard();
		dealer.addCardToHand(card);
		statistics.updateCardCounting(card);
		card = shoe.retrieveCard();
		dealer.addHolecard(card);
		
		// Deal first 2 cards to the player.		
		player.newHand();
		card = shoe.retrieveCard();
		player.addCardToHand( card , 0 );
		statistics.updateCardCounting(card);
		card = shoe.retrieveCard();
		player.addCardToHand( card , 0 );
		statistics.updateCardCounting(card);
	}
	
	/**
	 * Show (system.out) the dealer's hand.
	 */
	public void showDealerCards(){
		Hand hand;
		hand = dealer.getHand();
		System.out.print("Dealer's hand " + hand.listCards() );
		System.out.println("("+hand.getHandValue()+")" );
	}
	
	/**
	 * Show (system.out) one specific hand of the player.
	 * 
	 * @param handindex	index of the hand that should be printed.
	 */
	public void showPlayerCards( int handindex){
		Hand hand;
		hand = player.getHand(handindex);
		System.out.print("Player's hand: " + hand.listCards() );
		System.out.println("("+hand.getHandValue()+")");
	}

	//***********************************************************************************************
	
	/**
	 * Function to show possible commands that can be used by the player.
	 */
	public void commandHelp(){
		System.out.println("Command    Meaning");
		System.out.println("--------------------------------");
		System.out.println("--------------------------------");
		System.out.println("b          Bet previous amount");
		System.out.println("b VALUE    Bet VALUE");
		System.out.println("--------------------------------");
		System.out.println("$          Shows current balance");
		System.out.println("--------------------------------");
		System.out.println("d          deal");
		System.out.println("--------------------------------");
		System.out.println("h          Draw a card");
		System.out.println("s          Stand");
		System.out.println("--------------------------------");
		System.out.println("i          insurance");
		System.out.println("u          surrender");
		System.out.println("p          splitting");
		System.out.println("2          double");
		System.out.println("--------------------------------");
		System.out.println("ad         advice");
		System.out.println("st         statistics");
		System.out.println("--------------------------------");
	}
		
	/**
	 * Prints in the System output the main statistics of the game.
	 */
	public void commandStatistics() {
		
		float balancepercent = 100*(float)(this.player.getBalance()) / (float)(this.statistics.getInicialBalance());
		
		System.out.println("BJ P/D	"+this.statistics.getPlayerBJs()+" / "+this.statistics.getDealerBJs());
		System.out.println("Win	"+this.statistics.getWins());
		System.out.println("Lose	"+this.statistics.getLosses());
		System.out.println("Push	"+this.statistics.getPushes());
		System.out.println("Balance	"+this.player.getBalance()+" ("+(balancepercent-100)+"%)");
	}	

	/**
	 * Method for the command for advice.
	 */
	public void commandAdvice( ){
		
		String basiccommand = "";
		String hilowcommand = "";
		String acefivecommand = "";
		PHand phand;
		Hand dhand;
		float truecount;	
		
		int acefivecount = this.statistics.getAcefiveCount();
		int minbet = this.getMinbet();
		int bet = player.getBet();
		int maxbet = this.getMaxbet();
		int cardsplayed = this.statistics.getCardsPlayed();
		
		
		if ( this.gamephase == 1 ){
			acefivecommand = strategies.determineAceFiveStrategy(acefivecount, minbet, bet, maxbet, cardsplayed);
			System.out.println("acefive		"+acefivecommand);
		}
		else if ( this.gamephase == 2 ){
			System.out.println("deal - (d)");
		} 
		else if ( this.gamephase == 3 ){
			phand = this.playinghand;
			dhand = dealer.getHand();
			truecount = statistics.getTrueCount();
			basiccommand = strategies.determineBasicStrategyCommand( phand , dhand );
			System.out.println("basic	"+basiccommand);
			
			hilowcommand = strategies.determineHiLowStrategyCommand( truecount , phand, dhand);
			System.out.println("hilow	"+hilowcommand);			
		}
		else
			System.out.println("[commandAdvice] Error - Incorrect gamephase");	
		
	}
	
	/**
	 * Method to run the Bet command issued by the player.
	 * @param command	command issued by the player
	 * @return	true if the command was correctly executed, false otherwise.
	 */
	public boolean commandBet( String command  ){
		
		Scanner scan = new Scanner (command);
		int bet;
		float newbalance;
				
		scan.next();
				
		if (scan.hasNext() == false ){	// No bet value is provided in the command.
			if ( player.getBet() == 0 )	{	// Bet the minimum value
				bet = this.minbet;
			}
			else {	// Bet the same as before.
				bet = player.getBet();			
			}
		}
		else if ( scan.hasNextInt() ){	// Bet value is provided in the command.
			bet = scan.nextInt();
			
		} else {	// Illegal command.
			System.out.println(command + ": illegal command");
			scan.close();
			return false;
		}
		
		if ( bet < minbet ) {// Check if bet is not lower then minbet
			System.out.println("You can't bet that value because it's lower then minbet");	
			scan.close();
			return false;			
		}
		else if ( bet > this.maxbet ) {	// Check if bet is less than maxbet allowed
			System.out.println("You can't bet that value because it's higher than maxbet");	
			scan.close();
			return false;
		} else if ( bet > player.getBalance() ){ // Check if player has enough balance.
			System.out.println("You can't bet that value because you don't have enough balance");
			scan.close();
			return false;			
		} else {
			// If there is enough balance remove the bet value from it.
			player.setBet(bet);
			newbalance = player.getBalance() - bet;
			player.setBalance(newbalance);
			System.out.println("Player is betting " + bet);
		}
		
		scan.close();
		return true;
	}
	
	/**
	 * Method to run the Deal command.
	 */
	public void commandDeal() {
		
		this.dealFirstCards();
		this.showDealerCards();
		
	}
	
	/**
	 * Method to run the Hit command
	 * @param handindex	index of player's hand to do the hit command.
	 * @return hasbusted	flag indicating if the player busted after hitting.
	 */
	public boolean commandHit( int handindex ) {
		Hand hand;
		boolean hasbusted;
		Card card;
		
		hasbusted = false;
		
		card = shoe.retrieveCard();
		this.statistics.updateCardCounting(card);
		player.addCardToHand( card, handindex );
		
		System.out.println("Player hits.");
		
		hand = player.getHand(handindex);		
		if ( hand.getHandValue() > 21 ){
			showPlayerCards(handindex);
			System.out.println("Player busts");
			hasbusted = true;
		}
			
		return hasbusted;
	}
		
	/**
	 * Method to run the Split command
	 * @param handindex	index of the player's hand to be split.
	 * @return	true if the split was possible/successful.
	 */
	public boolean commandSplit( int handindex ) {
		PHand hand;
		Card card;
		
		hand = player.getHand(handindex);
		if ( hand.canSplit() != true ){
			System.out.println("You can't split this hand.");
			return false;
		}
			
		this.player.SplitHand(handindex);
		card = shoe.retrieveCard();
		this.statistics.updateCardCounting(card);
		this.player.addCardToHand( card , handindex);
		card = shoe.retrieveCard();
		this.statistics.updateCardCounting(card);
		this.player.addCardToHand( card , player.getNumberOfHands()-1 );
		
		return true;
	}

	/**
	 * Method to run the Double command.
	 * 
	 * @param handindex	index of the player's hand where you doubled the bet.
	 * @return	true if the double was possible/successful.
	 */
	public boolean commandDouble( int handindex ) {

		float newbalance = player.getBalance();
		int bet = player.getBet();
		PHand hand = player.getHand(handindex);
		
		// Check if you can double
		if ( player.getBalance() >= 2*player.getBet() ){
			newbalance -= (float)(1*bet);
			player.setBalance(newbalance);
			hand.setDoublee(true);
			return true;
		} else{
			System.out.println("You can't double your bet. Not enough balance.");
			return false;			
		}	
		
	}
	
	/**
	 * Method to run the Surrender command.
	 * 
	 * @param handindex	index of the player's hand to apply surrender
	 */
	public void commandSurrender( int handindex ) {

		PHand hand = player.getHand(handindex);
		
		hand.setSurrender(true);
		
	}
	
	/**
	 * Method to run the Insurance command.
	 * 
	 * @param handindex of the player's hand to apply insurance.
	 * @return	true if the insurance was possible/successful.
	 */
	public boolean commandInsurance ( int handindex ){
		
		PHand phand = player.getHand(handindex);
		Hand dhand = dealer.getHand();
		float newbalance = player.getBalance();
		int bet = player.getBet();
		
		if ( bet > newbalance )	{ // Check if there is enough balance to do insurance.
			System.out.println("You can't insure in this situation.");
			return false;
		}
		if ( dhand.getNumberofaces() != 1 ){	// Check if dealer has an Ace.
			System.out.println("You can't insure in this situation.");
			return false;			
		}
		phand.setInsurance(true);
		
		return true;
	}
	
	//***********************************************************************************************
	
	/**
	 * Function to determine the command to be executed by the player.
	 * In this class it is basically a signature and will be overriden in the
	 * subclasses.
	 * 
	 */
	public String determineCommand() {
		return null;
	}
			
	/**
	 * This function executes the command chosen by the player regarding
	 * a specific hand and returns whether the player has finished his
	 * round playing.
	 * 
	 * @param command	-> command to be executed.
	 * @param handindex	-> index of the hand
	 * @return true if player has ended his round, false otherwise.
	 */
 	public boolean executeCommand( String command , int handindex ){
		boolean isPlayerDone = false;
		boolean commandsuccess = false;
		String comm;
		Scanner scan = new Scanner(command);		
		
		if (scan.hasNext() != true){
			System.out.println("[executeCommand] Error! No command was found.");
			comm = "error";
		}
		else
			comm = scan.next();	
		
		// Decode which command the player wishes to execute.
		switch (comm) {
		// Ask for help command.
		case("help"):
			this.commandHelp();
			break;
		// Bet Command
		case("b"):			// Bet	- DONE
			if ( gamephase != 1 ) {
				System.out.println(command + ": illegal command");
				break;
			}
			commandsuccess = commandBet ( command );
			if ( commandsuccess == true )
				gamephase ++;
			break;
		// Show current balance command
		case("$"):
			System.out.println("player current balance is " + player.getBalance() );
			break;
		// Deal the cards command
		case("d"):
			if ( gamephase != 2 ) {
				System.out.println(command + ": illegal command");
				break;
			}
			commandDeal();
			gamephase ++;
			break;
		// Hit command
		case("h"):
			if ( gamephase != 3 ) {
				System.out.println(command + ": illegal command");
				break;
			}
			this.cansiderules = false;
			isPlayerDone = commandHit(handindex);
			break;
		// Stand command.
		case("s"):
			if ( gamephase != 3 ) {
				System.out.println(command + ": illegal command");
				break;
			}
			this.cansiderules = false;
			isPlayerDone = true;
			this.stopdealer = false;
			System.out.println("Player stands.");
			break;
		// Insurance command
		case("i"):
			if ( gamephase != 3 ) {
				System.out.println(command + ": illegal command");
				break;
			}	
			commandsuccess = this.commandInsurance(handindex);
			if ( commandsuccess == true ){
				this.cansiderules = false;
			}
			break;
		// Surrender Command
		case("u"):
			if ( cansiderules != true || gamephase != 3 ) {
				System.out.println(command + ": illegal command");
				break;
			}	
			this.commandSurrender(handindex);
			this.cansiderules = false;
			isPlayerDone = true;
			break;
		// Split Cards command.
		case("p"):
			if ( cansiderules != true  || gamephase != 3 ){
				System.out.println( command + ": illegal command");
				break;				
			}
			this.commandSplit( handindex );
			break;
		// Double the bet command.
		case("2"):
			if ( cansiderules != true || gamephase != 3 ) {
				System.out.println(command + ": illegal command");
				break;
			}	
			commandsuccess = this.commandDouble(handindex);
			if ( commandsuccess == true ){
				isPlayerDone = this.commandHit(handindex);
				if ( isPlayerDone != true )
					this.stopdealer = false;
				isPlayerDone = true;				
			}
			break;
		// Ask for advice command.
		case("ad"):
			this.commandAdvice();
			break;
		// Show statistics command.
		case("st"):
			this.commandStatistics();
			break;
		// Quit the game command.
		case("q"):
			if ( gamephase != 1 ) {
				System.out.println(command + ": illegal command");
				break;
			}	
			System.out.println("You decided to stop playing.");
			isPlayerDone = true;
			this.gameover = true;
			break;
		default:
			System.out.println("Invalid command. Type help to see valid commands");
			break;
		}
		scan.close();
		return isPlayerDone;
	}
 	
	/**
	 * Sequence of actions the player does till he ends his play.
	 * His actions stop when he "Stands" , "Busts" or achieves "BlackJack"
	 */
	public void playerPlays(){
		boolean playerdone = false;		// tag that indicates if player has finished his play.
		String command;
		
		this.gamephase = 1;
		
		// 1st Stage of Player interaction: Betting.
		while ( this.gamephase == 1 && playerdone == false ){
			command = this.determineCommand();
			playerdone = this.executeCommand(command , 0);
		}		
		// 2nd Stage of Player interaction: Deal the Cards.
		while ( this.gamephase == 2 && playerdone == false ){
			command = this.determineCommand();
			playerdone = this.executeCommand(command , 0);
		}
		// 3rd Stage of Player interaction: hit, stand or apply side rules.
		for ( int nh = 0 ; nh < player.getNumberOfHands() ; nh++ ){
			this.playinghand = this.player.getHand(nh);
			playerdone = false;
			this.cansiderules = true;
			while ( playerdone != true ){
				this.showPlayerCards(nh);
				command = this.determineCommand();
				playerdone = this.executeCommand(command , nh);			
			}
				
		}
		
	}
		
	/**
	 * Sequence of actions the dealer does till the end of his play.
	 * Dealer will hit if the value of his hand is lower than 17, otherwise
	 * the dealer will stand.
	 */
	public void dealerPlays(){
		Card card;
		
		card = dealer.turnHoleCard();
		statistics.updateCardCounting(card);
		showDealerCards();
		
		while ( dealer.getHand().getHandValue() < 17 ) {
			card = shoe.retrieveCard();
			dealer.addCardToHand(card);
			statistics.updateCardCounting(card);
			System.out.println("Dealer hits.");
			this.showDealerCards();
		}
		
	}
		
	/**
	 * Functions that determines who won the game (player or dealer).
	 * 
	 * @return result	-> LOSEBYBJ if Player lost by BlackJack
	 * 					-> LOSE if Player Lost
	 * 					-> TIE if Player Pushed
	 * 					-> TIEBYBJ if Player Pushed by Blackjack
	 * 					-> WIN if Player Won
	 * 					-> WINBYBJ if Player Won by Blackjack
	 * 					
	 */
	public Result calcResult( PHand playerhand ) {
		Hand dealerhand = dealer.getHand();
		int ps = playerhand.getHandValue();
		int ds = dealerhand.getHandValue();
		
		if ( playerhand.getSurrender() == true ){
			System.out.println("Player has surrendered!");
			return Result.LOSE;
		}
		
		if ( ps > 21 ) {	// Player has busted.
			System.out.println("Player has busted! You LOSE.");
			return Result.LOSE;
		} else if ( ds > 21 ) {	//Dealer has busted.
			if ( playerhand.hasBlackJack() == true ){
				System.out.println("Dealer has busted! You WIN by BlackJack!");
				return Result.WINBYBJ;
			}
			else {
				System.out.println("Dealer has busted! You WIN!");
				return Result.WIN;			
			}
		} else if ( ps > ds ){	// Player Score > Dealer Score
			if ( playerhand.hasBlackJack() == true ){
				System.out.println("Player's hand is better than the dealer's. You WIN by BlackJack!");
				return Result.WINBYBJ;
			}
			System.out.println("Player's hand is better than the dealer's. You WIN. Congratz!");
			return  Result.WIN;	
		} else if ( ds > ps ) {
			if ( dealerhand.hasBlackJack() == true ) {
				System.out.println("Player's Hand is worse than the dealer's. You LOSE by Blackjack.");
				return Result.LOSEBYBJ;
			}
			System.out.println("Player's Hand is worse than the dealer's. You LOSE.");
			return Result.LOSE;
		} else if ( ds == ps ) {	// Player Score == Dealer Score
			if ( playerhand.hasBlackJack() && !dealerhand.hasBlackJack() )	{
				System.out.println("Player's hand is a BlackJack and the dealer's is not. You WIN by BlackJack.");
				return Result.WINBYBJ;
			} else if ( playerhand.hasBlackJack() && dealerhand.hasBlackJack() ) {
				System.out.println("Player's Hand and Dealer's Hand are a BlackJack. You PUSH.");
				return Result.TIEBYBJ;
			}
			else if ( !playerhand.hasBlackJack() && dealerhand.hasBlackJack() ) {
				System.out.println("Player's hand is not a BlackJack and the dealer's is. You LOSE by BlackJack.");
				return Result.LOSEBYBJ;				
			}
			System.out.println("Player's hand is worth the same as the dealer's. You PUSH");
			return Result.TIE;
		}
		
		System.out.println("Caso Imprevisto!!!");
		System.out.println("");
		return Result.TIE;
	}
	
	/**
	 * Function that applies the winnings to the player according to the result
	 * of the respective hand.
	 * 
	 * @param hand	player's hand that was evaluated.
	 * @param result	result of the hand ( win, loss, etc)
	 */
	public void executePayoffs( PHand hand , Result result ){
		
		float balance = player.getBalance();
		int bet = player.getBet();
		float returns = 0;
		
		// Assumindo que apenas Split eh stackavel com as outras side rules.
		// Situacao de Double
		if ( hand.getDoublee() == true ){
			bet = 2*bet;
		}
		// Situacao de Insurance
		if ( hand.getInsurance() == true ){
			if (dealer.getHand().hasBlackJack() == true)
				returns += 2*bet;
			else
				returns += 0*bet;
		}
		// Situacao de Surrender
		if ( hand.getSurrender() == true ){
			returns = (float) (0.5*bet);
		}
		// Situacao Normal
		else {
			if ( result == Result.WINBYBJ ) {
				if ( hand.getSplit() == true )	// Player split hands?
					returns += (float) (2.5*bet);
				else {
					returns += (float) (2*bet);
				}
			}
			else if ( result == Result.WIN )
				returns += (float) (2*bet);
			else if ( result == Result.LOSE || result == Result.LOSEBYBJ )
				returns += (float) (0*bet);
			else
				returns += (float) (1*bet);			
		}		
		player.setBalance( balance + returns );
		System.out.println("Player's Current Balance is " + player.getBalance() );
		
	}
		
	/**
	 *  Method for playing One and only one session of Blackjack.
	 *  The game features
	 *  1st the Player plays (bet, ask for deal , hit/stand/siderules).
	 *  2nd the Dealer plays (hit/stand according to the dealer's rules).
	 *  3rd the result for each of the player's hands is calculated.
	 *  4th the payoffs of each hand are paid to the player (according to the result)
	 *  Finnally both the player and dealer's hands are cleaned.
	 */
	public void playOneGame(){
		Result result;
		
		
		
		this.stopdealer = true;		
		this.playerPlays();
		
		// Se o Player não der bust ou Surrender o Dealer deve prosseguir com o jogo.
		// Caso contrário o dealer ganha e o player perde e acaba o jogo.
		if ( stopdealer != true )
			this.dealerPlays();	
		
		for ( int hi = 0 ; hi < player.getNumberOfHands() ; hi++){
			result = this.calcResult ( player.getHand(hi) );
			this.executePayoffs( player.getHand(hi) , result );
			this.statistics.updateHandStatistics(result);
		}
		
		this.statistics.updateGameStatistics();
		
		this.player.clearHands();
		this.dealer.clearHand();
				

	}
	
	/**
	 * Method for playing multiple games of BlackJack. In this class it's only
	 * a signature. In the subclasses the method is overriden according to the
	 * game mode.
	 */
	public void playGames(){
		
	}
	
	
}
