import java.net.*;
import java.io.*;

public class Spaceship
{
	Position pos;
	String path;
	int health;
	ShipSystems sys;

	Spaceship(Position p)
	{
		pos = p;
		path = "";
		health = 4;
		sys = new ShipSystems();
	}

	void getNextDirection(String dir)
	{
		path = path+" "+dir;
		if (dir == "N")
		{
			pos.y -= 1;
		}
		else if(dir == "S")
		{
			pos.y += 1;
		}
		else if(dir == "E")
		{
			pos.x +=1;
		}
		else if(dir == "W")
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

	public ShipSystems getShipSystem()
	{
		return sys;
	}
}