package audio;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import pref.GamePreferences;

public class MyPlayer extends Application{
	
	public Media		med;
	public MediaPlayer	mp;
	
	public MyPlayer(String s){

		med = new Media(GamePreferences.RESOURCES_PATH + s + ".mp3");
		mp = new MediaPlayer(med);

		

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

	public void playTrack() {


		mp.play();


	}

}
