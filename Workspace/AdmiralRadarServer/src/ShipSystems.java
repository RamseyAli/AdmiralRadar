import java.net.*;
import java.io.*;
import java.util.*;

public class ShipSystems implements Serializable
{
	int[] sonar = new int[2];
	int[] missile = new int[2];
	int[] mine = new int[2];
	int[] drone = new int[2];
	int[] silent = new int[2];
	
	public ShipSystems()
	{
		Arrays.fill(sonar,0);
		Arrays.fill(missile,0);
		Arrays.fill(mine,0);
		Arrays.fill(drone,0);
		Arrays.fill(silent,0);
	}
	
	public void checkActive()
	{
		if(sonar[0]==3)
		{
			sonar[1] = 1;
		}
		
		if(missile[0]==3)
		{
			missile[1] = 1;
		}
		
		if(mine[0]==3)
		{
			mine[1] = 1;
		}
		
		if(drone[0]==3)
		{
			drone[1] = 1;
		}
		
		if(silent[0]==3)
		{
			silent[1] = 1;
		}
	}
}