import java.net.*;
import java.io.*;

public class Maps {
	Position [] asteroids;
	Position [] mines;
	
	Maps()
	{
		asteroids[0] = new Position();
		mines[0] = new Position();
	}
	
	int calculateCrash(Spaceship s)
	{
		Position p = s.pos;
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
}