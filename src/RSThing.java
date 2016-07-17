import java.io.IOException;
import java.util.Scanner;

public class RSThing
{
	
	public static void main(String[] args) throws IOException, InterruptedException{ 
		//Ask for frquency of players 
		Scanner in = new Scanner(System.in); 
		System.out.println("Player frequency ammount?");
        int frequency = in.nextInt();	
        
        //start the lobby
		new Lobby(frequency);
	
	}
	
}
 