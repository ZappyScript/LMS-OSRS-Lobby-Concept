

public class RSThing
{
	PlayerData pd;
	Lobby l;
	public static void main(){
		//This is where you feed in the player data, we are randomly creating
		//players inside of it.
		pd = new PlayerData();
		
		/*
		 * Now players have been loaded we can start the lobby
		 * Lobby is split into 4 groups, f2pcasual, f2pcomp, p2pcasual,p2pcomp
		 * We will look at every player, assign them a group
		 * Then check the groups for 25 players. If 25 players have been found
		 * we hop our players to the same world, and put them in the game.
		 * 
		 */
		l = new Lobby();
		String output = null;
		System.console().readLine(null, output);
		
		
	}
	
}
