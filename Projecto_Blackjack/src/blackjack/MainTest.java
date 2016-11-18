package blackjack;

import java.util.LinkedList;
import java.util.Scanner;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		LinkedList<String> list;
		String str;

		Arguments arguments = new Arguments( args );
				
		Scanner scan = ReadFile.openFile( arguments.getCmdFile() );

		list = ReadFile.readCmdFile( scan );
		
		str = (String) list.getFirst();
		System.out.println("string = "+str);
		
		
	}

}
