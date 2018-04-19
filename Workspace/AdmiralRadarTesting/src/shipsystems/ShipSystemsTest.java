package shipsystems;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import game.GameMap;
import game.Position;
import game.Role;
import game.Spaceship;
import static pref.GamePreferences.*;

@SuppressWarnings("unused")
public class ShipSystemsTest {
	private static ArrayList<Spaceship> ships = new ArrayList<Spaceship>();
	
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
			if (ships.get(index).checkSector(guess, SEG, SEC))
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
	private static void printRadar(int index) {
		int radarInfo[];
		
		radarInfo = ships.get(index).randomRadar(SEG, SEC);
		
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
	}
	
	private static void testDrone() {
		final int lengthX = 20, lengthY = 30;
		final int TEST_SIZE = lengthX * lengthY;
		final int startX = -5, startY = -5;
		
		int i, j, x, y;
		
		Spaceship tempShip;
		
		for (i = 0; i < TEST_SIZE; ++i)
		{
			ships.add( new Spaceship() );
			ships.get(i).toGameStartCondition(new Position(startX + i % lengthX, startY + i / lengthX));
		}
		
		for (i = 0; i < TEST_SIZE; ++i)
		{
			printPos(i);
			
			for (j = 0; j < SEC * SEC; ++j)
				printDrone(i, j);
			
			System.out.println();
		}
	}
	
	private static void testRadar() {
		final int lengthX = 15, lengthY = 15;
		final int TEST_SIZE = lengthX * lengthY;
		final int startX = 0, startY = 0;
		
		int i, j;
		
		for (i = 0; i < TEST_SIZE; ++i)
		{
			ships.add( new Spaceship() );
			ships.get(i).toGameStartCondition(new Position(startX + i % lengthX, startY + i / lengthX));
		}
		
		for (i = 0; i < TEST_SIZE; ++i)
		{
			printTrueInfo(i);
			printRadar(i);
			System.out.println();
		}
		
		
		
	}
	
	public static void main(String[] args) throws Exception {
		PrintStream stdout = System.out;
		
		testRadar();
		
		PrintStream out = new PrintStream(new FileOutputStream("DroneTest.txt"));
		System.setOut(out);
		
		testDrone();
		
	}

}
