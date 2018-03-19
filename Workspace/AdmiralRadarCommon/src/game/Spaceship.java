package game;

import java.net.*;
import java.io.*;

public class Spaceship implements Serializable
{
	Position pos;
	String path;
	int health;
	ShipSystems systems;

	public Spaceship()
	{
		pos = new Position();
		path = "";
		health = 4;
		systems = new ShipSystems();
	}

	public void setPos(Position p)
	{
		pos = p;
	}
	
	public void getNextDirection(String dir)
	{
		path = path+" "+dir;
		if (dir.equalsIgnoreCase("N"))
		{
			pos.y -= 1;
		}
		else if(dir.equalsIgnoreCase("S"))
		{
			pos.y += 1;
		}
		else if(dir.equalsIgnoreCase("E"))
		{
			pos.x +=1;
		}
		else if(dir.equalsIgnoreCase("W"))
		{
			pos.x -= 1;
		}
	}

	public void chargeSystem(String sys)
	{
		if (sys.equalsIgnoreCase("Sonar"))
		{
			systems.sonar[0] += 1;
		}
		else if(sys.equalsIgnoreCase("Missile"))
		{
			systems.missile[0] += 1;
		}
		else if(sys.equalsIgnoreCase("Mine"))
		{
			systems.mine[0] +=1;
		}
		else if(sys.equalsIgnoreCase("Drone"))
		{
			systems.drone[0] += 1;
		}
		else if(sys.equalsIgnoreCase("Silent"))
		{
			systems.silent[0] += 1;
		}
		
		systems.checkActive();
	}
		
	public String getPath()
	{
		return path;
	}

	public Position getPosition()
	{
		return pos;
	}

	public int getHealth()
	{
		return health;
	}

	public ShipSystems getShipSystem()
	{
		return systems;
	}
}