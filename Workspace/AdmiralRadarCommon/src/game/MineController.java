package game;

import java.io.Serializable;
import java.util.ArrayList;

public class MineController implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ArrayList<Position> mines;

    public MineController() {
        mines = new ArrayList<>();
    }
    
    // Add a mine to the board
    public boolean placeMine(Position p) {
    	if (hasMine(p)) {
    		// Maybe send an error message later??
    		return false;
    	}
        mines.add(p);
        return true;
    }
    
    public boolean hasMine(Position p) {
    	for (Position currentMine : mines) {
    		if (currentMine.equals(p)) return true; 
    	}
    	return false;
    }
    
    // Check the current and adjacent locations and remove necessary amounts of health to the Ships
    // if damageShips flag is set to TRUE, else remove the mine from the array
    public static void detonateMine(Position mine, Spaceship[] ships, boolean damageShips) {
        for (Position currentMine : mines) {
            if (currentMine.equals(mine)) {
            	if (damageShips) { 	// exists so that double damage won't be inflicted to ships when launching a missile 
            		for (Spaceship ship : ships) {
                    	// SAME POSITION
                    	if (ship.getPosition().x == currentMine.x && ship.getPosition().y == currentMine.y)
                        	ship.removeHealth(2);
                    	// CORNERS
                    	else if ((ship.getPosition().x + 1 == currentMine.x + 1 && 
                    			ship.getPosition().y == currentMine.y)         		||
                             	(ship.getPosition().x + 1 == currentMine.x + 1 	&& 
                             	ship.getPosition().y + 1 == currentMine.y + 1) 		||
                             	(ship.getPosition().x == currentMine.x 			&& 
                             	ship.getPosition().y + 1 == currentMine.y + 1)  	||
                             	(ship.getPosition().x - 1 == currentMine.x - 1 	&& 
                             	ship.getPosition().y + 1 == currentMine.y + 1) 		||
                             	(ship.getPosition().x - 1 == currentMine.x - 1 	&& 
                             	ship.getPosition().y == currentMine.y)         		||
                             	(ship.getPosition().x - 1 == currentMine.x - 1 	&& 
                             	ship.getPosition().y - 1 == currentMine.y - 1) 		||
                             	(ship.getPosition().x == currentMine.x 			&& 
                             	ship.getPosition().y - 1 == currentMine.y - 1)  	||
                             	(ship.getPosition().x + 1 == currentMine.x + 1 	&& 
                             	ship.getPosition().y - 1 == currentMine.y - 1)) {
                        	ship.removeHealth(1);
                    	}
                	} 
                }
                mines.remove(currentMine);
                return;
            }
        }
    }
}
