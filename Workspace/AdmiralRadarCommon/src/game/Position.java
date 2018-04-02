package game;

import java.awt.Point;
import java.io.*;

import net.MyPacketable;
import util.Preferences;

public class Position implements Serializable , MyPacketable
{

	private int x;
	private int y; 

	private static final long serialVersionUID = 1L;
	
	public Position(int x, int y)
	{
		this.setX(x);
		this.setY(y);
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
		return new Point(getX(), getY());
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
		if ( (getX() >= 0) && (getX() < Preferences.SEG) ){
			if ( (getY() >= 0) && (getY() < Preferences.SEG) ){
				return true;
			}
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
		return "(" + getX() + ":" + getY() + ")";
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}