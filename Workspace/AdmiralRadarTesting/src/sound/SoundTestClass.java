package sound;

import audio.SoundManager;
import game.Role;

public class SoundTestClass{
	
	public static void main(String[] args) {
		
		SoundManager.startRoleTrack( Role.NETWORK );
		
		hold(2);
		
		System.out.println( "3" );
		hold(1);
		System.out.println( "2" );
		hold(1);
		System.out.println( "1" );
		hold(1);
		System.out.println( "0" );
		
		SoundManager.startRoleTrack( Role.RADIO );
		
		System.out.println( "3" );
		hold(1);
		System.out.println( "2" );
		hold(1);
		System.out.println( "1" );
		hold(1);
		System.out.println( "0" );
		
		SoundManager.playSoundEffect( SoundManager.SHPBOST_SOUND );
		
	}
	
	
	private static void hold(int s){
		
		try {
			Thread.sleep( 1000*s );
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
