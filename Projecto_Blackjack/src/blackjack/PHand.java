package blackjack;

public class PHand extends Hand {
		
	private boolean insurance;
	
	private boolean surrender;
	
	private boolean doublee;
	
	private boolean split;
	
	//***********************************************************************************************
	
	public PHand(){
		super();
		this.insurance = false;
		this.surrender = false;
		this.doublee = false;
		this.split = false;
	}

	//***********************************************************************************************
	
	public boolean canSplit() {
		Card card1;
		Card card2;
		
		if ( this.getNumberOfCards() == 2) {	// Check if hand has at least and only 2 cards.
			card1 = this.getCard(0);
			card2 = this.getCard(1);
			if ( card1.getCardValue() == card2.getCardValue() )
				return true;
			else
				return false;
		}
		else
			return false;		
	}

	public boolean getInsurance() {
		return insurance;
	}

	public void setInsurance(boolean insurance) {
		this.insurance = insurance;
	}

	public boolean getSurrender() {
		return surrender;
	}

	public void setSurrender(boolean surrender) {
		this.surrender = surrender;
	}

	public boolean getDoublee() {
		return doublee;
	}

	public void setDoublee(boolean doublee) {
		this.doublee = doublee;
	}	
	
	public boolean getSplit() {
		return split;
	}

	public void setSplit(boolean split) {
		this.split = split;
	}

	/**
	 *  Removes (clear) all cards from the hand
	 */
	public void clearHand (){
		 this.clearHand();
		 this.insurance = false;
		 this.surrender = false;
		 this.doublee = false;
	}

	
}
