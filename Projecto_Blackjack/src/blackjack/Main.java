package blackjack;

public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String gamemode;
		GameBody gb = null;
		Arguments arguments = new Arguments( args );
		
		gamemode = arguments.getMode();
		
		switch ( gamemode ){
		case ("-i"):
			gb = new GameInteractive(arguments);
			break;
		case ("-d"):
			gb = new GameDebug(arguments);
			break;
		case ("-s"):
			gb = new GameSimulation(arguments);
			break;		
		case ("-g"):
			//gb = new SwingBody();
			break;
		default:
			gb = new GameInteractive(arguments);
		}
		
		gb.playGames();
		
	}

}
