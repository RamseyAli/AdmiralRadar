package game;

import java.io.*;

import net.MyPacketable;

public class Position implements Serializable , MyPacketable
{

	public int x;
	public int y; 

	private static final long serialVersionUID = 1L;
	
	public Position()
	{
		x=0;
		y=0;
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
}