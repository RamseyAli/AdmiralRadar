package src;  // Something Pano needs to run, (maybe) comment out if your're not Pano

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
		else if (command.equalsIgnoreCase("Charge Sonar"))
		{
			ship.chargeSystem("Sonar");
		}
		else if (command.equalsIgnoreCase("Charge Missile"))
		{
			ship.chargeSystem("Missile");
		}
		else if (command.equalsIgnoreCase("Charge Mine"))
		{
			ship.chargeSystem("Mine");
		}
		else if (command.equalsIgnoreCase("Charge Drone"))
		{
			ship.chargeSystem("Drone");
		}
		else if (command.equalsIgnoreCase("Charge Silent"))
		{
			ship.chargeSystem("Silent");
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