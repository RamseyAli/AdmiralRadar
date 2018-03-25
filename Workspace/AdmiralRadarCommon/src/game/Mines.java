package game;

import java.util.ArrayList;

public class Mines {
    ArrayList<Position> mines;

    public Mines() {
        mines = new ArrayList<>();
    }
    // Add a mine to the
    public void addMine(Position p) {
        mines.add(p);
    }
    // Check the current and surrounding locations and take away necessary amounts of damage to the Ships
    public void damageShips(Position mine, Spaceship[] ships) { // FIRST FOR-LOOP MAY BE UNNECESSARY
        for (Position currentMine : mines) {
            if (currentMine.equals(mine)) {
                for (Spaceship ship : ships) {
                    // SAME POSITION
                    if (ship.getPosition().x == currentMine.x && ship.getPosition().y == currentMine.y)
                        ship.removeHealth(2);
                    // CORNERS
                    else if ((ship.getPosition().x + 1 == currentMine.x + 1 && ship.getPosition().y == currentMine.y)         ||
                             (ship.getPosition().x + 1 == currentMine.x + 1 && ship.getPosition().y + 1 == currentMine.y + 1) ||
                             (ship.getPosition().x == currentMine.x && ship.getPosition().y + 1 == currentMine.y + 1)         ||
                             (ship.getPosition().x - 1 == currentMine.x - 1 && ship.getPosition().y + 1 == currentMine.y + 1) ||
                             (ship.getPosition().x - 1 == currentMine.x - 1 && ship.getPosition().y == currentMine.y)         ||
                             (ship.getPosition().x - 1 == currentMine.x - 1 && ship.getPosition().y - 1 == currentMine.y - 1) ||
                             (ship.getPosition().x == currentMine.x && ship.getPosition().y - 1 == currentMine.y - 1)         ||
                             (ship.getPosition().x + 1 == currentMine.x + 1 && ship.getPosition().y - 1 == currentMine.y - 1)) {
                        ship.removeHealth(1);
                    }
                }
                mines.remove(currentMine);
                return;
            }
        }
    }
}
