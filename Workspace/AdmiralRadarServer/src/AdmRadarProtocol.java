
import game.GameMap;
import game.Spaceship;

public class AdmRadarProtocol
{
	private static int NUMCOMMANDS;
	
	GameMap map1;
	
	public AdmRadarProtocol()
	{
		NUMCOMMANDS = 0;
		map1 = new GameMap();
	}
	
	public Spaceship processCommands(String command,Spaceship ship)
	{
		if(ship == null)
			return null;
		
		NUMCOMMANDS++;
		
		if (command.equalsIgnoreCase("NORTH"))
		{
			ship.setDirection("N");
		}
		else if (command.equalsIgnoreCase("SOUTH"))
		{
			ship.setDirection("S");
		}
		else if (command.equalsIgnoreCase("EAST"))
		{
			ship.setDirection("E");
		}
		else if (command.equalsIgnoreCase("WEST"))
		{
			ship.setDirection("W");
		}
		else if (command.equalsIgnoreCase("Sonar"))
		{
			ship.getShipSystem().chargeSystem("Sonar");
		}
		else if (command.equalsIgnoreCase("Missile"))
		{
			ship.getShipSystem().chargeSystem("Missile");
		}
		else if (command.equalsIgnoreCase("Mine"))
		{
			ship.getShipSystem().chargeSystem("Mine");;
		}
		else if (command.equalsIgnoreCase("Drone"))
		{
			ship.getShipSystem().chargeSystem("Drone");
		}
		else if (command.equalsIgnoreCase("Silent"))
		{
			ship.getShipSystem().chargeSystem("Silent");
		}
		else if (command.equalsIgnoreCase("Weapons"))
		{
			
		}
		else if (command.equalsIgnoreCase("Surveillance"))
		{
			
		}
		else if (command.equalsIgnoreCase("Stealth"))
		{
			
		}
		else if (command.equalsIgnoreCase("Radiation"))
		{
			
		}
		else if (command.equalsIgnoreCase("exit"))
		{
			ship = null;
		}
		else
		{
			
		}
		
		return ship;
	}
	
	public GameMap updateMap()
	{
		return map1;
	}
}