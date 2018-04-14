package game;

import java.io.*;
import java.util.ArrayList;
import java.util.Random; // for Sonar

import net.MyPacketable;

public class Spaceship implements Serializable, MyPacketable {

	private Position				pos;
	private ArrayList<Direction>	path;

	private int				health;
	public static final int	MAX_HEALTH	= 4;

	private MineController	shipMines;
	private ShipSystems		systems;

	private static final long serialVersionUID = 1L;
	private static Random rand = new Random();
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

	public boolean restoreSystems() {
		return systems.enableSystems();
	}

	public void destroyShip() {
		pos.setPosition( -1 , -1 );
		path.clear();
		health = 0;
		nextDir = Direction.STOP;
	}
	
	/* TACTICAL SYSTEMS */
	public void placeMine(Position min) {
		if (systems.useSystem(Systems.MINE))
			shipMines.addMine(min);
	}
	
	public void blastMine(Position min, ArrayList<Spaceship> ships) {
		shipMines.detonateMine(min, ships, true);
	}
	
	public boolean hasPlacedMines() {
		return shipMines.getMines().size() > 0;
	}
	
	public void launchMissile(Position miss, ArrayList<Spaceship> ships) { // Fix Missile and MINE checks
		if (!systems.useSystem( Systems.MISSILE )) return;

		for (Spaceship ship : ships) {
			// If a mine exists in the same position as the launched missile,
			// then detonate the mine but don't damage overlapping and/or adjacent ships
			
			if (miss.equals(ship.getPosition()))
				ship.removeHealth(2);
			if (miss.isAdjacent(ship.getPosition()))
				ship.removeHealth(1);

			ship.getShipMines().detonateMine( miss , ships , false );

		}

		// missile[CHARGED_STATUS] = NOT_CHARGED;
		// missile[POWER_LEVEL] = 0;
	}
	// Drone //
	private int getSector(int n, int m)
	{
		int secSize;
		// Note: "GamePreferences.SEG" is the size of the Map //
		secSize = n / m;
				
		return m * ( pos.getY() / secSize ) + ( pos.getX() / secSize );
	}
	public boolean checkSector(int guess, int n, int m) // n = dimension of N x N map // m = how many sectors in each row, m x m sectors
	{
		int sector;
		boolean systemReady;
		
		systemReady = systems.useSystem(Systems.DRONE);
		if (!systemReady)
		{
			// Should throw exception in the future, system is not fully charge
			// throw new Exception()
		} 
		if (n < 1 || m < 1)
		{
			// incorrect dimensions
		}
		
		sector = getSector(n, m);
		
		if (sector == guess)
			return true;
		
		return false;
	}
	// Current sonar implementation //
	// Will return array of 3 integers, representing X, Y, and Sector of ship //
	// The non-returned value is -1 //
	public int[] randomSonar(int n, int m) // n = dimension of map
	{
		int randNum, lie;
		int ans[] = new int[3];
		int indices[] = new int [2]; // 2 indices corresponding to returned vals // 0=x, 1=y, 2=sector
		Position answer = new Position(); // return value
		
		
		if (n < 1 || m < 1)
		{
			// TODO: handle error
		}
		
		// Correct info //
		ans[0] = pos.getX();
		ans[1] = pos.getY();
		ans[2] = getSector(n, m);
		
		//answer.setPosition( pos.getX(), pos.getY() );
		
		randNum = rand.nextInt(3);
		switch (randNum)
		{
			case 0:
				indices[0] = 1;
				indices[1] = 2;
				break;
			case 1:
				indices[0] = 0;
				indices[1] = 2;
				break;
			case 2:
				indices[0] = 0;
				indices[1] = 1;
				break;
			default: break;
		}
		
		ans[randNum] = -1;
		
		lie = indices[randNum = rand.nextInt(2)]; // which value to change
		
		switch(lie)
		{
			case 0:
			case 1:
				
				break;
			case 2:
				
				break;
			default: break;
		}
		
		randNum = rand.nextInt( n - 1 ); // excludes the correct coordinate
		
		// Randomly change 1 of the coordinates to a false coordinate //
		if (rand.nextInt(2) == 1)
		{
			if (randNum >= pos.getY()) // ensures that the correct coordinate is not returned
				++randNum;
			
			answer.setY( randNum );
		}
		else
		{
			if (randNum >= pos.getX())
				++randNum;
				
			answer.setX( randNum );	
		}
		
		return ans;
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