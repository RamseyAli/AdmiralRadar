import java.net.*;
import java.io.*;

public class Position
{
	int x;
	int y;
	
	public Position()
	{
		x=0;
		y=0;
	}
	
	public void setPosition(int a,int b)
	{
		x=a;
		y=b;
	}
	
	public Position getPosition()
	{
		return this;
	}
}