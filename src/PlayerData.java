import java.util.Random;

public class PlayerData {
	
	
	
	
	//Here's my array of player data.
	public String[][] PlayerData = null;
	
	/*
	 * The LastId and generatePlayer are functions to 
	 * create more players for testing
	 * Since I don't have access to game code,
	 * and because I hate writing out data, we will use this 
	 * to generate random players.
	 */
	private int lastId = 0;
	private String[][] generatePlayer(int ammount){
		String[][] players  =new String[ammount][5];
		
		if(ammount > 0){
			
			for (int i=0; i<=ammount; i++){
				String id = ""+lastId++;
				lastId ++;
				String name = "Player"+id;
				String mode=null;
				String memberType=null;
				String world=null;
				Random rand = new Random(); 
				int dice = rand.nextInt(3);
				
				
				switch(dice){
				case 0:
					mode = "Casual";
					memberType = "F2P";
					world = "301";
					break;
				case 1:
					mode = "Competitive";
					memberType = "F2P";
					world = "302";
				case 2:
					mode = "Casual";
					memberType = "Member";
					world = "321";
					break;
				case 3:
					mode = "Competitive";
					memberType = "Member";
					world = "321";
					break;
				
				}
				
				players[i][0] = id;
				players[i][1] = name;
				players[i][2] = mode;
				players[i][3] = world;
				players[i][4] = memberType;
			}
			
			return players;
			
		}
		
		return null;
		
	}
	
	//gets and sets the play list.
public void setPlayerData(int ammount){
	PlayerData = new String[ammount][4];
	PlayerData = generatePlayer(ammount);
	}

	
	public PlayerData(){
		/*this would be where all player based object would be.
		 * such as a full array of all player data, I like arrays. I'll use that as my example.
		 * 
		 */
		
		//First We need to add our player to the list..
		System.out.println("Adding 100 random accounts now");
		setPlayerData(100);
		
	}

	public String getID(String Pid){
		return returnPlayerDriver(0,Pid);
	}
	
	public String getName(String Pid){
		return returnPlayerDriver(1,Pid);
	}
	public String getMode(String Pid){
		return returnPlayerDriver(2,Pid);
	}
	
	public String getWorld(String Pid){
		return returnPlayerDriver(3,Pid);
	}
	
	private String returnPlayerDriver(int column, String Pid){
		for (int i =0; i < PlayerData.length - 1; i++){
			   if(PlayerData[i][0] == Pid){
				  return PlayerData[i][column];
			   }
		}
		return null;
		
	}
	
	private void addPlayer(String[] pdata){
		String[][] pData = PlayerData;
		PlayerData = new String[pData.length][4];
		PlayerData[pData.length] = pdata;
	}
	
	//this should be called when the player dcs or something, or for keeping up with the list or smthin
	public void removePlayer(String Pid){
		String[][] pData = new String[PlayerData.length-1][4]; //Build new array
		int c = 0;	//Coutner for new array
		for (int i =0; i < PlayerData.length; i++){
		   if(PlayerData[i][0] != Pid){ //Is it the name we want to remove from list?
			  pData[c] = PlayerData[i]; //All items we dont want removed.
			  c++;
		   }
		}
		if (c == PlayerData.length-2){
			//if a player was removed, and because length starts from 1 and c starts from 0
			PlayerData = new String[pData.length][4];
			PlayerData = pData;
			
		}else{
		System.out.println("Player wasn't in the group. Nothing removed");
		}
		
	}
	

}
