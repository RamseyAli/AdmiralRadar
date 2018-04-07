package game;

import java.io.*;
import java.util.ArrayList;

import net.MyPacketable;

public class Spaceship implements Serializable, MyPacketable
{

	private Position pos;
	private ArrayList<Direction> path;
	
	private int health;
	public static final int MAX_HEALTH 	= 4;
	
	private MineController shipMines;
	private ShipSystems systems;

	private static final long serialVersionUID = 1L;
	
	private Direction nextDir;
	
	public Spaceship()
	{
		pos = new Position();
		pos.setPosition(-1, -1);
		path = new ArrayList<Direction>();
		health = MAX_HEALTH;
		systems = new ShipSystems();
		nextDir = Direction.STOP;
	}

	public void setPos(Position p)
	{
		pos = p;
	}
	
	public void setDirection(Direction dir)
	{
		nextDir = dir;
		path.add( dir );
		if (dir == Direction.NORTH)
		{
			pos.setY(pos.getY() - 1);
		}
		else if(dir == Direction.SOUTH)
		{
			pos.setY(pos.getY() + 1);
		}
		else if(dir == Direction.EAST)
		{
			pos.setX(pos.getX() + 1);
		}
		else if(dir == Direction.WEST)
		{
			pos.setX(pos.getX() - 1);
		}
	}
		
	public ArrayList<Direction> getPath()
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
	
	public Direction getDirection()
	{
		return nextDir;
	}
	
	public MineController getShipMines() {
		return shipMines;
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
		path.clear();
		health = 0;
		nextDir = Direction.STOP;
	}
	
	public void printShip() //For testing purposes
	{
		System.out.println("Position: x = "+pos.getX()+", y = "+pos.getY());
		System.out.println("Path: "+path);
		System.out.println("Health: "+health);
		systems.printSystems();
	}
}