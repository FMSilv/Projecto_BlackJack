package blackjack;

import java.util.*;

public class GameDebug extends GameBody{
	
	/**
	 * Input parameter - file that has a description of the shoe
	 * (list of cards in a predefined format)
	 */
	private String shoefile;
	
	/**
	 * Input parameter - file that has a description of the commands
	 * (list of commands to be taken in order by the player)
	 */
	private String cmdfile;	
	
	/**
	 * List of commands to be taken by the player during the games.
	 * The list is decoded from the a file with the name given by
	 * the field shoefile.	
	 */
	private LinkedList<String> commandslist;
	
	/**
	 * Iterator used to go through the List of commands.
	 */
	private Iterator<String> commandsiterator;
	
	//***********************************************************************************************
	
	/**
	 * GameDebug Constructor
	 * @param arguments	command line arguments
	 */
	public GameDebug ( Arguments arguments){
		super( arguments );
		this.shoefile = arguments.getShoeFile();
		this.cmdfile = arguments.getCmdFile();
		this.commandslist = null;
		this.commandsiterator = null;

		this.statistics = new Statistics( arguments.getBalance() , 2 );
	}

	//***********************************************************************************************

	/**
	 * Method to determine the command to be executed by the player.
	 * In this subclass the action to be performed by the player is determined
	 * by the commands retrieved form a file and saved on the commandlist field.
	 * 
	 * @return
	 */
	public String determineCommand ( ){
		String command = "q";
		
		System.out.println("");
		
		if (commandsiterator.hasNext() == true) {
			command = commandsiterator.next();	
			System.out.println(command);		
		}
		else
			commandsiterator = null;	

		return command;
	}	
	
	/**
	 * Plays multiple games of BlackJack until there are no more commands to be
	 * executed by the player.
	 * (commands are retrieved from a file)
	 * 
	 * The sequence of commands to be performed by the player will temporarily be
	 * saved in the field "comm". Eventually all the commands will be executed and
	 * the end of the list of commands will be reached. Once that happens the field
	 * comm will have "null" in it therefore marking that there are no more commands
	 * to be performed. During the playGames() cycle of playOneGame() if it is detected
	 * null on the field comm we flag the game as over (gameover = true) and stop the
	 * cycle of playOneGame().
	 * 
	 * The update to the field command is done in another overriden function - 
	 * determinCommand().
	 */
	public void playGames(){
		
		Scanner scan;
		int round = 0;
		
		// Open shoefile and save its data on the deck class (shoe).
		scan = ReadFile.openFile(this.shoefile);
		this.shoe = ReadFile.readShoeFile(scan);
		ReadFile.closeFile(scan);
		
		// Open cmdfile and save its data to a LinkedList<String>.
		scan = ReadFile.openFile(this.cmdfile);
		commandslist = ReadFile.readCmdFile(scan);
		ReadFile.closeFile(scan);
		
		// Set the commandsiterator to the beggining of the commands list.
		commandsiterator = commandslist.iterator();
		
		this.gameover = false;
				
		while (  gameover != true ) {
			round++;
			
			// Play one game of blackjack.
			this.playOneGame();
			
			// Check if there are no more commands.
			if ( commandsiterator == null) 
				gameover = true;	
			
			
		}

		
		
	}
	
}
