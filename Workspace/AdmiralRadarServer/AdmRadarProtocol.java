
import java.net.*;
import java.io.*;

public class AdmRadarProtocol
{
	private static final int WAITING = 0;
	private static final int ACCEPTCOMMAND = 1;
	private static final int EXECUTECOMMAND = 2;

	private static final int NUMCOMMANDS = 0;

	private int state = WAITING;
	
	Maps map1;
	
	public AdmRadarProtocol()
	{
		map1 = new Maps();
	}
	
	public String processMessages(String theInput)
	{
		String theOutput = null;


		if (state == WAITING)
		{
			theOutput = "Please enter command";
			state = ACCEPTCOMMAND;
		}
		else if (state == ACCEPTCOMMAND)
		{
			if (theInput.equalsIgnoreCase("NORTH"))
			{
				theOutput = "Move North";
				state = WAITING;
			}
			else if (theInput.equalsIgnoreCase("SOUTH"))
			{
				theOutput = "Move South";
				state = WAITING;
			}
			else if (theInput.equalsIgnoreCase("EAST"))
			{
				theOutput = "Move East";
				state = WAITING;
			}
			else if (theInput.equalsIgnoreCase("WEST"))
			{
				theOutput = "Move West";
				state = WAITING;
			}
			else if (theInput.equalsIgnoreCase("exit"))
			{
				theOutput = "Bye,.";
				state = EXECUTECOMMAND;
			}
			else
			{
				theOutput = "You're supposed to say \"Direction\"!\n Try again.";
			}
		}
	
		return theOutput;
	}
	
	public Maps updateMap()
	{
		return map1;
	}
}