import java.net.*;
import java.io.*;

public class Spaceship implements Serializable
{
	Position pos;
	String path;
	int health;
	ShipSystems sys;

	Spaceship()
	{
		pos = new Position();
		path = "";
		health = 4;
		sys = new ShipSystems();
	}

	void setPos(Position p)
	{
		pos = p;
	}
	
	void getNextDirection(String dir)
	{
		System.out.println("Check 2");
		path = path+" "+dir;
		System.out.println(path);
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
	
	public void printPosition()
	{
		System.out.println("Ship at x = "+pos.x+" y = "+pos.y);
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
		return sys;
	}
}