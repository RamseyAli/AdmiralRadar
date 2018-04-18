package audio;

import java.util.HashMap;
import java.util.Map;

import game.Role;

public class SoundManager {

	private static Map<Role, MyPlayer> 		roleMusic;
	private static Map<Integer, MyPlayer> 	soundEffects;
	private static Map<Integer, MyPlayer>	soundtrack;
	
	static{
		// Map Role to Associated Audio File
		roleMusic = new HashMap<Role, MyPlayer>();
		roleMusic.put( Role.CAPTAIN , new MyPlayer(Role.CAPTAIN.name()) );
		roleMusic.get( Role.CAPTAIN ).makeLoopable();
		
		roleMusic.put( Role.FIRST , new MyPlayer(Role.FIRST.name()) );
		roleMusic.get( Role.FIRST ).makeLoopable();
		
		roleMusic.put( Role.ENGINE , new MyPlayer(Role.ENGINE.name()) );
		roleMusic.get( Role.ENGINE ).makeLoopable();
		
		roleMusic.put( Role.NETWORK , new MyPlayer(Role.NETWORK.name()) );
		roleMusic.get( Role.NETWORK ).makeLoopable();
		
		roleMusic.put( Role.RADIO , new MyPlayer(Role.RADIO.name()) );
		roleMusic.get( Role.RADIO ).makeLoopable();
		
		// Map integer value to every Sound Effect
		soundEffects = new HashMap<Integer, MyPlayer>();
		soundEffects.put(0, new MyPlayer("MessageSend"));
		soundEffects.put(1, new MyPlayer("MessageRecieved"));
		soundEffects.put(2, new MyPlayer("MinePlace"));
		soundEffects.put(3, new MyPlayer("MineExplosion"));
		soundEffects.put(4, new MyPlayer("MissileLaunch"));
		soundEffects.put(5, new MyPlayer("MissileExplosion"));
		soundEffects.put(6, new MyPlayer("sensorySounds"));					// Drone and Sonar
		soundEffects.put(7, new MyPlayer("MouseHover"));
		soundEffects.put(8, new MyPlayer("MineClick"));			
		// Add Boost, Spacewalk 15,16
	}
	
	public static void startRoleTrack(Role r) {
		roleMusic.get( r ).play();
	}
	
	
	public static void stopRoleTrack(Role r) {
		try {
			roleMusic.get( r ).stop();
		} catch (Exception e) {
			System.out.println("ERROR: Problem stopping music track...");
			e.printStackTrace();
		}
	}
	
	public static void playSoundEffect(int t) {
		soundEffects.get(t).play();
	}

}
