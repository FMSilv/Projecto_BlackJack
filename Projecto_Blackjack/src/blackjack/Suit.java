package blackjack;

/**
 * An enum representing the four possible suits of a card.
 */
public enum Suit {
	
	CLUBS("C") , DIAMONDS("D") , HEARTS("H") , SPADES("S");
	
	
	private String stringSuit;
	
	Suit ( String stringSuit){
		this.stringSuit = stringSuit;
	}

	public String getValueString(){
		return stringSuit;
	}
	
};