package audio;

import java.util.HashMap;
import java.util.Map;

import game.Role;

public class SoundManager {

	
	public static final int MSG_SND_SOUND = 1;
	public static final int MSG_REC_SOUND = 2;
	public static final int MIN_PUT_SOUND = 3;
	public static final int MIN_EXP_SOUND = 4;
	public static final int MIS_LNH_SOUND = 5;
	public static final int MIS_EXP_SOUND = 6;
	public static final int SENSORS_SOUND = 7;
	public static final int MSE_HVR_SOUND = 8;
	public static final int MSE_CLK_SOUND = 9;
	
	private static Map<Role, MyPlayer> 		roleMusic;
	private static MyPlayer[] 	soundEffects = new MyPlayer[10];
	
	static{
		// Map Role to Associated Audio File
		roleMusic = new HashMap<Role, MyPlayer>();
		generatePlayer(Role.CAPTAIN);
		generatePlayer(Role.FIRST);
		generatePlayer(Role.ENGINE);
		generatePlayer(Role.NETWORK);
		generatePlayer(Role.RADIO);
		
		// Map integer value to every Sound Effect
		soundEffects[0] = new MyPlayer("MessageSend");
		soundEffects[1] = new MyPlayer("MessageRecieved");
		soundEffects[2] = new MyPlayer("MinePlace");
		soundEffects[3] = new MyPlayer("MineExplosion");
		soundEffects[4] = new MyPlayer("MissileLaunch");
		soundEffects[5] = new MyPlayer("MissileExplosion");
		soundEffects[6] = new MyPlayer("sensorySounds");					// Drone and Sonar
		soundEffects[7] = new MyPlayer("MouseHover");
		soundEffects[8] = new MyPlayer("MineClick");			
		// Add Boost, Spacewalk 15,16
	}
	
	public static void startRoleTrack(Role r) {
		roleMusic.get( r ).play();
	}
	
	
	private static void generatePlayer(Role r) {
		roleMusic.put( r , new MyPlayer(r.name()) );
		roleMusic.get( r ).makeLoopable();
		
	}


	public static void stopRoleTrack(Role r) {
		try {
			roleMusic.get( r ).stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void playSoundEffect(int t) {
		soundEffects[t].play();
	}

}
