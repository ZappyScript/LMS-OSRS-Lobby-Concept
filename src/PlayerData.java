import java.util.Random;

public class PlayerData {
	
	Lobby L;
	public PlayerData(){
		/*this would be where all player based object would be.
		 * such as a full array of all player data, I like arrays. I'll use that as my example.
		 * 
		 */
		
	}
	
	//Here's my array of player data.
	public String[][] PlayerData = null;
	
	/*
	 * The LastId and generatePlayer are functions to 
	 * create more players for testing
	 * Since I don't have access to game code,
	 * and because I hate writing out data, we will use this 
	 * to generate random players coming into the lobby.
	 */
	private int lastId = 0;
	
	/*
	 * Function : generatePlayer
	 * usage: input a number and it'll generate those players
	 * Not going to really commment on this as it's self explanitory
	 */
	public  String[][] generatePlayer(int ammount){
		String[][] players  =new String[ammount][5];
		
		if(ammount > 0){
			
			for (int i=0; i<ammount; i++){
				String id = ""+lastId++;
				String name = "BanEmily"+id;
				String mode=null;
				String memberType=null;
				String world=null;
				Random rand = new Random(); 
				int dice = rand.nextInt(4);
				switch(dice){
				case 0:
					mode = "Casual";
					memberType = "F2P";
					world = ""+getRandomWorld(0);
					break;
				case 1:
					mode = "Competitive";
					memberType = "F2P";
					world = ""+getRandomWorld(0);
					break;
				case 2:
					mode = "Casual";
					memberType = "Member";
					world = ""+getRandomWorld(1);
					break;
				case 3:
					mode = "Competitive";
					memberType = "Member";
					world = ""+getRandomWorld(1);
					break;
				}
				players[i][0] = id;
				players[i][1] = name;
				players[i][2] = mode;
				players[i][3] = world;
				players[i][4] = memberType;
			}
		} 
		return players;
	}
	
	//gets and sets the player list.
public void setPlayerData(int ammount){
	PlayerData = new String[ammount][4];
	PlayerData = generatePlayer(ammount);
	
	}


	/*
	 * Function: addPlayer
	 * Usage: addPlayer(Player data array)
	 * Purpose: Adds a player to the final list in the array
	 * 
	 */
	public void addPlayer(String[] pdata){
		String[][] pData = PlayerData;
		//Get the length
		int size = (pData == null)? 0:pData.length;
		PlayerData = new String[size+1][5];
		//Set the final item
		PlayerData[size] = pdata;
		//add the others
		for (int i=0; i <size; i++){
			PlayerData[i] = pData[i];
		}
	}
	
	/*
	 * Just to remove a player. So far it will only remove
	 * Players that have started a game
	 * It really should include stuff like players DCing/Leaving the game
	 * 
	 */
	public void removePlayer(String Pid){
		String[][] pData = new String[PlayerData.length-1][4]; //Build new array
		int c = 0;	//Coutner for new array
		if(Pid != null){
			for (int i =0; i < pData.length; i++){
				if(PlayerData[i][0]!= null){
					if(!PlayerData[i][0].equals(Pid)){ //Is it the name we want to remove from list?
						pData[c] = PlayerData[i]; //All items we dont want removed.
						c++;
					}
				}
			}
			//Set the new player data
			PlayerData = new String[pData.length][4];
			PlayerData = pData;
	
		}
	}
	
	//Worlds to use in the world hopper.
	private static final int[][] worlds = {
			
			//f2p worlds
			{
				301,108,316,326,335,381,382,383,384,385,293,294
			},
			
			//p2p worlds
			{
				302,303,304,305,306,307,309,310,311,312,313,314,318,319,320,322,327,
				328,329,330,333,334,336,338,341,342,343,344,346,349,350,351,352,353,
				354,357,358,359,360,361,362,365,366,367,368,369,370,373,375,377
				
			}
	};

	//Gets a random world. Sub 0= f2p, sub 1 = p2p
	public int getRandomWorld(int sub){
		Random rand = new Random(); 
		return worlds[sub][rand.nextInt(worlds[sub].length)];
	}

	
	
}
