import java.net.*;
import java.io.*;

public class Position {
	int x;
	int y;
	
	Position()
	{
		x=0;
		y=0;
	}
	
	void setPosition(int a,int b)
	{
		x=a;
		y=b;
	}
	
	Position getPosition()
	{
		return this;
	}
}