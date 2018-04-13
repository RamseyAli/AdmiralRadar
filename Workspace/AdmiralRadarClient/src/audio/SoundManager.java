package audio;

import game.Role;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import pref.GamePreferences;

public class SoundManager {

	
	private static MyPlayer sound = new MyPlayer("pit");
	
	static{
		
		Application.launch(  );
	}
	
	public static void startRoleTrack(Role r) {

		sound.playTrack();
		
	}

	public static void startTrack(int t) {

	}

	public static void playSoundEffect(int t) {

	}

}
