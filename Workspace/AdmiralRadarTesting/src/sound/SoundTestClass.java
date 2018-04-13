package sound;

import java.io.File;

import javafx.scene.media.*;

public class SoundTestClass {

	public static void main(String[] args) {
		String bip = "pit.mp3";

		Media hit = new Media(new File(bip).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();

	}

}
