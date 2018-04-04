package game;

import java.awt.Point;
import java.io.*;

import net.MyPacketable;
import util.Preferences;

public class Position implements Serializable , MyPacketable
{

	public int x;
	public int y;

	private static final long serialVersionUID = 1L;
	
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Position()
	{
		x = 0;
		y = 0;
	}
	
	public Position(Point p)
	{
		x = p.x;
		y = p.y;
	}
	
	public Point getPoint(){
		return new Point(x, y);
	}
	
	public void setPosition(int a,int b)
	{
		x = a;
		y = b;
	}
	
	public Position getPosition()
	{
		return this;
	}
	
	public boolean isValid(){
		if ( (x >= 0) && (x < Preferences.SEG) ){
			if ( (y >= 0) && (y < Preferences.SEG) ){
				return true;
			}
		}
		return false;
	}
	
	public boolean isAdjacent(Position p) {
		if ((x + 1 	== p.getX() + 1 && y 	 == p.getY())     ||
			(x + 1 	== p.getX() + 1 && y + 1 == p.getY() + 1) ||
			(x 		== p.getX() 	&& y + 1 == p.getY() + 1) ||
			(x - 1 	== p.getX() - 1 && y + 1 == p.getY() + 1) ||
			(x - 1 	== p.getX() - 1 && y 	 == p.getY())     ||
			(x - 1 	== p.getX() - 1 && y - 1 == p.getY() - 1) ||
			(x 		== p.getX() 	&& y - 1 == p.getY() - 1) ||
			(x + 1 	== p.getX() + 1 && y - 1 == p.getY() - 1)) {
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString(){
		return "(" + x + ":" + y + ")";
	}
}