
import java.net.*;
import java.io.*;

public class AdmRadarProtocol
{
	private static final int NUMCOMMANDS = 0;
	
	Maps map1;
	
	public AdmRadarProtocol()
	{
		map1 = new Maps();
	}
	
	public Spaceship processCommands(String command,Spaceship ship)
	{
		System.out.println("Check 1");
		if (command.equalsIgnoreCase("NORTH"))
		{
			ship.getNextDirection("N");
		}
		else if (command.equalsIgnoreCase("SOUTH"))
		{
			ship.getNextDirection("S");
		}
		else if (command.equalsIgnoreCase("EAST"))
		{
			ship.getNextDirection("E");
		}
		else if (command.equalsIgnoreCase("WEST"))
		{
			ship.getNextDirection("W");
		}
		else if (command.equalsIgnoreCase("exit"))
		{
			ship = null;
		}
	
		return ship;
	}
	
	public Maps updateMap()
	{
		return map1;
	}
}