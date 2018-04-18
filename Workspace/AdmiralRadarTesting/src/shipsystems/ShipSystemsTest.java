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
	
	private static void testDrone() {
		final int lengthX = 20, lengthY = 30;
		final int TEST_SIZE = lengthX * lengthY;
		int i, j, x, y;
		int startX = -5, startY = -5;
		
		Spaceship tempShip;
		
		/*
		ships.get(0).toGameStartCondition(new Position( 0, 0 ));
		ships.get(1).toGameStartCondition(new Position( 6, 7 ));
		ships.get(2).toGameStartCondition(new Position( 4, 2 ));
		ships.get(3).toGameStartCondition(new Position( 11, 11));
		*/
		
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
	
	public static void main(String[] args) throws Exception {
		PrintStream out = new PrintStream(new FileOutputStream("ShipSystemTest.txt"));
		System.setOut(out);
		
		testDrone();
	}

}
