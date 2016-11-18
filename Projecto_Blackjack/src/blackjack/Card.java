package blackjack;

/**
 * This Class represents a Card.
 * A card is represented by a rank and a suit.
 * 
 * @author Filipe Silvério
 * @author Miguel Laranjeira
 *
 */
public class Card {

	/**
	 * Ranks of the Card
	 */
	private final Rank rank;
	/**
	 * One of the four valid suits for this card.
	 */	
	private final Suit suit;

	//***********************************************************************************************
	
	/**
	 * Card constructor
	 * 
	 * @param r		rank of the card
	 * @param s		suit of the card
	 */
	
	public Card ( Rank r , Suit s) {
		this.rank = r;
		this.suit = s;
	}
	
	//***********************************************************************************************
	
	/**
	 * Getter for rank.
	 * 
	 * @return	rank of the card.
	 */
	public Rank getCardRank(){
		return this.rank;
	}

	/**
	 * Getter for the value of the card.
	 * 
	 * @return	value of the card (1-11).
	 */
	public int getCardValue(){
		return this.rank.getRankValue();
	}	
	
	/**
	 * Getter for the suit of the card.
	 * 
	 * @return	suit of the card.
	 */
	public Suit getCardSuit(){
		return this.suit;
	}	
		
	@Override
	/**
	 * Override of hash code for card.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	/**
	 * Override of equals to make it possible to compare cards.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}

	@Override
	/**
	 * Override the toString() method.
	 */
	public String toString() {
		
		if (rank == Rank.ACE)
			return ""+"A"+""+suit.getValueString()+"";
		else
		return ""+rank.getRankValue()+""+suit.getValueString()+"";
		
		//return "[" + rank + " of " + suit + "]";
	}
	
	
	
}
