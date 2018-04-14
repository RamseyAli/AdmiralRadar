package helpers;

import game.Direction;
import game.GameMap;
import game.Spaceship;
import game.Systems;

public class AdmRadarProtocol {
	@SuppressWarnings("unused")
	private static int NUMCOMMANDS;

	GameMap map1;

	public AdmRadarProtocol() {
		NUMCOMMANDS = 0;
		map1 = new GameMap();
	}
	
	public Spaceship processDirections(Direction dir, Spaceship ship) {
		if (ship == null) return null;

		NUMCOMMANDS++;
		
		ship.setDirection(dir);
		
		return ship;
	}
	
	public Spaceship processCommands(Systems command, Spaceship ship) {
		if (ship == null) return null;

		NUMCOMMANDS++;
		if (command == Systems.RADAR) {
			ship.getShipSystem().chargeSystem( Systems.RADAR );
		} else if (command == Systems.MISSILE) {
			ship.getShipSystem().chargeSystem( Systems.MISSILE );
		} else if (command == Systems.MINE) {
			ship.getShipSystem().chargeSystem( Systems.MINE );
			;
		} else if (command == Systems.DRONE) {
			ship.getShipSystem().chargeSystem( Systems.DRONE );
		} else if (command == Systems.BOOST) {
			ship.getShipSystem().chargeSystem( Systems.BOOST );
		} else if (command.getPayload().equals( "0" ) || command.getPayload().equals( "1" ) 
				|| command.getPayload().equals( "2" ) || command.getPayload().equals( "3" )
				|| command.getPayload().equals( "4" ) || command.getPayload().equals( "5" ) 
				|| command.getPayload().equals( "6" ) || command.getPayload().equals( "7" )
				|| command.getPayload().equals( "8" ) || command.getPayload().equals( "9" ) 
				|| command.getPayload().equals( "10" ) || command.getPayload().equals( "11" )
				|| command.getPayload().equals( "12" ) || command.getPayload().equals( "13" ) 
				|| command.getPayload().equals( "14" ) || command.getPayload().equals( "15" )
				|| command.getPayload().equals( "16" ) || command.getPayload().equals( "17" ) 
				|| command.getPayload().equals( "18" ) || command.getPayload().equals( "19" )
				|| command.getPayload().equals( "20" ) || command.getPayload().equals( "21" ) 
				|| command.getPayload().equals( "22" ) || command.getPayload().equals( "23" )) {
			ship.getShipSystem().disableSystemComponent( Integer.parseInt( command.getPayload() ) );
		} else if (command.getPayload().equals( "exit" )) {
			ship = null;
		} else {

		}

		return ship;
	}

	public GameMap updateMap() {
		return map1;
	}
}