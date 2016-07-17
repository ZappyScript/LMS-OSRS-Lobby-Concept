import java.io.IOException;
public class Lobby {
  
	
//build our player lists.	
private String[][] lobbyF2pCasual; // Group ID 0
private String[][] lobbyF2pComp;  // Group ID 1
private String[][] lobbyP2pCasual;   // Group ID 2
private String[][] lobbyP2pComp;    //Group ID 3

private PlayerData Pd;


public Lobby(int frequency) throws IOException, InterruptedException{
	Pd = new PlayerData();
	//We want the lobby open forever.
	while (System.in.available() == 0) {
	
	//Start getting our players. These are just randomly generated
	//But the system can take anything as long as the inputed user data is a String Array.
	String[][] player = Pd.generatePlayer(frequency);
	for (int i=0; i<player.length; i++){
		Pd.addPlayer(player[i]);
	}
	/*Runs our data handler.
	 *Since this is only a proof of concept, the data will be split
	 *into 4 groups. F2P casual, F2P comp, P2p casual, P2p comp.
	 *This does not include an ELO system but it's possible to add with a bit more time 
	 */
	pupulateData();
	
	/*
	 * There's 4 groups in total, so we want to loop them
	 *
	 */
		for (int i = 0; i <= 3; i++){

			// If gamelobby(game group id) is null, then the group contains less than 25 people
			// however, if it's not null and has data, it has 25 or more people
			if (GameLobby(i) != null){ 
			
				//Now we can get our first 25 players and put them together
				String[][] lmsGroup = StartAGame(GameLobby(i));
				if (lmsGroup != null){
					//now the group is together, we can now start to move the players 
					//to a new world to play together.
					worldHopper(lmsGroup);
				} 
			
			}
		
		}
	
	
		System.out.println("--Lobby count--" );

		int f2pcasualC = (lobbyF2pCasual != null) ? lobbyF2pCasual.length : 0;
		System.out.println("F2P casual has: " + f2pcasualC);

		int f2pcompC = (lobbyF2pComp != null) ? lobbyF2pComp.length : 0;
		System.out.println("F2p Comp has: " +f2pcompC );
	
		int p2pcasualC = (lobbyP2pCasual != null) ? lobbyP2pCasual.length : 0;
		System.out.println("P2P casual has:  " +p2pcasualC );
	
		int p2pcompC = (lobbyP2pComp != null) ? lobbyP2pComp.length : 0;
		System.out.println("P2p Comp has: " +p2pcompC );
		
		System.out.println("Games started: "+groupsHopped);
	
		Thread.sleep(600);
	}
	
}


public int groupsHopped=0;

/*Function: worldHopper
 * Usage: worldHopper(2D array player group data)
 * Purpose: Move the lobby to the same world.
 */

private void worldHopper(String[][] group){
	/*TODO:
	 *This needs to be the real RS code for hoppinh a person to another world.
	 *Just whatever function is called on 'Quick Hop'
	*/
	 
	int world = 0;
	switch(group[0][4]){
	case("Member"):
		 world = Pd.getRandomWorld(1);
		break;
	case("F2P"):
		world = Pd.getRandomWorld(0);
		break;
	}
	for (int i = 0; i<group.length; i++){
		
		System.out.println("Player hopped: "+group[i][0]+"/"+group[i][3]+"/"+world);
		//Move the player to a new world
	
	}
	groupsHopped++;
	
}

/*
 * Function GameLobby
 * Usage: GameLobby(int group ID)
 * Purpose: Finds if the group has 25+ players in it
 * If it does, return the group.
 * Else, just return null
 * 
 */
private String[][] GameLobby(int groupId){
	
	
	
	String[][] group = null;
	
	switch (groupId){
	case 0:
		
		group = lobbyF2pCasual;
		break;
	case 1:
		group = lobbyF2pComp;
		break;
	case 2:
		group = lobbyP2pCasual;
		break;
	case 3:
		group = lobbyP2pComp;
		break;
	
	}
	if(group != null){
		//here is where we check if a lobby is ready
		if (group.length >=25){
			return group;
		} 
	}
	
	return null;
}


/*
 * Function: StartAGame(2D Array player data)
 * Purpose: Gets the group of 25 people and puts them in a group
 * this is where we call the remove function, to remove them from the
 * lobby list
 * 
 */
private String[][] StartAGame(String[][] group){
	
	if(group != null){
		
		// if we get here, that means we have 25 players or more in our group, therefore meeting the requirements
		//now we get the first 25 of the group and throw them in a game together, we are creating the array here.
	
		String[][] lmsGroup = new String[25][4];
		for(int i=0; i<25; i++){
			//add them to the group of people to play together
			lmsGroup[i] = group[i]; 
			//remove them from the player list.
			Pd.removePlayer(lmsGroup[i][0]); 
		}
		
		return lmsGroup;
	}
	
	return null;
}

/*
 * Function: populateData
 * Usage: Call it when you need a group refresh
 * Purpose: Filters all the players and assigns them to a group
 */
private void pupulateData(){

	//Garbage the old stuff
	lobbyF2pCasual = null;
	lobbyF2pComp =  null;
	lobbyP2pCasual = null;
	lobbyP2pComp =  null;

	//Start looping thru all the players searching
	for (int i=0; i<Pd.PlayerData.length; i++){
		//null check
		if(Pd.PlayerData[i][2] != null){
			//See if the player is in competitive group
			if (Pd.PlayerData[i][2].contains("Comp")){
				//Is member?
				if (Pd.PlayerData[i][4].contains("Mem")){ 
					//Send the player to group id 3 with this data.
					updateGroup(3,Pd.PlayerData[i]);
				//Is f2p?	
				}else{
					//assign player to group
					updateGroup(1,Pd.PlayerData[i]);
				}
			//see if the player is in the casual group
			}else if  (Pd.PlayerData[i][2].contains("Cas")){
				//Is member?
				if(Pd.PlayerData[i][4].contains("Mem")){
					//assign a player to group
					updateGroup(2,Pd.PlayerData[i]);
				//Is F2p?
				}else{
					//assign a player to group
					updateGroup(0,Pd.PlayerData[i]);
				}			
			
			}
		}
	}
	
}

/*
 * Function: updateGroup
 * Usage: Takes group ID, player data.
 * Purpose: Just adds the user to the array group.
 * 
 */
	private void updateGroup(int id, String[] pData){
		String[][] newGroup;
		int size = 0;
		if(pData[0] != null){
			switch(id){
			case 0:
				//Simple array addition in java.... This repeats 4 times.
				//First get the size of the new array
				size = (lobbyF2pCasual != null) ? lobbyF2pCasual.length :0;
				//Make a new array with +1 of the size.
				newGroup = new String[size+1][4];
				//Add the last item onto the array
				newGroup[size] = pData;
				//fill in the data 0->the one we just added (last).
				if(size != 0 ){
					for (int i=0; i < size; i++){
						newGroup[i] = lobbyF2pCasual[i];
					}
				}
				//dispose + recreate our global array with new length
				lobbyF2pCasual = new String[newGroup.length][4];
				//Set the global array.
				lobbyF2pCasual = newGroup;
				break;
			case 1:
				size = (lobbyF2pComp  != null) ? lobbyF2pComp.length : 0;
				newGroup = new String[size+1][5];
				newGroup[size] = pData;
				if(size != 0 ){
				for (int i=0; i < size; i++){
					newGroup[i] = lobbyF2pComp[i];
				}
				}
				lobbyF2pComp = new String[newGroup.length-1][4];
				lobbyF2pComp = newGroup;
				break;
			case 2:
			    size = (lobbyP2pCasual  != null) ? lobbyP2pCasual.length :0;
			    newGroup = new String[size+1][5];
				newGroup[size] = pData;
				if(size != 0 ){
				for (int i=0; i < size; i++){
					newGroup[i] = lobbyP2pCasual[i];
				}
				}
				lobbyP2pCasual = new String[newGroup.length-1][4];
				lobbyP2pCasual = newGroup;
				break;
			case 3:
				size = (lobbyP2pComp  != null) ? lobbyP2pComp.length :0;
				 newGroup = new String[size+1][5];
				 newGroup[size] = pData;
				if(size != 0 ){
			      for (int i=0; i < size; i++){
					  newGroup[i] = lobbyP2pComp[i];
					 }
					}
				lobbyP2pComp = new String[newGroup.length][4];
				lobbyP2pComp = newGroup;
				break;
			
			}
			
		}
		
	}

	
}
