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
                    if (ship.pos.x == currentMine.x && ship.pos.y == currentMine.y)
                        ship.health -= 2;
                    // CORNERS
                    else if ((ship.pos.x + 1 == currentMine.x + 1 && ship.pos.y == currentMine.y)         ||
                             (ship.pos.x + 1 == currentMine.x + 1 && ship.pos.y + 1 == currentMine.y + 1) ||
                             (ship.pos.x == currentMine.x && ship.pos.y + 1 == currentMine.y + 1)         ||
                             (ship.pos.x - 1 == currentMine.x - 1 && ship.pos.y + 1 == currentMine.y + 1) ||
                             (ship.pos.x - 1 == currentMine.x - 1 && ship.pos.y == currentMine.y)         ||
                             (ship.pos.x - 1 == currentMine.x - 1 && ship.pos.y - 1 == currentMine.y - 1) ||
                             (ship.pos.x == currentMine.x && ship.pos.y - 1 == currentMine.y - 1)         ||
                             (ship.pos.x + 1 == currentMine.x + 1 && ship.pos.y - 1 == currentMine.y - 1)) {
                        ship.health -= 1;
                    }
                }
                return;
            }
        }
    }
}
