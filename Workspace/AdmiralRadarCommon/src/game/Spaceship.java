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
	public boolean dropMine(Position min) {
		if (systems.useSystem(Systems.MINE)) {
			shipMines.addMine(min);
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<Spaceship> blastMine(int index, ArrayList<Spaceship> ships) {
		if (shipMines.isMine(index))
			shipMines.detonateMine(shipMines.getMines().get(index), ships, true);
		
		return ships; // For ship server updates
	}
	
	public boolean hasPlacedMines() {
		return shipMines.getMines().size() > 0;
	}
	
	public ArrayList<Spaceship> launchMissile(Position miss, ArrayList<Spaceship> ships) { // Fix Missile and MINE checks
		if (!systems.useSystem( Systems.MISSILE )) return ships;

		for (Spaceship ship : ships) {
			// If a mine exists in the same position as the launched missile,
			// then detonate the mine but don't damage overlapping and/or adjacent ships
			
			if (miss.equals(ship.getPosition()))
				ship.removeHealth(2);
			if (miss.isAdjacent(ship.getPosition()))
				ship.removeHealth(1);

			ship.getShipMines().detonateMine( miss , ships , false );

		}

		return ships;
	}
	// Drone //
	private int getSector(int n, int m)
	{
		int secSize;
		// Note: "GamePreferences.SEG" is the size of the Map //
		secSize = n / m;
		
		if (n % m != 0) // if n is not a multiple of m, increase sector size by 1 
			++secSize;
				
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
		if (n < 2 || m < 2)
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
	public int[] randomRadar(int n, int m) // n = map dimension, m = sector dimension
	{
		int randNum, lie, correctVal;
		int ans[] = new int[3]; // return value { x, y, sector }
		int indices[] = new int [2]; // 2 indices corresponding to returned vals // 0=x, 1=y, 2=sector
		boolean systemReady;
		
		systemReady = systems.useSystem(Systems.RADAR);
		if (!systemReady)
		{
			// TODO: ship system not ready.
		}
		if (n < 2 || m < 2)
		{
			// TODO: handle error
		}
		
		// Correct info //
		ans[0] = pos.getX();
		ans[1] = pos.getY();
		ans[2] = getSector(n, m);
		
		//answer.setPosition( pos.getX(), pos.getY() );
		
		randNum = rand.nextInt(3); 
		switch (randNum) // which data to leave out
		{
			case 0: // x coordinate
				indices[0] = 1;
				indices[1] = 2;
				break;
				
			case 1: // y coordinate
				indices[0] = 0;
				indices[1] = 2;
				break;
				
			case 2: default: // sector
				indices[0] = 0;
				indices[1] = 1;
				break;
		}
		
		ans[randNum] = -1;
		
		lie = indices[randNum = rand.nextInt(2)]; // which value to change
		
		if (lie < 2) // false coordinate
		{
			if (lie == 0)
				correctVal = pos.getX(); // get true X value
			else
				correctVal = pos.getY();
			
			randNum = rand.nextInt( n - 1 );
		}
		else // Give false Sector
		{
			correctVal = getSector( n, m );
			randNum = rand.nextInt( m * m - 1 );
		}
		
		if (randNum >= correctVal) // excludes the correct coordinate or sector
			++randNum;
		
		ans[lie] = randNum;
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