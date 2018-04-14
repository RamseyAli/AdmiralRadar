package game;

import java.io.Serializable;
import java.util.ArrayList;

public class MineController implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private ArrayList<Position>	mines;

	public MineController() {
		mines = new ArrayList<>();
	}

	// Add a mine to the board
	public boolean addMine(Position p) {
		if (isPlacedMine( p )) {
			// Maybe send an error message later??
			return false;
		}
		mines.add( p );
		return true;
	}
	
	public boolean isPlacedMine(Position p) {
		for (Position currentMine : mines) {
			if (currentMine.equals( p )) return true;
		}
		return false;
	}
	
	public boolean isMine(int index) {
		return index >= 0 && index < mines.size();
	}

	// Check the current and adjacent locations and remove necessary amounts of health to the Ships
	// if damageShips flag is set to TRUE, else remove the mine from the array
	public void detonateMine(Position mine, ArrayList<Spaceship> ships, boolean damageShips) {
		for (Position currentMine : mines) {
			if (currentMine.equals( mine )) {
				if (damageShips) { // exists so that double damage won't be inflicted to ships when launching a missile
					for (Spaceship ship : ships) {
						// SAME POSITION
						if (ship.equals( mine )) ship.removeHealth( 2 );
						// CORNERS
						if (mine.isAdjacent( ship.getPosition() )) ship.removeHealth( 1 );
					}
				}
				mines.remove( currentMine );
				return;
			}
		}
	}

	public ArrayList<Position> getMines() {
		return this.mines;
	}
}
