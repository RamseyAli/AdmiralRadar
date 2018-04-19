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
	public static final int SHPBOST_SOUND = 10;
	public static final int SPCWALK_SOUND = 11;
	
	private static Map<Role, MyPlayer> 		roleMusic;
	private static MyPlayer[] 	soundEffects = new MyPlayer[12];
	
	static {
		// Map Role to Associated Audio File
		roleMusic = new HashMap<Role, MyPlayer>();
		generatePlayer(Role.CAPTAIN);
		generatePlayer(Role.FIRST);
		generatePlayer(Role.ENGINE);
		generatePlayer(Role.NETWORK);
		generatePlayer(Role.RADIO);
		
		// Map integer value to every Sound Effect
		soundEffects[MSG_SND_SOUND] = new MyPlayer("MessageSend");
		soundEffects[MSG_REC_SOUND] = new MyPlayer("MessageRecieved");
		soundEffects[MIN_PUT_SOUND] = new MyPlayer("MinePlace");
		soundEffects[MIN_EXP_SOUND] = new MyPlayer("MineExplosion");
		soundEffects[MIS_LNH_SOUND] = new MyPlayer("MissileLaunch");
		soundEffects[MIS_EXP_SOUND] = new MyPlayer("MissileExplosion");
		soundEffects[SENSORS_SOUND] = new MyPlayer("sensorySounds");					// Drone and Sonar
		soundEffects[MSE_HVR_SOUND] = new MyPlayer("MouseHover");
		soundEffects[MSE_CLK_SOUND] = new MyPlayer("MouseButtonClick");
		soundEffects[SHPBOST_SOUND] = new MyPlayer("ShipBoost");
		soundEffects[SPCWALK_SOUND] = new MyPlayer("Spacewalk");
	}
	
	public static void startRoleTrack(Role r) {
		stopAllTracks();
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
			System.out.println("ERROR: Problem stopping music track...");
			e.printStackTrace();
		}
	}
	
	public static void stopAllTracks(){
		for (MyPlayer mp : roleMusic.values()) {
		    mp.stop();
		}
		
		for (int i = 1; i < soundEffects.length; i++) 
			soundEffects[i].stop();
	}
	
	public static void playSoundEffect(int t) {
		soundEffects[t].play();
	}

}
