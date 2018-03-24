
import game.GameMap;
import game.Spaceship;

public class AdmRadarProtocol
{
	private static int NUMCOMMANDS = 0;
	
	GameMap map1;
	
	public AdmRadarProtocol()
	{
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
			ship.chargeSystem("Sonar");
		}
		else if (command.equalsIgnoreCase("Missile"))
		{
			ship.chargeSystem("Missile");
		}
		else if (command.equalsIgnoreCase("Mine"))
		{
			ship.chargeSystem("Mine");
		}
		else if (command.equalsIgnoreCase("Drone"))
		{
			ship.chargeSystem("Drone");
		}
		else if (command.equalsIgnoreCase("Silent"))
		{
			ship.chargeSystem("Silent");
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
		return ship;
	}
	
	public GameMap updateMap()
	{
		return map1;
	}
}