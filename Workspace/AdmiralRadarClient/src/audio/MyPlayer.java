package audio;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import pref.GamePreferences;

public class MyPlayer extends Application{
	
	public Media		med;
	public MediaPlayer	mp;
	public String name;
	
	public MyPlayer(String s){
		name = s;
		com.sun.javafx.application.PlatformImpl.startup(()->{});
		med = new Media(GamePreferences.RESOURCES_PATH + name + ".mp3");
        mp = new MediaPlayer(med);
        
	}
	
	public void setString(String s){
		name = s;
	}
	
	public void play() {
		mp.play();
	}
    

	@Override
	public void start(Stage primaryStage) throws Exception {
		
       
	}
	
}