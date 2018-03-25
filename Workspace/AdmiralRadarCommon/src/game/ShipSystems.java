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
	boolean[] sysComponents = new boolean[24];

	
	public ShipSystems() {
		sonar[POWER_LEVEL] 		= 0;
		sonar[CHARGED_STATUS] 	= NOT_CHARGED;
		sonar[SYSTEM_STATUS] 	= NOT_DESTROYED;
		
		missile[POWER_LEVEL] 	= 0;
		missile[CHARGED_STATUS] = NOT_CHARGED;
		missile[SYSTEM_STATUS] 	= NOT_DESTROYED;
		
		mine[POWER_LEVEL] 		= 0;
		mine[CHARGED_STATUS] 	= NOT_CHARGED;
		mine[SYSTEM_STATUS] 	= NOT_DESTROYED;
		
		drone[POWER_LEVEL] 		= 0;
		drone[CHARGED_STATUS] 	= NOT_CHARGED;
		drone[SYSTEM_STATUS] 	= NOT_DESTROYED;
		
		silent[POWER_LEVEL] 	= 0;
		silent[CHARGED_STATUS] 	= NOT_CHARGED;
		silent[SYSTEM_STATUS] 	= NOT_DESTROYED;
		
		Arrays.fill(sysComponents, ENABLED);
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
		if (sysComponents[0] == DISABLED || sysComponents[7] == DISABLED ||
				sysComponents[10] == DISABLED || sysComponents[14] == DISABLED ||
				sysComponents[15] == DISABLED || sysComponents[20] == DISABLED) {
			mine[SYSTEM_STATUS] = DESTROYED;
			missile[SYSTEM_STATUS] = DESTROYED;
		}
		if (sysComponents[2] == DISABLED || sysComponents[3] == DISABLED ||
				sysComponents[9] == DISABLED || sysComponents[12] == DISABLED ||
				sysComponents[18] == DISABLED || sysComponents[22] == DISABLED) {
			drone[SYSTEM_STATUS] = DESTROYED;
			sonar[SYSTEM_STATUS] = DESTROYED;
		}
		
		if (sysComponents[1] == DISABLED || sysComponents[6] == DISABLED ||
				sysComponents[8] == DISABLED || sysComponents[13] == DISABLED ||
				sysComponents[17] == DISABLED || sysComponents[19] == DISABLED) {
			silent[SYSTEM_STATUS] = DESTROYED;
			// scenario may not be implemented yet
		}
	}
	
	// power-level and charged to 0
	public void useSystem(String sys) {
		if (sys.equals("Sonar") && sonar[CHARGED_STATUS] == CHARGED &&
				sonar[SYSTEM_STATUS] == NOT_DESTROYED) {
			sonar[POWER_LEVEL] = 0;
			sonar[CHARGED_STATUS] = NOT_CHARGED;
			return;
		}
		if (sys.equals("Missile") && missile[CHARGED_STATUS] == CHARGED &&
				missile[SYSTEM_STATUS] == NOT_DESTROYED) {
			missile[POWER_LEVEL] = 0;
			missile[CHARGED_STATUS] = NOT_CHARGED;
			return;
		}
		if (sys.equals("Mine") && mine[CHARGED_STATUS] == CHARGED &&
				mine[SYSTEM_STATUS] == NOT_DESTROYED) {
			mine[POWER_LEVEL] = 0;
			mine[CHARGED_STATUS] = NOT_CHARGED;
			return;
		}
		if (sys.equals("Drone") && drone[CHARGED_STATUS] == CHARGED &&
				drone[SYSTEM_STATUS] == NOT_DESTROYED) {
			drone[POWER_LEVEL] = 0;
			drone[CHARGED_STATUS] = NOT_CHARGED;
			return;
		}
		if (sys.equals("Silent") && silent[CHARGED_STATUS] == CHARGED &&
				silent[SYSTEM_STATUS] == NOT_DESTROYED) {
			silent[POWER_LEVEL] = 0;
			silent[CHARGED_STATUS] = NOT_CHARGED;
			return;
		}
		
		throw new IllegalArgumentException(sys + " is an invalid Ship System type:"
         		+ "(Sonar, Missile, Mine, Drone, Silent)...");
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
	             (sys + " is an invalid Ship System type:"
	             		+ "(Sonar, Missile, Mine, Drone, Silent)...");
		}
		
		updateStatuses();
	}
	
	// Used by the Engineer to disable system temporarily for maintenance
	// HANDLE REACTOR COMPONENTS LATER
	public void disableSystemComponent(int comp) {
		if (sysComponents[comp] == ENABLED)
			sysComponents[comp] = DISABLED;
		else {
			throw new IllegalArgumentException
            	(comp + " is an invalid Ship System component:"
            			+ "(0-23)...");
		}
		// check circuit completion
		if (sysComponents[2] == DISABLED && sysComponents[1] == DISABLED &&
				sysComponents[0] == DISABLED && sysComponents[20] == DISABLED) {
			sysComponents[2] 	= ENABLED;
			sysComponents[1] 	= ENABLED;
			sysComponents[0] 	= ENABLED;
			sysComponents[20] 	= ENABLED;
		
		}
		if (sysComponents[8] == DISABLED && sysComponents[7] == DISABLED &&
				sysComponents[6] == DISABLED && sysComponents[18] == DISABLED) {
			sysComponents[8] 	= ENABLED;
			sysComponents[7] 	= ENABLED;
			sysComponents[6] 	= ENABLED;
			sysComponents[18] 	= ENABLED;
		
		}
		if (sysComponents[12] == DISABLED && sysComponents[13] == DISABLED &&
				sysComponents[14] == DISABLED && sysComponents[19] == DISABLED) {
			sysComponents[12] 	= ENABLED;
			sysComponents[13] 	= ENABLED;
			sysComponents[14] 	= ENABLED;
			sysComponents[19] 	= ENABLED;
		
		}
		
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