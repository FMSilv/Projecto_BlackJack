package blackjack;

import java.util.HashMap;

/** 
 * @author Filipe Silvério
 * @author Miguel Laranjeira
 * 
 * Class used to store the commands of a specific hand using the strategies to card counting.
 * It also returns the corresponding command for a specific hand and strategy.
 *
 */
public class Strategies {
	
	/**
	 * Two dimensional string array that stores the commands from the hard table.
	 */
	private final String[][] hardTable = new String[17][10];
	/**
	 * Two dimensional string array that stores the commands from the soft table.
	 */
	private final String[][] softTable = new String[9][10];
	/**
	 * Two dimensional string array that stores the commands from the pair table.
	 */
	private final String[][] pairTable = new String[10][10];
	
	/**
	 * Hash map that stores the Decision Class containing the commands for a specific and set of hands.
	 */
	private final HashMap<String, Decisions> HMap = new HashMap<String, Decisions>();
	
	//***********************************************************************************************	
	
	/**
	 * 
	 * Enum of commands so we can associate them as the strings asked from the terminal.
	 *
	 */
	public enum Command{
		STAND("s"), HIT("h"), SPLIT("p"), DOUBLE("2"), INSURANCE("i"), SURRENDER("u"), BASIC("#B");
		
		private String decodedCommands;
		
		Command ( String decodedCommands ){
			this.decodedCommands = decodedCommands;
		}
		
		public String getDecodedCommands(){
			return decodedCommands;
		}
	};

	/**
	 * 
	 * Class used to store the two possible commands for the Illustrious 18 and Fab4 strategies in the has map.
	 *
	 */
	class Decisions{
		
		private int hedgeNumber;
		private Command decision1;
		private Command decision2;
		
		public Decisions( int hedgeNumber, Command opt1, Command opt2 ){
			this.hedgeNumber = hedgeNumber;
			this.decision1 = opt1;
			this.decision2 = opt2;
		}
		
		public int showDecisionsHedgeNumber(){
			return this.hedgeNumber;
		}
		public Command showFirstDecisionsCommand(){
			return this.decision1;
		}
		public Command showSecondDecisionsComand(){
			return this.decision2;
		}

		
		
		/**
		 * @Override	Override of the class decisions to string.
		 */
		public String toString() {
			return "Decisions [hedgeNumber=" + hedgeNumber + ", decision1="
					+ decision1 + ", decision2=" + decision2 + "]";
		}
		
		
	}

	//***********************************************************************************************
	
	/**
	 * Strategy constructor that fills the strategy tables and the hash map with commands(strings).
	 */
	public Strategies() {
		
	//BASIC STRATEGY
		//Hard table
		fillHardTable();
		
		//Soft table
		fillSoftTable();
		
		//Pairs table
		fillPairsTable();
		
		
	//HI-LO STRATEGY
		//Illustrous 18 and Fab4
		fillHashMap();
		
			
	}

	//***********************************************************************************************
	//Basic strategy
	/**
	 * Fills the hard table(without aces) with commands(Strings).
	 */
	public void fillHardTable(){
		for( int row = 0; row < this.hardTable.length; row++ ){
			for( int column = 0; column < this.hardTable[row].length; column++ ){
				this.hardTable[row][column] = "H";
			}
		}
		for( int row = 12; row < this.hardTable.length; row++ ){
			for( int column = 0; column < this.hardTable[row].length; column++ ){
				this.hardTable[row][column] = "S";
			}
		}
		for( int row = 8; row < this.hardTable.length-5; row++ ){
			for( int column = 0; column < this.hardTable[row].length-5; column++  ){
				this.hardTable[row][column] = "S";
			}
		}
		this.hardTable[10][8] = "Rh";
		for( int column = 7; column < this.hardTable[column].length; column++ ){
			hardTable[11][column] = "Rh";
		}
		for( int column = 2; column < this.hardTable[7].length-5; column++ ){
			this.hardTable[7][column] = "S";
		}
		for( int row = 5; row < this.hardTable.length-10; row++ ){
			for( int column = 0; column < this.hardTable[row].length-2; column++ ){
				this.hardTable[row][column] = "Dh";
			}
		}
		for( int column = 1; column < this.hardTable[4].length-5; column++ ){
			this.hardTable[4][column] = "Dh";
		}
		this.hardTable[6][8] = "Dh";
	}
	
