package audio;

import java.util.HashMap;
import java.util.Map;

import game.Role;

public class SoundManager {

	private static Map<Role, MyPlayer> players; 
	
	static{
		players = new HashMap<Role, MyPlayer>();
		players.put( Role.CAPTAIN , new MyPlayer(Role.CAPTAIN.name()) );
		players.put( Role.FIRST , new MyPlayer(Role.FIRST.name()) );
		players.put( Role.ENGINE , new MyPlayer(Role.ENGINE.name()) );
		players.put( Role.NETWORK , new MyPlayer(Role.NETWORK.name()) );
		players.put( Role.RADIO , new MyPlayer(Role.RADIO.name()) );
	}
	
	public static void startRoleTrack(Role r) {
		players.get( r ).play();
	}

	public static void playSoundEffect(int t) {

	}

}
