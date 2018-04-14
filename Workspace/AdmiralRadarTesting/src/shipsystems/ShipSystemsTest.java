package shipsystems;

import java.util.ArrayList;
import game.GameMap;
import game.Position;
import game.Role;
import game.Spaceship;

public class ShipSystemsTest {
	private static ArrayList<Spaceship> ships;
	private static void testDrone1() {
		final int TEST_SIZE = 4;
		int counter;
		Spaceship tempShip;
		
		for (counter = 0; counter < TEST_SIZE; ++counter)
			ships.add( new Spaceship() );
		
		ships.get(0).toGameStartCondition(new Position( 0, 0 ));
		ships.get(1).toGameStartCondition(new Position( 6, 7 ));
		ships.get(2).toGameStartCondition(new Position( 4, 2 ));
		ships.get(3).toGameStartCondition(new Position( 11, 11));
		
		for (counter = 0; counter < TEST_SIZE; ++counter)
		{
			System.out.print("Ship #");
			System.out.print(counter);
			System.out.print(" Position: ");
			System.out.print(ships.get(0).getPosition().getX());
			System.out.print(", ");
			System.out.println(ships.get(0).getPosition().getY());
		}
		
		return;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testDrone1();
	}

}