	/**
	 * Fills the soft table(with aces) with commands(Strings).
	 */
	public void fillSoftTable(){
		for( int row = 0; row < this.softTable.length; row++ ){
			for( int column = 0; column < this.softTable[row].length; column++ ){
				this.softTable[row][column]= "H";
			}
		}
		for( int row = 6; row < this.softTable.length; row++ ){
			for( int column = 0; column < this.softTable[row].length; column++ ){
				this.softTable[row][column] = "S";
			}
		}
		this.softTable[5][0] = "S";
		this.softTable[5][5] = "S";
		this.softTable[5][6] = "S";
		for( int row = 0; row < this.softTable.length-4; row++ ){
			for( int column = 3; column < this.softTable[row].length-5; column++ ){
				this.softTable[row][column] = "Dh";
			}
		}
		for( int column = 1; column < this.softTable[5].length-5; column++ ){
			this.softTable[5][column] = "Ds";
		}
		for( int row = 2; row < this.softTable.length-4; row++ ){
			this.softTable[row][2] = "Dh";
		}
		this.softTable[4][1] = "Dh";
	}

	/**
	 * Fills the pair table with commands(Strings).
	 */
	public void fillPairsTable(){
		for( int row = 0; row < this.pairTable.length; row++ ){
			for( int column = 0; column < this.pairTable[row].length; column++ ){
				this.pairTable[row][column] = "H";
			}
		}
		for( int row = 6; row < this.pairTable.length; row++ ){
			for( int column = 0; column < this.pairTable[row].length; column++ ){
				this.pairTable[row][column] = "P";
			}
		}
		for( int row = 0; row < this.pairTable.length-8; row++ ){
			for( int column = 2; column < this.pairTable[row].length-4; column++ ){
				this.pairTable[row][column] = "P";
			}
		}
		for( int column = 0; column < this.pairTable[5].length-4; column++ ){
			this.pairTable[5][column] = "P";
		}
		for( int column = 1; column < this.pairTable[4].length-5; column++ ){
			this.pairTable[4][column] = "P";
		}
		for( int column = 0; column < this.pairTable[3].length-2; column++ ){
			this.pairTable[3][column] = "Dh";
		}
		for( int column = 0; column < this.pairTable[8].length; column++ ){
			this.pairTable[8][column] = "S";
		}
		this.pairTable[7][5] = "S";
		this.pairTable[7][8] = "S";
		this.pairTable[7][9] = "S";
	}
	
	
	/**
	 * Gets the command needed from the hard table.
	 * 
	 * @param playerScore	Player score has the input parameter.
	 * @param dealerScore	Dealer score has the input parameter.
	 * @return				Command has a string.
	 */
	public String getHardTableCommand( int playerScore, int dealerScore ){
		int ps = playerScore - 5;
		int ds = dealerScore - 2;
		
		return this.hardTable[ps][ds];
	}
	
	/**
	 * Gets the command needed from the soft table.
	 * 
	 * @param playerScore	Player score has the input parameter.
	 * @param dealerScore	Dealer score has the input parameter.
	 * @return				Command has a string
	 */
	public String getSoftTableCommand( int playerScore, int dealerScore ){
		int ps = playerScore - 13;
		int ds = dealerScore - 2;
		
		return this.softTable[ps][ds];
	}
	
