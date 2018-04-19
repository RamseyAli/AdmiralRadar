package demo;

import javax.swing.JOptionPane;

import game.Role;
import graphic.TestServerSam;
import pref.GamePreferences;
import visual.util.operations.GUIFactory;

public class GUIDemo {


	public static void main(String[] args){

		GamePreferences.setFullscreen( false );
		// Create GUI Factory
		GUIFactory factory = new GUIFactory();

		// Create and Show GUI
		//	new Thread( () -> factory.beginGUI() ).start();

		// Start local test server
		DemoTestServer ts = new DemoTestServer();
		Thread t = new Thread( ts );
		t.start();


		String[] options = {"Damage Demo",
				"MineMissleDemo",
		"Demo 3"};

		switch(JOptionPane.showOptionDialog(null,
				"Which Demo would you like to see?",
				"Demo Options",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				null)){
					case 0:
						damageDemo();
						break;
					case 1:
						MineMissleDemo();
						break;
					case 2:

						break;
					default: break;
		}

		System.exit( 0 );
	}



	private static void damageDemo(){
		System.out.println( "Damgae Demo" );
	}

	private static void MineMissleDemo(){
		System.out.println( "Mine Missle Demo" );
	}



}
