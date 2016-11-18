package blackjack;
import java.util.*;

/**
 * Representation of a Deck of cards, more specifically, a shoe. ( several 52-card decks tied
 * together). The class is characterized by a simple list of cards, the initial size of the shoe
 * (how many decks it has) and how many cards it has at a given moment.
 * 
 * @author Filipe Silvério
 * @author Miguel Laranjeira
 *
 */
public class Deck {

	/**
	 * List of cards in this deck.
	 */
	private LinkedList<Card> cards;
	
	/**
	 * Initial size of the Shoe -> Number of complete decks.
	 */
	private int shoesize;

	/**
	 * Remaining quantity of cards on the shoe.
	 */
	private int numberofcards;
	
	/**
	 * Size of a Deck -> Number of cards on one deck.
	 */
	public static final int DECKSIZE = Rank.values().length * Suit.values().length;
	
	
	//***********************************************************************************************
	
	/**
	 * Create an empty deck/shoe.
	 */
	public Deck(){
		this.cards = new LinkedList<Card>();
	}
	
	/**
	 * Create a a shoe with a specific quantity of complete decks.
	 * 
	 * @param ss number of complete decks.
	 */
	public Deck( int ss) {
		this.shoesize = ss;
		this.cards = new LinkedList<Card>( );
		for ( int i = 0 ; i < ss ; i++ )
			for ( Suit s : Suit.values() )
				for ( Rank r : Rank.values() )
					this.addCardToDeck ( new Card(r, s) );		
	}
	
	//***********************************************************************************************

	/**
	 * Getter for numberofcards
	 * 
	 * @return	remaining number of cards on the shoe.
	 */
	public final int getNumberofcards(){
		return this.numberofcards;
	}
	
	/**
	 * Getter for shoesize
	 * 
	 * @return	initial number of decks on this shoe.
	 */
	public final int getShoeSize(){
		return this.shoesize;
	}

	//***********************************************************************************************
	
	/**
	 * Add a Card to the Deck (at the end/top)
	 * 
	 * @param card	Card to be added.
	 */
	public final void addCardToDeck( Card card){
		this.cards.add(card);		
		this.numberofcards ++;
	}
	
	/**
	 * Remove and return a Card (at the end/top)
	 * 
	 * @return	card removed
	 */
	public final Card retrieveCard(){
		this.numberofcards --;
		return cards.remove();
	}

	/**
	 * Shuffle the cards in this deck.
	 */
	public final void shuffleCards(){
		System.out.println("Shuffling the shoe...");
		Collections.shuffle(cards);
	}

	/**
	 * Method used to directly print the list of cards on this shoe to the
	 * standard output.
	 */
 	public void printCards (){
		System.out.println("\n");
		for ( int k = 0 ; k<cards.size() ; k++)
			System.out.println ( cards.get(k));			
	}
	
}