	/**
	 * Gets the command needed from the pair table.
	 * 
	 * @param playerCardNumber	Score of an one player hand.
	 * @param dealerScore		Dealer score has the input parameter.
	 * @return					Command has a string.
	 */
	public String getPairTableCommand( int playerCardNumber, int dealerScore ){
		int pcn = playerCardNumber - 2;
		int ds = dealerScore - 2;
		
		return this.pairTable[pcn][ds];
	}
	
	
	/**
	 * Gets the string from the basic strategy tables and converts it to the Command ENUM type.
	 * 
	 * @param s		String returned by basic strategy tables
	 * @param h		Player hand to check if its possible to do certain commands(DOULBE and SURRENDER):
	 * @return		Basic strategy Command ENUM type.
	 */
	public Command decodeBasicCommand( String s, PHand h ){
			
		Command comm;
		
		switch( s ){
		case("H"):
			comm = Command.HIT;
			break;
		case("P"):
			comm = Command.SPLIT;
			break;
		case("S"):
			// This is only called for the Pairs table so we are sure the hand is splitable.
			comm = Command.STAND;
			break;
		case("Dh"):
			// Double se tiver duas cartas e nao pode ter sido feito nenhuma side rule anteriormente.
			if( h.getNumberOfCards() == 2 && !h.getInsurance() && !h.getDoublee() && !h.getSurrender() ){
				comm = Command.DOUBLE;
			}else{
				comm = Command.HIT;
			}
			break;
		case("Ds"):
			if( h.getNumberOfCards() == 2 && !h.getInsurance() && !h.getDoublee() && !h.getSurrender() ){
				comm = Command.DOUBLE;
			}else{
				comm = Command.STAND;
			}
			break;
		case("Rh"):
			if( h.getNumberOfCards() == 2 && !h.getInsurance() && !h.getDoublee() && !h.getSurrender() && !h.getSplit() ){
				comm = Command.SURRENDER;
			}else{
				comm = Command.STAND;
			}
			break;
		default:
			comm =Command.BASIC;
			break;
		}
			return comm;
		}

	/**
	 * Function to determine the string command from the Basic strategy and use it as an input parameter in the terminal.
	 * 
	 * @param ph	Received hand from player.
	 * @param dh	Received hand from dealer.
	 * @return		Returns the string command from the Basic strategy to use as a input parameter in the terminal.
	 */
	public String determineBasicStrategyCommand( PHand ph, Hand dh ){
		Command c;
		
		// condicao if para a tabela par
		if( ph.getNumberOfCards() == 2 && ph.getCard(0).getCardValue() == ph.getCard(1).getCardValue() )
		{
			String s = getPairTableCommand( ph.getCard(0).getCardValue(), dh.getHandValue() );
			c = decodeBasicCommand( s, ph );
		}
		else if ( ph.getNumberofaces() != 0 && ph.getAcesworth11() == ph.getNumberofaces() )
		{
		//condicao else para a tabela soft
			String s = getSoftTableCommand( ph.getHandValue(), dh.getHandValue() );
			c = decodeBasicCommand( s, ph );
		}
		else
		{
		//condicao else if para a tabela hard
			String s = getHardTableCommand( ph.getHandValue(), dh.getHandValue() );
			c = decodeBasicCommand( s, ph );
		}

		return c.getDecodedCommands();
	}
	
	//***********************************************************************************************
	//Hi-LOW Strategy
	/**
	 * Value of a card from the perspective of Hi-Low strategy.
	 * 
	 * @param c		Gets a card as an input value.
	 * @return		1, 0 or -1 depending on the card value
	 */
	public static final int decodeHiLowCardValue( Card c ){
		int cardValue = c.getCardValue();
		if( cardValue >= 2 && cardValue <=6 ){
			return 1;
		} else if( cardValue >= 7 && cardValue <= 9 ){
			return 0;
		} else if( cardValue == 10 || cardValue == 11 ){
			return -1;
		}
		return 999;
	}

