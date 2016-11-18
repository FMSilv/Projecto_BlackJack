package blackjack;

import java.util.LinkedList;
import java.util.List;


public abstract class Person {

	/**
	 * [Attribute] List of the person's cards
	 */
	private List<Card> hand;
	
	/**
	 * [Attribute] Value of the sum of the cards the person holds
	 */
	private int handvalue;
	
	/**
	 * [Attribute] Number of cards the person holds on his hand.
	 */
	private int numberofcards;
	
	
	/**
	 *  Constructor for this Class
	 */
	public Person (){
		this.hand = new LinkedList<Card>();
		this.handvalue = 0;
		this.numberofcards = 0;
	}
	
	
	/**
	 *  Add a Card to Person's hand
	 *  
	 *  @param c	Card added to Person's hand.
	 */
	public void addCard ( Card c){
		this.hand.add(c);
		this.numberofcards ++;
		this.handvalue = this.calculateHandValue();
	}
	
	/**
	 *  Removes (clear) all cards from Person's hand
	 */
	public void clearHands (){
		 this.hand.clear();
		 this.numberofcards = 0;
		 this.handvalue = 0;
	}	
	
	/**
	 * Returns the number of cards the person holds on his hand.
	 * 
	 * @return numberofcards	-> quantity of cards in person's hand.
	 */
	public int getNumberOfCards(){
		return this.numberofcards;
	}
	
	/**
	 * Returns the person's hand value.
	 * 
	 * @return handvalue	-> person's hand value.
	 */
	public int getHandValue(){
		return this.handvalue;
	}
	 
	/**
	 * List of Person's Cards in string format
	 * 
	 * @return listofcards	-> String representing the cards on the person's hand.
	 */
	public String listCards(){
		 String listofcards = "";
		 for ( int i = 0 ; i< hand.size() ; i++)
		 {
			 listofcards += hand.get(i) ;
		 }
		 return listofcards;
	}
	
	/**
	 * Calculates the value of the card in hand taking into account
	 * that ACES are worth 11 or 1.
	 * 
	 * @return handsum		Value of the cards this person holds atm.
	 */
	public int calculateHandValue() {
		int handsum = 0;
		Card card;
		int cardvalue;
		int numaces = 0;
		
		// Calculate each card's contribution to the hand sum
		for ( int i = 0 ; i< hand.size() ; i++)
		 {
			 // get the value of the current card
			card = hand.get(i);
			cardvalue = card.getCardValue();
			
			if ( cardvalue == 1 ) {	// Ace
				numaces++;
				handsum += 11;
			} else if ( cardvalue == 10) { // Face Card
				handsum +=10;
			} else {	// Number Cards
				handsum += cardvalue;
			}
		 }
			
		// if we have aces and our sum is > 21, set some/all of them
		// to value 1 instead
		while ( handsum > 21 && numaces > 0 ) {
			handsum -= 10;
			numaces --;
		}

	return handsum;	
	}

	
	
}
