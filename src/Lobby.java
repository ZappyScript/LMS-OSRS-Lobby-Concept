import java.util.Random;

public class Lobby {
  
	
//build our player lists.	
private String[][] f2pCasual; // Group ID 0
private String[][] f2pComp;  // Group ID 1
private String[][] p2pCasual;   // Group ID 2
private String[][] p2pComp;    //Group ID 3

private PlayerData Pd;


public Lobby(){
	pupulateData();
	//loop the game mode groups and start full lobbies.
	for (int i = 0; i == 3; i++){
		
		//Load each game lobby and check if it's null, it isn't, then we have a game lobby of 25+ people.
		if (GameLobby(i) != null){ 
			String[][] lmsGroup = StartAGame(GameLobby(i));
			if (lmsGroup != null){
				worldHopper(lmsGroup);
			}
				
		}
		
	}
	
	
	
	
}

/*
 * 
 * PopulatData function
 * Purpose: Filter all players into 4 groups 
 * so we can get games ASAP / more random if there's a lot of players / qued system
 * 
 */


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
private String[] playerHopped;
private void worldHopper(String[][] group){
	/*TODO:
	 *This needs to be the real RS code for hoppinh a person to another world.
	 *Just whatever function is called on 'Quick Hop'
	*/
	
	Random rand = new Random(); 
	int world = 0;
	switch(group[0][4]){
	case("Member"):
		 world = rand.nextInt(worlds[1].length);
		break;
	case("F2p"):
		world = rand.nextInt(worlds[0].length);
		break;
	}
	int start = (playerHopped.length > 0)? playerHopped.length : 0;
	for (int i = 0; i<group.length; i++){
		//for simulation purposes, we are just going to store the player id, prev world and new world in an array
		playerHopped[start+i] = group[i][0]+"/"+group[i][3]+"/"+world; 
		System.out.println("Player hopped: "+group[i][0]+"/"+group[i][3]+"/"+world);
	}
	
	
	
}


private String[][] GameLobby(int groupId){
	
	String[][] group = null;
	
	switch (groupId){
	case 0:
		group = f2pCasual;
		break;
	case 1:
		group = f2pComp;
		break;
	case 2:
		group = p2pCasual;
		break;
	case 3:
		group = p2pComp;
		break;
	
	}
	if(group != null){
		if (group.length >=25){
			return group;
		} 
	}
	
	return null;
}

@SuppressWarnings("null") //It's BS, Will never get to this stage and be null.
private String[][] StartAGame(String[][] group){
	
	if(group != null){
		
		// if we get here, that means we have 25 players or more in our group, therefore meeting the requirements
		//now we get the first 25 of the group and throw them in a game together, we are creating the array here.
		String[][] lmsGroup = null;
		for(int i=0; i==25; i++){
			//add them to the group of people to play together
			lmsGroup[i] = group[i]; 
			//remove them from the player list.
			Pd.removePlayer(lmsGroup[i][0]); 
		}
		
		return lmsGroup;
	}
	
	return null;
}


private void pupulateData(){
	p2pComp =null;
	f2pComp = null;
	p2pCasual = null;
	f2pCasual = null;
	//counters
	int countF2pC = 0,countF2pCp = 0,countP2pC = 0,countP2pCp = 0;
	
	for (int i=0; i<Pd.PlayerData.length; i++){
		//Start pulling the current list
		switch(Pd.PlayerData[i][2]){
			case ("Competitive"):
				if (Pd.PlayerData[i][4] == "Member"){ 
					
					//assign a player to group
					p2pComp[countP2pCp] = Pd.PlayerData[i];
					countP2pCp++;
				}else{
					//assign a player to group
					f2pComp[countF2pCp] = Pd.PlayerData[i];
					countF2pCp++;
				}
				
				break;
			case ("Casual"):
				if(Pd.PlayerData[i][4] == "Member"){
					//assign a player to group
					p2pCasual[countP2pC] = Pd.PlayerData[i];
					countP2pC++;
				}else{
					//assign a player to group
					f2pCasual[countF2pC] = Pd.PlayerData[i];
					countP2pC++;
				}
		}
		
	}
	
}


	
}
