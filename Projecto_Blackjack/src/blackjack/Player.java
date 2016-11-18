package blackjack;

import java.util.*;

public class Player {

		
	/**
	 * [Field] List of hands the player holds.
	 */
	private List<PHand> hands;
	
	/**
	 * [Field] Player's monetary balance.
	 */
	private float balance;
	
	/**
	 * [Field] Number of hands the player holds ( = 1 if no splits were done).
	 */
	private int numberofhands;

	/**
	 * [Field] Current bet done by the player.
	 */
	private int bet;
	
	//***********************************************************************************************
	
	/**
	 * Player constructor
	 * 
	 * @param value		initial balance for the Player
	 */
	public Player ( float balance){
		this.hands = new LinkedList<PHand>();
		this.balance = balance;
		this.numberofhands = 0;
		this.bet = 0;
	}
	
	//***********************************************************************************************
	
	/**
	 * Getter for balance.
	 * 
	 * @return	balance	-> value of balance.
	 */
	public float getBalance(){
		return this.balance;
	}
	
	/**
	 * Setter for balance
	 * 
	 * @param balance	-> value of balance
	 */
	public void setBalance( float balance ){
		this.balance = balance;		
	}
		
	/**
	 * Get the number of hands the player has atm.
	 * 
	 * @return	numberofhands	-> Number of hands ( ==1 if no split )
	 */
	public int getNumberOfHands(){
		return this.numberofhands;
	}
	
	/**
	 * Getter for bet.	
	 * @return	bet.
	 */
	public int getBet() {
		return bet;
	}

	/**
	 * Setter for bet.
	 * @param bet
	 */
	public void setBet(int bet) {
		this.bet = bet;
	}

	/**
	 * Creates a new Hand for the player.
	 */
	public void newHand () {
		
		PHand newhand = new PHand();
		
		this.hands.add ( newhand );
		this.numberofhands ++;
		
	}
	
	/**
	 * Returns the player's hand specified by the handindex.
	 * 
	 * @param handindex		-> index of hand.
	 * @return	specified player's hand.
	 */
	public PHand getHand ( int handindex ){
		return this.hands.get(handindex);		
	}
	
	/**
	 * Adds a Card to one of the player's hands.
	 * 
	 * @param c		-> card to add to the hand.
	 * @param handindex		-> index of the hand.
	 */
	public void addCardToHand ( Card c , int handindex ){
		
		PHand hand = this.getHand ( handindex);
		
		hand.addCard( c );
		
	}

	/**
	 *  Removes (clear) all cards from all the player's hands.
	 */
	public void clearHands (){
		 this.hands.clear();
		 this.numberofhands = 0;
	}	
	
	/**
	 * Splits the player's hand. Creates a new hand for the player and adds a card
	 * retrieved from the hand the player wishes to split.
	 * 
	 * @param handindex		which hand the player is splitting
	 */
	public void SplitHand ( int handindex ) {
		Card card;
		PHand hand;
		PHand newhand;
		
		hand = getHand (handindex);
		card = hand.retrieveCard();
		hand.setSplit(true);
		
		newhand = new PHand();
		newhand.addCard( card );
		newhand.setSplit(true);
		
		hands.add ( newhand );
		numberofhands ++;
		
		balance = balance - bet;
	}
	
}
