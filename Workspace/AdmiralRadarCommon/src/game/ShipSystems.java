package game;

import java.io.*;
import java.util.*;

public class ShipSystems implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Array index constants
	public static final int POWER_LEVEL 	= 0;
	public static final int CHARGED_STATUS 	= 1;
	public static final int SYSTEM_STATUS 	= 2;
	
	// "Boolean" values for charged Status
	public static final int NOT_CHARGED = 3;
	public static final int CHARGED 	= 4;
	
	// "Boolean" value for system Status
	public static final int NOT_DESTROYED = 5;
	public static final int DESTROYED 	  = 6;
	
	// Boolean value for system components
	public static final boolean DISABLED = false;
	public static final boolean ENABLED	 = true;
	
	// Index @ 0: Power level 
	// Index @ 1: (Boolean) Filled or not
	// Index @ 2: (Boolean) Destroyed or not
	int[] sonar 	= 	new int[3];
	int[] missile 	= 	new int[3];
	int[] mine 		= 	new int[3];
	int[] drone 	= 	new int[3];
	int[] silent 	= 	new int[3];
	
	// Refer to Engineer Pane image
	// true: component is enabled
	// false: component is disabled
	boolean[] systemComponents = new boolean[24];

	
	public ShipSystems()
	{
		
		Arrays.fill(sonar, 0);
		Arrays.fill(missile, 0);
		Arrays.fill(mine, 0);
		Arrays.fill(drone, 0);
		Arrays.fill(silent, 0);
		
		Arrays.fill(systemComponents, ENABLED);
	}
	
	private void updateStatuses() {
		/* 
		 * Update CHARGED Status
		 */
		if (sonar[POWER_LEVEL] == 3)	
			sonar[CHARGED_STATUS] = CHARGED;
		else sonar[CHARGED_STATUS] = NOT_CHARGED;
		
		if (missile[POWER_LEVEL] == 3) 	
			missile[CHARGED_STATUS] = CHARGED;
		else missile[CHARGED_STATUS] = NOT_CHARGED;
		
		if (mine[POWER_LEVEL] == 3)		
			mine[CHARGED_STATUS] = CHARGED;
		else mine[CHARGED_STATUS] = NOT_CHARGED;
		
		if (drone[POWER_LEVEL] == 4)	
			drone[CHARGED_STATUS] = CHARGED;
		else drone[CHARGED_STATUS] = NOT_CHARGED;
		
		if (silent[POWER_LEVEL] == 6)	
			silent[CHARGED_STATUS] = CHARGED;
		else silent[CHARGED_STATUS] = NOT_CHARGED;
		
		/* 
		 * Update SYSTEM Status
		 */
		//TODO: using boolean array
	}
	
	// Used by the First Officer to charge up a ship system for later use
	public void chargeSystem(String sys) {
		switch (sys) {
			case "Sonar": 	sonar[POWER_LEVEL] 	+= 1; 	break;
			case "Missile": missile[POWER_LEVEL]+= 1; 	break;
			case "Mine": 	mine[POWER_LEVEL] 	+= 1; 	break;
			case "Drone": 	drone[POWER_LEVEL] 	+= 1; 	break;
			case "Silent": 	silent[POWER_LEVEL] += 1; 	break;
			
			default:
	             throw new IllegalArgumentException
	             (sys + " is an invalid Ship System type...");
		}
		
		updateStatuses();
	}
	
	// Used by the Engineer to disable system temporarily for maintenance
	// Also handle circuits???
	public void disableSystemComponent(String sys) {
		// TODO: Disable system component and check for
		// 		circuit completion
		updateStatuses();
	}
	
	public void printSystems() {
		System.out.println("Sonar at "+		sonar[POWER_LEVEL]);
		System.out.println("Missile at "+	missile[POWER_LEVEL]);
		System.out.println("Mine at "+		mine[POWER_LEVEL]);
		System.out.println("Drone at "+		drone[POWER_LEVEL]);
		System.out.println("Silent at "+	silent[POWER_LEVEL]);
	}
}