package blackjack;

import java.util.LinkedList;

public class Hand {

	//***********************************************************************************************
	
	/**
	 * [Attribute] List of Cards in one Hand
	 */
	private LinkedList<Card> cards;

	/**
	 * [Attribute] Value of the sum of the cards in the hand
	 */
	private int handvalue;
	
	/**
	 * [Attribute] Number of cards on the hand.
	 */
	private int numberofcards;
	
	/**
	 * [Attribute] Number of Aces on the hand.
	 */
	private int numberofaces;
	
	/**
	 * 
	 */
	private int acesworth11;
		
	//***********************************************************************************************
	
	/**
	 *  Constructor for this Class
	 */
	public Hand (){
		this.cards = new LinkedList<Card>();
		this.handvalue = 0;
		this.numberofcards = 0;
		this.numberofaces = 0;
		this.acesworth11 = 0;
	}
	
	//***********************************************************************************************
	
	/**
	 *  Add a Card to the hand
	 *  
	 *  @param c	Card added to the hand
	 */
	public void addCard ( Card c){
		int cardvalue = c.getCardValue();
		
		if (cardvalue == 11)
			this.numberofaces ++;
		this.cards.add(c);
		this.numberofcards ++;
		this.handvalue = this.calculateHandValue();		
	}
	
	/**
	 * Retrieve a Card from the hand
	 * 
	 * @return	the last card from the hand.
	 */
	public Card retrieveCard () {
		Card card;
		card = this.cards.removeLast();
		
		if (card.getCardValue() == 11)
			this.numberofaces --;
		
		this.numberofcards --;
		this.handvalue = this.calculateHandValue();
		
		return card;		
	}	

	/**
	 * Get a Card at a specific position from the hand.
	 * 
	 * @param i	->	index of card
	 * @return	card at position [i] of the Hand.
	 */
	public Card getCard ( int i) {		
		return this.cards.get(i);		
	}
	

	/**
	 *  Removes (clear) all cards from the hand
	 */
	public void clearHand (){
		 this.cards.clear();
		 this.numberofcards = 0;
		 this.handvalue = 0;
		 this.numberofaces = 0;
		 this.acesworth11 = 0;
	}

	/**
	 * Setter for numberofcards
	 * 
	 * @return numberofcards	-> quantity of cards in the hand
	 */
	public int getNumberOfCards(){
		return this.numberofcards;
	}
	
	/**
	 * Getter of handvalue.
	 * 
	 * @return handvalue	-> value of the hand
	 */
	public int getHandValue(){
		return this.handvalue;
	}
	 
	/**
	 * Getter of numberofaces.
	 * 
	 * @return Number of aces on the hand.
	 */
	public int getNumberofaces(){
		return this.numberofaces;
	}

	public int getAcesworth11(){
		return this.acesworth11;
	}
	
	/**
	 * List of Cards in the hand in string format
	 * 
	 * @return listofcards	-> String representing the cards on the hand.
	 */
	public String listCards(){
		 String listofcards = "";
		 for ( int i = 0 ; i< numberofcards; i++)
		 {
			 listofcards += cards.get(i)+" " ;
		 }
		 return listofcards;
	}
	
	/**
	 * Function that determines if Hand is a BlackJack Hand
	 * 
	 * @return	true if Hand is a Blackjack hand.
	 * 			false otherwise.
	 */
	public boolean hasBlackJack() {
		if ( this.getHandValue() == 21 && this.getNumberOfCards() == 2)
			return true;
		else
			return false;
	}
	
	/**
	 * Calculates the value of the card in hand taking into account
	 * that ACES are worth 11 or 1.
	 * 
	 * @return handsum		Value of the cards in the hand
	 */
	public int calculateHandValue() {
		int handsum = 0;
		Card card;
		int cardvalue;
		int numaces = 0;
		
		this.acesworth11 = this.numberofaces;
		
		// Calculate each card's contribution to the hand sum
		for ( int i = 0 ; i< numberofcards ; i++)
		 {
			 // get the value of the current card
			card = cards.get(i);
			cardvalue = card.getCardValue();
			
			if ( cardvalue == 11 ) {	// Ace
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
			this.acesworth11 --;
		}
	

	return handsum;	
	}

	
	
}