	/**
	 * Function that fills the hash map content.
	 */
	public void fillHashMap(){
		//Illustrious18
		Decisions decision0 = new Decisions( 3, Command.INSURANCE, Command.INSURANCE );
		Decisions decision1 = new Decisions( 0, Command.STAND, Command.HIT );
		//Decisions decision2 = new Decisions( 4, Command.STAND, Command.HIT );
		Decisions decision3 = new Decisions( 5, Command.SPLIT, Command.STAND );
		Decisions decision4 = new Decisions( 4, Command.SPLIT, Command.STAND );
		Decisions decision5 = new Decisions( 4, Command.DOUBLE, Command.HIT );
		Decisions decision6 = new Decisions( 2, Command.STAND, Command.HIT );
		Decisions decision7 = new Decisions( 3, Command.STAND, Command.HIT );
		Decisions decision8 = new Decisions( 1, Command.DOUBLE, Command.HIT );
		Decisions decision9 = new Decisions( 1, Command.DOUBLE, Command.HIT );
		Decisions decision10 = new Decisions( 4, Command.DOUBLE, Command.HIT );
		Decisions decision11 = new Decisions( 3, Command.DOUBLE, Command.HIT );
		Decisions decision12 = new Decisions( 5, Command.STAND, Command.HIT );
		Decisions decision13 = new Decisions( -1, Command.STAND, Command.HIT );
		Decisions decision14 = new Decisions( 0, Command.STAND, Command.HIT );
		Decisions decision15 = new Decisions( -2, Command.STAND, Command.HIT );
		Decisions decision16 = new Decisions( -1, Command.STAND, Command.HIT );
		Decisions decision17 = new Decisions( -2, Command.STAND, Command.HIT );

		HMap.put("Insurance", decision0);
		HMap.put("16vT", decision1);
		//HMap.put("15vT", decision2);
		HMap.put("TTv5", decision3);
		HMap.put("TTv6", decision4);
		HMap.put("10vT", decision5);
		HMap.put("12v3", decision6);
		HMap.put("12v2", decision7);
		HMap.put("11vA", decision8);
		HMap.put("9v2", decision9);
		HMap.put("10vA", decision10);
		HMap.put("9v7", decision11);
		HMap.put("16v9", decision12);
		HMap.put("13v2", decision13);
		HMap.put("12v4", decision14);
		HMap.put("12v5", decision15);
		HMap.put("12v6", decision16);
		HMap.put("13v3", decision17);
		
		//Fab4
		Decisions decision18 = new Decisions( 3, Command.SURRENDER, Command.BASIC );
		//Decisions decision19 = new Decisions( 0, Command.SURRENDER, Command.BASIC );
		Decisions decision20 = new Decisions( 2, Command.SURRENDER, Command.BASIC );
		Decisions decision21 = new Decisions( 1, Command.SURRENDER, Command.BASIC );
		
		HMap.put("14vT", decision18);
		//HMap.put("15vT", decision19);
		HMap.put("15v9", decision20);
		HMap.put("15vA", decision21);
		
	}
	
	/**
	 * 	Checks extra conditions to the tables, if none match, gets the command from the hash map.
	 * 
	 * @param trueCount		Running Count/Decks left.
	 * @param ph			Received hand from player.
	 * @param dh			Received hand from dealer.
	 * @return				Decoded command for Hi-Low strategy.
	 */
	public Command decodeHiLowStrategy( float trueCount, PHand ph, Hand dh ){
		String hashInput;
		String playerString;
		String dealerString;

		
		// Player score to String
		if( ph.getHandValue() == 20 && ph.getCard(0).getCardValue() == 10 ){
				playerString = "TT";
		} else {
			playerString = Integer.toString(ph.getHandValue());
		}
		//Dealer score to String
		if( dh.getHandValue() == 10 ){
			dealerString = "T";
		} else if( dh.getHandValue() == 11 ){
			dealerString = "A";
		} else{
			dealerString = Integer.toString(dh.getHandValue());
		}
		
		hashInput = playerString + "v" + dealerString;
		
		// Illustrious 18 - Specific rule - Insurance
		if ( dh.getNumberofaces() == 1 && trueCount >= 3 && ph.getNumberOfCards() == 2 && !ph.getInsurance() && !ph.getDoublee() && !ph.getSurrender() && !ph.getSplit() ){
			return Command.INSURANCE;
		}
		// Illustrious 18 + Fab 4 - Specific rule - 15vT
		else if( hashInput.equals("15vT") ){
			if( trueCount >= 0 && trueCount <= 3 && ph.getNumberOfCards() == 2 && !ph.getInsurance() && !ph.getDoublee() && !ph.getSurrender() && !ph.getSplit() ) {
				return Command.SURRENDER;
			}else if( trueCount >= 4 ){
				return Command.STAND;
			}else{
				return Command.HIT;
			}
		}
		// Illustrious 18 + Fab 4 - All other rules - Access by HashMap
		else if( HMap.get(hashInput) != null ){
			int i = HMap.get(hashInput).hedgeNumber;
			if( trueCount >= i ){
				// Function that check if it is possible to do the side rule received by decision1.
				Command comm = HMap.get(hashInput).decision1;
				return checkCommandRestrictions( comm, ph, hashInput );
			}else{
				return HMap.get(hashInput).decision2;
			}
			
		}else{
			return Command.BASIC;
		}
	}
	
