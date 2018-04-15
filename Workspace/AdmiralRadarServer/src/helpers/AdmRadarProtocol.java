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

		System.out.println( "Himalayan Pink Pepper" );
		NUMCOMMANDS++;
		
		ship.setDirection(dir);
		
		return ship;
	}
	
	public Spaceship processSystems(Systems command, Spaceship ship) {
		if (ship == null) return null;

		NUMCOMMANDS++;
		ship.getShipSystem().chargeSystem( command );
		
		return ship;
	}
	
	public Spaceship processParts(int partNo, Spaceship ship) {
		if(ship == null) return null;
		
		NUMCOMMANDS++;
		if(partNo >= 0 && partNo <= 23) {
			ship.getShipSystem().disableSystemComponent(partNo);
		} else {
			// Do nothing
		}
		
		return ship;
	}
	
	public GameMap updateMap() {
		return map1;
	}
}