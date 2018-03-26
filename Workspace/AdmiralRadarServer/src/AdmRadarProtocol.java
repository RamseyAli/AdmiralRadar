
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
		else if (command.equals("0") || command.equals("1") || 
				command.equals("2") || command.equals("3") || 
				command.equals("4") || command.equals("5") || 
				command.equals("6") || command.equals("7") || 
				command.equals("8") || command.equals("9") || 
				command.equals("10") || command.equals("11") || 
				command.equals("12") || command.equals("13") || 
				command.equals("14") || command.equals("15") || 
				command.equals("16") || command.equals("17") || 
				command.equals("18") || command.equals("19") || 
				command.equals("20") || command.equals("21") || 
				command.equals("22") || command.equals("23"))
		{
			ship.getShipSystem().disableSystemComponent(Integer.parseInt(command));
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