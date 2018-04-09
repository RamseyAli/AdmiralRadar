package game;

import java.io.*;

import net.MyPacketable;
import pref.GamePreferences;

public class GameMap implements Serializable, MyPacketable {
	private static final long serialVersionUID = 1L;

	private boolean[][] asteroid = new boolean[GamePreferences.SEG][GamePreferences.SEG];

	public GameMap() {
		for (int i = 0; i < GamePreferences.SEG; i++)
			for (int j = 0; j < GamePreferences.SEG; j++)
				asteroid[i][j] = false;

		asteroid[1][2] = true;
		asteroid[1][3] = true;
		asteroid[5][4] = true;
		asteroid[5][5] = true;
		asteroid[6][5] = true;

	}

	public int calculateCrash(Spaceship s) {
		Position p = s.getPosition();

		return ( asteroid[p.getX()][p.getY()] ) ? 1 : 0;

	}

	public boolean isAsteroid(int x, int y) {

		return asteroid[x][y];
	}

	public void setIsAsteroid(int x, int y, boolean b) {
		asteroid[x][y] = b;
	}

	public void printAsteroids() {
		for (int i = 0; i < GamePreferences.SEG; i++)
			for (int j = 0; j < GamePreferences.SEG; j++)
				if (isAsteroid( i , j )) System.out.println( "Asteroid at (" + i + "," + j + ")" );
	}
}