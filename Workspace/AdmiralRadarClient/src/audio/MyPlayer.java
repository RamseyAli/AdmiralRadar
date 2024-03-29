package audio;

import java.io.File;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import pref.GamePreferences;

public class MyPlayer extends Application{
	
	public Media		med;
	public MediaPlayer	mp;
	public String name;
	
	public MyPlayer(String s){
		name = s;
		com.sun.javafx.application.PlatformImpl.startup(()->{});
		med = new Media((new File(GamePreferences.RESOURCES_PATH + name + ".mp3")).toURI().toString());
        mp = new MediaPlayer(med);
        
	}
	
	public void makeLoopable() {
		mp.setCycleCount(Integer.MAX_VALUE);
	}
	
	public void setString(String s){
		name = s;
	}
	
	public void play() {
		mp.seek(new Duration(0));
		mp.play();
	}
	
	public void stop() {
		mp.stop();
	}
    

	@Override
	public void start(Stage primaryStage) throws Exception {
		
       
	}
	
}