package shipsystems;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

import game.GameMap;
import game.Position;
import game.Role;
import game.Spaceship;
import game.Systems;

import static pref.GamePreferences.*;

@SuppressWarnings("unused")
public class ShipSystemsTest {
	private static Spaceship sensorShip;
	private static ArrayList<Spaceship> ships = new ArrayList<Spaceship>();
	private static final boolean FISHER = false;
	
	private static void printPos(int index)	{
		System.out.print("Ship #");
		System.out.print(index);
		System.out.print(" Position: ");
		System.out.print(ships.get(index).getPosition().getX());
		System.out.print(", ");
		System.out.println(ships.get(index).getPosition().getY());
	}
	private static void printDrone(int index, int guess) {
		System.out.print("    Guess: ");
		System.out.print(guess);
		System.out.print(" Verdict: ");
		try 
		{
			sensorShip.getShipSystem().chargeSystem(Systems.DRONE);
			sensorShip.getShipSystem().chargeSystem(Systems.DRONE);
			if (sensorShip.checkSector(ships.get(index), guess, SEG, SEC))
				System.out.print("Yes");
			else
				System.out.print("No");
		}
		catch (Exception ex)
		{
			System.out.print("Exception -> ");
			System.out.print(ex.getMessage());
		}
		
		System.out.println();
	}	
	private static void testDrone() {
		final int LENGTHX = 15, LENGTHY = 15;
		final int TEST_SIZE = LENGTHX * LENGTHY;
		final int STARTX = 0, STARTY = 0;
		
		int i, j, x, y;
		Spaceship tempShip;
		
		ships.clear();
		
		for (i = 0; i < TEST_SIZE; ++i)
		{
			ships.add( new Spaceship() );
			ships.get(i).toGameStartCondition(new Position(STARTX + i % LENGTHX, STARTY + i / LENGTHX));
		}
		
		// Shuffle //
		if (FISHER)
			Collections.shuffle(ships);
		
		for (i = 0; i < TEST_SIZE; ++i)
		{
			printPos(i);
			
			for (j = 0; j < SEC * SEC; ++j)
				printDrone(i, j);
			
			System.out.println();
		}
	}
	
	private static void printTrueInfo(int index) {
		System.out.print("Ship #");
		System.out.println(index);
		System.out.print("    X: ");
		System.out.println(ships.get(index).getPosition().getX());
		System.out.print("    Y: ");
		System.out.println(ships.get(index).getPosition().getY());
		System.out.print("    Sector: ");
		System.out.println(ships.get(index).getSector(SEG, SEC));
	}
	private static boolean matchRadar(int index) {
		int i, ommissions = 0, truths = 0, lies = 0;
		int radarInfo[];
		int trueInfo[] = new int[3];
		
		trueInfo[0] = ships.get(index).getPosition().getX();
		trueInfo[1] = ships.get(index).getPosition().getY();
		trueInfo[2] = ships.get(index).getSector(SEG, SEC);
		
		try 
		{
			sensorShip.getShipSystem().chargeSystem(Systems.RADAR);
			sensorShip.getShipSystem().chargeSystem(Systems.RADAR);
			sensorShip.getShipSystem().chargeSystem(Systems.RADAR);
			radarInfo = sensorShip.randomRadar(ships.get(index), SEG, SEC);
		}
		catch (RuntimeException ex)
		{
			System.out.print("Exception -> ");
			System.out.println(ex.getMessage());
			return false;
		}
		
		
		System.out.println("Radar Gives");
		
		System.out.print("    X: ");
		if (radarInfo[0] > -1)
			System.out.println(radarInfo[0]);
		else
			System.out.println("Ommitted");
		
		System.out.print("    Y: ");
		if (radarInfo[1] > -1)
			System.out.println(radarInfo[1]);
		else
			System.out.println("Ommitted");
		
		System.out.print("    Sector: ");
		if (radarInfo[2] > -1)
			System.out.println(radarInfo[2]);
		else
			System.out.println("Ommitted");
		
		for (i = 0; i < 3; ++i)
		{
			if (radarInfo[i] < 0) 
				++ommissions;
			else if (radarInfo[i] == trueInfo[i])
				++truths;
			else
				++lies;
		}
		
		if (ommissions == 1 && truths == 1 && lies == 1)
			return true;
		
		return false;
	}
	private static void testRadar() {
		final int LENGTHX = 15, LENGTHY = 15;
		final int TEST_SIZE = LENGTHX * LENGTHY;
		final int STARTX = 0, STARTY = 0;
		
		int i, j, totalSuccess = 0;
		
		ships.clear();
		
		for (i = 0; i < TEST_SIZE; ++i)
		{
			ships.add( new Spaceship() );
			ships.get(i).toGameStartCondition(new Position(STARTX + i % LENGTHX, STARTY + i / LENGTHX));
		}
		
		if (FISHER)
			Collections.shuffle(ships);
		
		for (i = 0; i < TEST_SIZE; ++i)
		{
			printTrueInfo(i);
			if (matchRadar(i))
			{
				++totalSuccess;
				System.out.println("Test Passed");
			}
			else
				System.out.println("Test Failed");
			
			System.out.println();
		}
		
		System.out.print("Passed ");
		System.out.print(totalSuccess);
		System.out.print(" out of ");
		System.out.println(TEST_SIZE);
	}
	
	public static void main(String[] args) throws Exception {
		sensorShip = new Spaceship();
		sensorShip.toGameStartCondition(new Position(0, 0));
		
		PrintStream stdout = System.out;
		PrintStream out;
		
		out = new PrintStream(new FileOutputStream("RadarTest.txt"));
		System.setOut(out);
		
		testRadar();
		
		out = new PrintStream(new FileOutputStream("DroneTest.txt"));
		System.setOut(out);
		
		testDrone();
		
	}

}
