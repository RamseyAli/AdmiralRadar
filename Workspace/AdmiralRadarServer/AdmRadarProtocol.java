
import java.net.*;
import java.io.*;

public class AdmRadarProtocol {
    private static final int WAITING = 0;
    private static final int ACCEPTCOMMAND = 1;
    private static final int EXECUTECOMMAND = 2;

    private static final int NUMCOMMANDS = 0;

    private int state = WAITING;

    private String[] moves = { "North", "South", "East", "West" };
    private String[] activation = { "Sonar",
                                 "Missile",
                                 "Mine",
                                 "Silent" };

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Please wait";
            state = ACCEPTCOMMAND;
        } else if (state == ACCEPTCOMMAND){
            if (theInput.equalsIgnoreCase("NORTH")) {
                theOutput = "Move North";
                state = EXECUTECOMMAND;
            } else if (theInput.equalsIgnoreCase("SOUTH")) {
		theOutput = "Move South";
		state = EXECUTECOMMAND;
	    } else if (theInput.equalsIgnoreCase("EAST")) {
		theOutput = "Move East";
		state = EXECUTECOMMAND;
	    } else if (theInput.equalsIgnoreCase("WEST")) {
		theOutput = "Move West";
		state = EXECUTECOMMAND;
	    } else {
                theOutput = "You're supposed to say \"Direction\"! " +
			    "Try again.";
            }
        } 
        return theOutput;
    }
}