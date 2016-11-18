package blackjack;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Filipe Silvério
 * @author Miguel Laranjeira
 * 
 * Class used to open and decode the command and shoe files.
 * 
 */
public class ReadFile {

	/**
	 * Function to open a file from a specific directory.
	 * 
	 * @param directory		Takes the directory of the file.
	 * @return				Returns the scanner input(directory).
	 */
	public static Scanner openFile( String directory ){
		try{
			Scanner x = new Scanner( new File( directory ) );
			return x;
		}
		catch(Exception e){
			System.out.println("Could not find a file");
			System.exit(0);
		}
		return null;
	}
	
	/**
	 * Creates and empty deck to then fill it with the cards read on the file.
	 * 
	 * @param x		Takes the shoe file directory as parameter.
	 * @return		returns the playable deck filled with cards.
	 */
	public static Deck readShoeFile( Scanner x ){
		Deck d = new Deck();
			while( x.hasNext() ){
				String a = x.next();
				
				d.addCardToDeck( decodeCard( a ) );
			
		} 
		return d;
	}
	
	
	/**
	 * Reads the commands from the cmd file and store the values in a linked list.
	 * 
	 * @param x		Takes the shoe file directory as parameter.
	 * @return		Returns a linked list with the commands read from the cmd file.
	 */
	public static LinkedList<String> readCmdFile( Scanner x ){
		
		LinkedList<String> stringArray = new LinkedList<String>();
		String a;
		String b;
		
		while( x.hasNext() ){
			a = x.next();
			if  ( ( a.equals("b")) && x.hasNextInt() ) {
				b = x.next();
				a = a + " " + b;
			}
			stringArray.add( a );
		}
		
		return stringArray;
	}
	
	
	/**
	 * 	Function used to close the file.
	 * 
	 * @param x		Takes the file directory as a parameter.
	 */
	public static void closeFile( Scanner x ){
		x.close();
	}
	
	/**
	 * Function used to interpret a card from the shoe file and "create" it with the correct Rank and Suit Enums.
	 * 
	 * @param string	Takes the first string read on the shoe file.
	 * @return			Returns a playable card.
	 */
	public static Card decodeCard( String string ){
		Rank r = null;
		Suit s = null;
		String[] array;
		array = string.split("(?!^)");
		String Left;
		String Right;
		
		if( array.length == 3 ){
			Left = array[0] + array[1];
			Right = array[2];
		} else {
			Left = array[0];
			Right = array[1];
		}
		
		switch( Left ){
		case("2"):
			r = Rank.DEUCE;
			break;
		case("3"):
			r = Rank.THREE;
			break;
		case("4"):
			r = Rank.FOUR;
			break;
		case("5"):
			r = Rank.FIVE;
			break;
		case("6"):
			r = Rank.SIX;
			break;
		case("7"):
			r = Rank.SEVEN;
			break;
		case("8"):
			r = Rank.EIGHT;
			break;
		case("9"):
			r = Rank.NINE;
			break;
		case("10"):
			r = Rank.TEN;
			break;
		case("J"):
			r = Rank.JACK;
			break;
		case("Q"):
			r = Rank.QUEEN;
			break;
		case("K"):
			r = Rank.KING;
			break;
		case("A"):
			r = Rank.ACE;
			break;
		}
		switch( Right ){
		case("S"):
			s = Suit.SPADES;
			break;
		case("D"):
			s = Suit.DIAMONDS;
			break;
		case("H"):
			s = Suit.HEARTS;
			break;
		case("C"):
			s = Suit.CLUBS;
			break;
		}
			
		return new Card( r, s );

	}
	
	
}
