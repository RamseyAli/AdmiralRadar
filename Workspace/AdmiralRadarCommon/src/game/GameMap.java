package game;

import java.net.*;
import java.io.*;

public class GameMap implements Serializable
{
	Position [] asteroids = new Position[5];
	
	public GameMap()
	{
		asteroids[0] = new Position();
		asteroids[0].setPosition(1,2);
		asteroids[1] = new Position();
		asteroids[1].setPosition(1,3);
		asteroids[2] = new Position();
		asteroids[2].setPosition(5,4);
		asteroids[3] = new Position();
		asteroids[3].setPosition(5,5);
		asteroids[4] = new Position();
		asteroids[4].setPosition(6,5);
	}
	
	public int calculateCrash(Spaceship s)
	{
		Position p = s.getPosition();
		int i=0;
		while(asteroids[i] != null)
		{
			if(p == asteroids[i])
			{
				return 1;
			}
			i++;
		}
		return 0;
	}
	
	public void printAsteroids()
	{
		for(int i=0;i<5;i++)
		{
			System.out.println("x="+asteroids[i].x+"\ty="+asteroids[i].y);
		}
	}
}