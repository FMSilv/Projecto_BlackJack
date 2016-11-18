package blackjack;

/**
 * 
 * This class is used to decode the commands from the terminal and store them in
 * class fields. The commands are also checked for errors.
 * 
 * 	@author Filipe Silvério
 *	@author Miguel Laranjeira
 */
public class Arguments {

	/**
	 * Variable to store the mode that we are playing
	 */
	private String mode;
	/**
	 * Variable that store the min bet
	 */
	private int minBet;
	/**
	 * Variable that stores the max bet.
	 */
	private int maxBet;
	/**
	 * Variable that stores the balance.
	 */
	private int balance;
	/**
	 * Variable to store the shoe size.
	 */
	private int shoe;
	/**
	 * Stores the percentage of shoe played before shuffling.
	 */
	private int shuffle;
	/**
	 * name of the file with the shoe.
	 */
	private String shoeFile;
	/**
	 * name of the file with the commands.
	 */
	private String cmdFile;
	/**
	 * number of shuffles to perform until ending the simulation.
	 */
	private int sNumber;
	/**
	 * counting cards strategy to use.
	 */
	private String strategy;
	
	/**
	 * Stores the input arguments when running the project.
	 */
	public String[] args;
	
	/**
	 * Saves the input arguments and runs the function decodeArgs.
	 * 
	 * @param args	Arguments stored from the running program.
	 */
	public Arguments( String[] args ) {
		this.args = args;
		decodeArgs();
	}
	
	/**
	 * Identifies the mode of the running program and stores the respective arguments.
	 */
	public void decodeArgs(){
		if( this.args.length < 1 ){
			System.out.println("ERROR! Too few arguments. Ending program...");
			System.exit(0);
		}
		switch(args[0]){
		/*
		 * Interactive mode
		 */
		case("-i"):
			if( this.args.length != 6 ){
				System.out.println("ERROR! Incorrect number of arguments. Needs 6 arguments for interactive mode. Ending program...");
				System.exit(0);
			}
			this.mode = args[0];
			this.minBet = Integer.parseInt(this.args[1]);
			this.maxBet = Integer.parseInt(this.args[2]);
			this.balance = Integer.parseInt(this.args[3]);
			this.shoe = Integer.parseInt(this.args[4]);
			this.shuffle = Integer.parseInt(this.args[5]);
			checkForErrors("-i");
			break;
		/*
		 * Debug mode
		 */
		case("-d"):
			if( this.args.length != 6 ){
				System.out.println("ERROR! Incorrect number of arguments. Needs 6 arguments for debug mode. Ending program...");
				System.exit(0);
			}
			this.mode = args[0];
			this.minBet = Integer.parseInt(this.args[1]);
			this.maxBet = Integer.parseInt(this.args[2]);
			this.balance = Integer.parseInt(this.args[3]);
			this.shoeFile = this.args[4];
			this.cmdFile = this.args[5];
			checkForErrors("-d");
			break;
		/*
		 * Simulation mode
		 */
		case("-s"):
			if( this.args.length != 8 ){
				System.out.println("ERROR! Incorrect number of arguments. Needs 8 arguments for simulation mode. Ending program...");
				System.exit(0);
			}
			this.mode = args[0];
			this.minBet = Integer.parseInt(this.args[1]);
			this.maxBet = Integer.parseInt(this.args[2]);
			this.balance = Integer.parseInt(this.args[3]);
			this.shoe = Integer.parseInt(this.args[4]);
			this.shuffle = Integer.parseInt(this.args[5]);
			this.sNumber = Integer.parseInt(args[6]);
			this.strategy = this.args[7];
			checkForErrors("-s");
			break;
		/*
		 * Swing mode
		 */
		case("-g"):
			this.mode = args[0];
			break;
		default:
			System.out.println("Invalid arguments!");
			break;
		}
		
	}
	
	/**
	 * Getter for mode.
	 * 
	 * @return	returns the mode from the input arguments.
	 */
	public String getMode(){
		return this.mode;
	}
	
