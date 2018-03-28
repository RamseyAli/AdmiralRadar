package game;

import java.io.*;

import net.MyPacketable;

public class Spaceship implements Serializable, MyPacketable
{

	private Position pos;
	private String path;
	
	private int health;
	public static final int MAX_HEALTH 	= 4;
	
	private ShipSystems systems;

	private static final long serialVersionUID = 1L;
	
	private static String nextDir;
	
	public Spaceship()
	{
		pos = new Position();
		pos.setPosition(-1, -1);
		path = "";
		health = MAX_HEALTH;
		systems = new ShipSystems();
	}

	public void setPos(Position p)
	{
		pos = p;
	}
	
	public void setDirection(String dir)
	{
		nextDir = dir;
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
	
	public String getDirection()
	{
		return nextDir;
	}
	
	public ShipSystems getShipSystem()
	{
		return systems;
	}
	
	public void removeHealth(int valueToRemove) {
		health = (health - valueToRemove < 0 ? 0 
				: health - valueToRemove);
	}
	
	public void restoreHealth() {
		health = MAX_HEALTH;
	}
	
	public void destroyShip()
	{
		pos.setPosition(-1, -1);
		path = "";
		health = 0;
		nextDir = "";
	}
	
	public void printShip() //For testing purposes
	{
		System.out.println("Position: x = "+pos.x+", y = "+pos.y);
		System.out.println("Path: "+path);
		System.out.println("Health: "+health);
		systems.printSystems();
	}
}