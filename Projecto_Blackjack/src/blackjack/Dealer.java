package blackjack;


/**
 * This class represents a person, more specifically a dealer for the game of Blackjack.
 * A dealer will hold a Hand (of visible cards) and a holecard(hidden card).
 * 
 * @author Filipe Silvério
 * @author Miguel Laranjeira
 *
 */
public class Dealer {

	/**
	 * [Field] Dealer's hand.
	 */
	private Hand hand;
	
	/**
	 * [Field] Dealer's holecard.
	 */
	private Card holecard;
	
	//***********************************************************************************************
	
	/**
	 * Dealer constructor
	 */
	public Dealer (){
		this.hand = new Hand();
		this.holecard = null;
	}	
	
	//***********************************************************************************************
	
	/**
	 * Obtain an holecard(hidden card) for the dealer.
	 * 
	 * @param c card to save as holecard.
	 */
	public void addHolecard ( Card c){
		this.holecard = c;
	}
	
	/**
	 * Turn the hidden card (making it visible) by 
	 * adding it to the dealer's hand.
	 */
	public Card turnHoleCard(){
		Card card = this.holecard;
		this.addCardToHand ( card );
		this.holecard = null;
		
		return card;
	}
	
	/**
	 * Adds a Card to the dealer's hand.
	 * 
	 * @param c	card to add to the hand.
	 */
	public void addCardToHand ( Card c ){
		this.hand.addCard( c );		
	}
	
	/**
	 * Getter for the dealer's hand.
	 * 
	 * @return	dealer's hand.
	 */
	public Hand getHand ( ){
		return this.hand;		
	}
	
	/**
	 *  Removes (clear) all cards from the dealer's hand.
	 */
	public void clearHand (){
		 this.hand.clearHand();
	}	
	
	

}