	/**
	 * Getter for min bet.
	 * 
	 * @return	returns the min bet from the input arguments.
	 */
	public int getMinBet(){
		return this.minBet;
	}
	/**
	 * Getter for max bet.
	 * 
	 * @return	returns the max bet from the input arguments.
	 */
	public int getMaxBet(){
		return this.maxBet;
	}
	/**
	 * Getter for balance.
	 * 
	 * @return	returns the balance from the input arguments.
	 */
	public int getBalance(){
		return this.balance;
	}
	/**
	 * Getter for number of shoes.
	 * 
	 * @return	returns the number of shoes from the input arguments.
	 */
	public int getShoe(){
		return this.shoe;
	}
	/**
	 * Getter for percentage of shoe played before shuffling
	 * 
	 * @return	returns the percentage of shoe played before shuffling from the input arguments.
	 */
	public int getShuffle(){
		return this.shuffle;
	}
	/**
	 * Getter for name of the file with the shoe.
	 * 
	 * @return	returns the name of the file with the shoe from the input arguments.
	 */
	public String getShoeFile(){
		return this.shoeFile;
	}
	/**
	 * Getter for the name of the file with the commands
	 * 
	 * @return	returns the name of the file with the commands from the input arguments.
	 */
	public String getCmdFile(){
		return this.cmdFile;
	}
	/**
	 * Getter for the number of shuffles to perform until the end of the simulation.
	 * 
	 * @return	returns the number of shuffles to perform until the end of the simulation from the input arguments.
	 */
	public int getSNumber(){
		return this.sNumber;
	}
	/**
	 * Getter for the counting cards strategy to use.
	 * 
	 * @return	returns the counting cards strategy to use from the input arguments.
	 */
	public String getStrategy(){
		return this.strategy;
	}
	
	/**
	 * Checks if the parameters from the mode running are correct.
	 * 
	 * @param mode	receives the mode as an argument.
	 */
	public void checkForErrors( String mode ){
		switch(mode){
		case("-i"):
			if( this.minBet < 1 ){
				System.out.println("Minimum bet must be >= 1");
				System.exit(0);
			}
			if( this.maxBet < 10*this.minBet || this.maxBet > 20*this.minBet ){
				System.out.println("Maximum bet must have a value between " + 10*this.minBet + " and " + 20*this.minBet);
				System.exit(0);
			}
			if( balance < 50*this.minBet ){
				System.out.println("Balance must have a value superior or equal to: " + 50*this.minBet );
				System.exit(0);
			}
			if( this.shoe < 4 || this.shoe > 8 ){
				System.out.println("Shoe size must be between 4 and 8");
				System.exit(0);
			}
			if( this.shuffle < 10 || this.shuffle > 100 ){
				System.out.println("Shuffle percentage must have a value between 10% and 100%");
				System.exit(0);
			}
			break;
		case("-d"):
			if( this.minBet < 1 ){
				System.out.println("Minimum bet must be >= 1");
				System.exit(0);
			}
			if( this.maxBet < 10*this.minBet || this.maxBet > 20*this.minBet ){
				System.out.println("Maximum bet must have a value between " + 10*this.minBet + " and " + 20*this.minBet);
				System.exit(0);
			}
			if( balance < 50*this.minBet ){
				System.out.println("Balance must have a value superior or equal to: " + 50*this.minBet );
				System.exit(0);
			}
			break;
		case("-s"):
			if( this.minBet < 1 ){
				System.out.println("Minimum bet must be >= 1");
				System.exit(0);
			}
			if( this.maxBet < 10*this.minBet || this.maxBet > 20*this.minBet ){
				System.out.println("Maximum bet must have a value between " + 10*this.minBet + " and " + 20*this.minBet);
				System.exit(0);
			}
			if( balance < 50*this.minBet ){
				System.out.println("Balance must have a value superior or equal to: " + 50*this.minBet );
				System.exit(0);
			}
			if( this.shoe < 4 || this.shoe > 8 ){
				System.out.println("Shoe size must be between 4 and 8");
				System.exit(0);
			}
			if( this.shuffle < 10 || this.shuffle > 100 ){
				System.out.println("Shuffle percentage must have a value between 10% and 100%");
				System.exit(0);
			}
			if( this.strategy.equals("BS")==false && this.strategy.equals("BS-AF")==false && this.strategy.equals("HL")==false &&this.strategy.equals("HL-AF")==false ){
				System.out.println("Wrong strategy combinations. Type BS, HL or AF");
				System.exit(0);
			}
			break;
		}
	}
	
	

	
	
}
