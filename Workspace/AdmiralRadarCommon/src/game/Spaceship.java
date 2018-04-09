package game;

import java.io.*;
import java.util.ArrayList;

import net.MyPacketable;

public class Spaceship implements Serializable, MyPacketable {

	private Position				pos;
	private ArrayList<Direction>	path;

	private int				health;
	public static final int	MAX_HEALTH	= 4;

	private MineController	shipMines;
	private ShipSystems		systems;

	private static final long serialVersionUID = 1L;

	private Direction nextDir;

	public Spaceship() {
		pos = new Position();
		pos.setPosition( -1 , -1 );
		path = new ArrayList<Direction>();
		health = MAX_HEALTH - 3;
		systems = new ShipSystems();
		nextDir = Direction.STOP;
	}

	public void setPos(Position p) {
		pos = p;
	}

	public void setDirection(Direction dir) {
		nextDir = dir;
		path.add( dir );
		if (dir == Direction.NORTH) {
			pos.setY( pos.getY() - 1 );
		} else if (dir == Direction.SOUTH) {
			pos.setY( pos.getY() + 1 );
		} else if (dir == Direction.EAST) {
			pos.setX( pos.getX() + 1 );
		} else if (dir == Direction.WEST) {
			pos.setX( pos.getX() - 1 );
		}
	}

	public ArrayList<Direction> getPath() {
		return path;
	}

	public Position getPosition() {
		return pos;
	}

	public int getHealth() {
		return health;
	}

	public Direction getDirection() {
		return nextDir;
	}

	public MineController getShipMines() {
		return shipMines;
	}

	public ShipSystems getShipSystem() {
		return systems;
	}

	public void removeHealth(int valueToRemove) {
		health = ( health - valueToRemove < 0 ? 0 : health - valueToRemove );
	}

	public void restoreHealth() {
		health = MAX_HEALTH;
	}

	public void destroyShip() {
		pos.setPosition( -1 , -1 );
		path.clear();
		health = 0;
		nextDir = Direction.STOP;
	}

	// Drone //
	public boolean checkSector(int guess, int n, int m) // n = dimension of N x N map // m = how many sectors in each row, m x m sectors
	{
		int sector;
		int secSize;
		boolean systemReady;
		
		systemReady = systems.useSystem("Drone");
		if (!systemReady)
		{
			// Should throw exception in the future, system is not fully charge
			// throw new Exception()
		} 
		
		// Note: "GamePreferences.SEG" is the size of the Map //
		secSize = n / m;
		
		sector = m * (pos.getY() / secSize) + (pos.getX() / secSize);
		
		if (sector == guess)
			return true;
			
		return false;
	}
	
	public void printShip() // For testing purposes
	{
		System.out.println( "Position: x = " + pos.getX() + ", y = " + pos.getY() );
		System.out.println( "Path: " + path );
		System.out.println( "Health: " + health );
		systems.printSystems();
	}

	public void toGameStartCondition(Position p) {
		health = MAX_HEALTH;
		pos = p;
		
	}
}