	/**
	 * Checks if the command received was BASIC. If so gets the command from the 
	 * basic strategy otherwise retrieves the command(String) from Hi-Low strategy.
	 * 
	 * @param trueCount		Running Count/Decks left
	 * @param ph			Received hand from player
	 * @param dh			Received hand from dealer
	 * @return				Returns the string command from the Hi-Low strategy to use as a input parameter in the terminal.
	 */
	public String determineHiLowStrategyCommand( float trueCount, PHand ph, Hand dh ){
		Command comm = decodeHiLowStrategy( trueCount, ph, dh );
		
		if( comm == Command.BASIC ){
			return determineBasicStrategyCommand( ph, dh );
		}else{
			return comm.getDecodedCommands();
		}
		
	}
		
	/**
	 * If the decision1 command is SPLIT, DOUBLE or SURRENDER, checks if is possible to execute the command.
	 * 
	 * @param comm			takes an Command as input.
	 * @param ph			takes a player hand as input.
	 * @param hashInput		receives the position of the command on the hash table.
	 * @return				returns the possible command.
	 */
	public Command checkCommandRestrictions( Command comm, PHand ph, String hashInput ){
		if( comm == Command.SPLIT ){
			if( ph.getNumberOfCards() == 2 && !ph.getInsurance() && !ph.getDoublee() && !ph.getSurrender()){
				return HMap.get(hashInput).decision1;
			}
			return HMap.get(hashInput).decision2;
		} 
		else if( comm == Command.DOUBLE ){
			if( ph.getNumberOfCards() == 2 && !ph.getInsurance() && !ph.getDoublee() && !ph.getSurrender()){
				return HMap.get(hashInput).decision1;
			}
			return HMap.get(hashInput).decision2;
		}
		else if( comm == Command.SURRENDER ){
			if( ph.getNumberOfCards() == 2 && !ph.getInsurance() && !ph.getDoublee() && !ph.getSurrender() && !ph.getSplit() ){
				return HMap.get(hashInput).decision2;
			}
			return HMap.get(hashInput).decision2;
		}
		else{
			return comm;
		}
	}
	
	
	// Ace-Five strategy
	/**
	 * Value of a card from the perspective of Ace-Five strategy.
	 * 
	 * @param c		Takes a card as an input.
	 * @return		1 if the card is a 5, -1 if it's an ace, 0 otherwise.
	 */
	public static final int decodeAceFiveCardValue( Card c ){
		int cardValue = c.getCardValue();
		if( cardValue == 5 )
			return 1;
		else if( cardValue == 11 )
			return -1;
		else
			return 0;
	}
		
	/**
	 * Function to decide how much a player should bet according to the Ace-Five strategy rules.
	 * 
	 * @param acefivecount	Ace-Five strategy count
	 * @param minbet		Minimum bet we can bet.
	 * @param bet			Value of the last bet done by the player.
	 * @param maxbet		Maximum bet we can bet.
	 * @param cardsplayed	Number of cards played till the moment by the player.
	 * @return				Returns the correct command for the bet according to the Ace-Five rules.
	 */
	public String determineAceFiveStrategy (int acefivecount , int minbet , int bet , int maxbet , int cardsplayed ) {
		
		int newbet;
		
		if ( cardsplayed == 0)
			return "b";
		if ( acefivecount >= 2){
			newbet = 2*bet;
			if (newbet > maxbet)
				newbet = maxbet;
		}else {
			newbet = minbet;
		}
				
		return ("b "+newbet);
	}

	
	
	
}
