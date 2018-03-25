package game;

import java.net.*;
import java.io.*;

public class Spaceship implements Serializable
{
	private Position pos;
	private String path;
	
	private int health;
	public static final int MAX_HEALTH 	= 4;
	
	private ShipSystems systems;

	public Spaceship()
	{
		pos = new Position();
		path = "";
		health = MAX_HEALTH;
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
	
	public void removeHealth(int valueToRemove) {
		health = (health - valueToRemove < 0 ? 0 
				: health - valueToRemove);
	}
	
	public void restoreHealth(int valueToAdd) {
		health = (health + valueToAdd > MAX_HEALTH ? MAX_HEALTH 
				: health + valueToAdd);
	}

	public ShipSystems getShipSystem()
	{
		return systems;
	}
}