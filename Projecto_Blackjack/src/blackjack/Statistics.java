package blackjack;

import blackjack.GameBody.Result;

public class Statistics {

	private int playerBJs;
	private int dealerBJs;
	private int playerHands;	
	private int wins;
	private int losses;
	private int pushes;
	
	private int gamesPlayed;
	private int dealerHands;
	
	private int inicialBalance;
	
	private int shoesize;
	private final int decksize = Deck.DECKSIZE;
	private int cardsplayed;
	
	//*************************************//
	
	private int runningCount;
	private float trueCount;
	private int acefiveCount;
	
	
	//******************************************************************************************
	
	public Statistics( int inicialBalance, int shoesize ) {
		this.playerBJs = 0;
		this.dealerBJs = 0;
		this.playerHands = 0;
		this.dealerHands = 0;
		this.wins = 0;
		this.losses = 0;
		this.pushes = 0;
		this.gamesPlayed = 0;
		this.inicialBalance = inicialBalance;
		this.shoesize = shoesize;
		this.cardsplayed = 0;		
	}

	//******************************************************************************************
	
	
	public int getPlayerBJs() {
		return playerBJs;
	}

	public int getDealerBJs() {
		return dealerBJs;
	}

	public int getPlayerHands() {
		return playerHands;
	}

	public int getDealerHands() {
		return dealerHands;
	}

	public int getWins() {
		return wins;
	}

	public int getLosses() {
		return losses;
	}

	public int getPushes() {
		return pushes;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getInicialBalance() {
		return inicialBalance;
	}

	public int getRunningCount() {
		return runningCount;
	}

	public float getTrueCount() {
		return trueCount;
	}

	public int getAcefiveCount() {
		return acefiveCount;
	}
	
	public int getCardsPlayed() {
		return cardsplayed;
	}
	
	//******************************************************************************************

	public void setShoesize( int shoesize ){
		this.shoesize = shoesize;
	}
	
	public void incrementPlayerBJs() {
		this.playerBJs++;
	}

	public void incrementDealerBJs() {
		this.dealerBJs++;
	}

	public void incrementPlayerHands() {
		this.playerHands++;
	}

	public void incrementDealerHands() {
		this.dealerHands++;
	}

	public void incrementWins(int wins) {
		this.wins++;
	}

	public void incrementLosses(int losses) {
		this.losses++;
	}

	public void incrementPushes() {
		this.pushes++;
	}

	public void incrementGamesPlayed() {
		this.gamesPlayed++;
	}

	public void addsubRunningCount(int addsub, int decksremaning ) {
		this.runningCount += addsub;
		setTrueCount( decksremaning );
	}

	public void setTrueCount( int decksRemaning ) {
		this.trueCount = (float)(this.runningCount)/(float)(decksRemaning);
	}

	public void addsubAcefiveCount(int addsub) {
		this.acefiveCount += addsub;
	}
	
	//******************************************************************************************
		
	public void updateRunningCount( Card c ){
		int cardsremaning = ((this.shoesize*this.decksize)-this.cardsplayed);
		int decksremaning = (int) Math.ceil((float)(cardsremaning)/(float)(this.decksize));
		int addsub;
		addsub = Strategies.decodeHiLowCardValue(c);
		this.addsubRunningCount(addsub, decksremaning);
	}
	
	public void updateacefivesCount( Card c ){
		int addsub;
		addsub = Strategies.decodeAceFiveCardValue(c);
		this.addsubAcefiveCount(addsub);
	}
	
	public void updateCardCounting( Card c ){
		this.cardsplayed++;
		updateRunningCount( c );
		updateacefivesCount( c );
	}
	
	public void resetCardCounting(){
		this.runningCount = 0;
		this.trueCount = 0;
		this.acefiveCount = 0;
		this.cardsplayed = 0;
	}
		
	
	public void updateHandStatistics ( Result result) {
		
		switch( result ){
		case LOSEBYBJ:
			this.dealerBJs ++;
			this.losses ++;
			break;
		case LOSE:
			this.losses ++;
			break;
		case TIEBYBJ:
			this.dealerBJs ++;
			this.playerBJs ++;
			this.pushes ++;
			break;
		case TIE:
			this.pushes ++;
			break;
		case WIN:
			this.wins ++;
			break;
		case WINBYBJ:
			this.playerBJs ++;
			this.wins ++;
			break;
		}
			
		this.playerHands ++;		
		
	}
	
	public void updateGameStatistics () {
		this.dealerHands++;
		this.gamesPlayed++;		
	}

	public void printCardCounting(){
		System.out.println("RunningCount ="+this.runningCount);
		System.out.println("TrueCount ="+this.trueCount);
		System.out.println("AceFiveCount="+this.acefiveCount);
		System.out.println("Number of Cards played so far ="+this.cardsplayed);
	}
	
}
