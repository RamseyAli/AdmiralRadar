package game;

import java.io.*;
import java.util.*;

public class ShipSystems implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Array index constants
	public static final int	POWER_LEVEL		= 0;
	public static final int	CHARGED_STATUS	= 1;
	public static final int	SYSTEM_STATUS	= 2;

	// "Boolean" values for charged Status
	public static final int	NOT_CHARGED	= 0;
	public static final int	CHARGED		= 1;

	// "Boolean" values for system Status
	public static final int	NOT_DESTROYED	= 0;
	public static final int	DESTROYED		= 1;

	// Boolean values for system components
	public static final boolean	DISABLED	= false;
	public static final boolean	ENABLED		= true;

	// Index @ 0: Power level
	// Index @ 1: (Boolean) Filled or not
	// Index @ 2: (Boolean) Destroyed or not
	private int[]	missile	= new int[3];	// Tactical Systems
	private int[]	mine	= new int[3];

	private int[]	radar	= new int[3];	// Sensory Systems
	private int[]	drone	= new int[3];

	private int[] 	boost 	= new int[3]; 	// Auxilary System

	// Refer to Engineer Pane image
	// true: component is enabled
	// false: component is disabled
	private boolean[] sysComponents = new boolean[24];

	public ShipSystems() {
		missile[POWER_LEVEL] = 0;
		missile[CHARGED_STATUS] = NOT_CHARGED;
		missile[SYSTEM_STATUS] = NOT_DESTROYED;

		mine[POWER_LEVEL] = 0;
		mine[CHARGED_STATUS] = NOT_CHARGED;
		mine[SYSTEM_STATUS] = NOT_DESTROYED;

		radar[POWER_LEVEL] = 0;
		radar[CHARGED_STATUS] = NOT_CHARGED;
		radar[SYSTEM_STATUS] = NOT_DESTROYED;

		drone[POWER_LEVEL] = 0;
		drone[CHARGED_STATUS] = NOT_CHARGED;
		drone[SYSTEM_STATUS] = NOT_DESTROYED;

		boost[POWER_LEVEL] = 0;
		boost[CHARGED_STATUS] = NOT_CHARGED;
		boost[SYSTEM_STATUS] = NOT_DESTROYED;

		Arrays.fill( sysComponents , ENABLED );
	}

	private void updateStatuses() {
		/*
		 * Update CHARGED Status
		 */
		if (radar[POWER_LEVEL] == 3)
			radar[CHARGED_STATUS] = CHARGED;
		else radar[CHARGED_STATUS] = NOT_CHARGED;

		if (missile[POWER_LEVEL] == 3)
			missile[CHARGED_STATUS] = CHARGED;
		else missile[CHARGED_STATUS] = NOT_CHARGED;

		if (mine[POWER_LEVEL] == 3)
			mine[CHARGED_STATUS] = CHARGED;
		else mine[CHARGED_STATUS] = NOT_CHARGED;

		if (drone[POWER_LEVEL] == 4)
			drone[CHARGED_STATUS] = CHARGED;
		else drone[CHARGED_STATUS] = NOT_CHARGED;

		if (boost[POWER_LEVEL] == 6)
			boost[CHARGED_STATUS] = CHARGED;
		else boost[CHARGED_STATUS] = NOT_CHARGED;

		/*
		 * Update SYSTEM Status
		 */
		if (sysComponents[0] == DISABLED || sysComponents[7] == DISABLED || sysComponents[10] == DISABLED
				|| sysComponents[14] == DISABLED || sysComponents[15] == DISABLED || sysComponents[20] == DISABLED) {
			// Disable Tactical Systems
			mine[SYSTEM_STATUS] = DESTROYED;
			missile[SYSTEM_STATUS] = DESTROYED;
		} else {
			mine[SYSTEM_STATUS] = NOT_DESTROYED;
			missile[SYSTEM_STATUS] = NOT_DESTROYED;
		}

		if (sysComponents[2] == DISABLED || sysComponents[3] == DISABLED || sysComponents[9] == DISABLED
				|| sysComponents[12] == DISABLED || sysComponents[18] == DISABLED || sysComponents[22] == DISABLED) {
			// Disable Sensory Systems
			drone[SYSTEM_STATUS] = DESTROYED;
			radar[SYSTEM_STATUS] = DESTROYED;
		} else {
			drone[SYSTEM_STATUS] = NOT_DESTROYED;
			radar[SYSTEM_STATUS] = NOT_DESTROYED;
		}

		if (sysComponents[1] == DISABLED || sysComponents[6] == DISABLED || sysComponents[8] == DISABLED
				|| sysComponents[13] == DISABLED || sysComponents[17] == DISABLED || sysComponents[19] == DISABLED) {
			// Disable Auxilary System(s)
			boost[SYSTEM_STATUS] = DESTROYED;
		} else {
			boost[SYSTEM_STATUS] = NOT_DESTROYED;
		}

	}
	
	public boolean enableSystems()
	{
		radar[SYSTEM_STATUS] = NOT_CHARGED;
		missile[SYSTEM_STATUS] = NOT_CHARGED;
		mine[SYSTEM_STATUS] = NOT_CHARGED;
		drone[SYSTEM_STATUS] = NOT_CHARGED;
		boost[SYSTEM_STATUS] = NOT_CHARGED;
		return true;
	}
	
	// power-level and charged to 0
	public boolean useSystem(Systems sys) {
		if (sys == Systems.RADAR && isSystemCharged(sys) && !isSystemDestroyed(sys)) {
			radar[POWER_LEVEL] = 0;
			radar[CHARGED_STATUS] = NOT_CHARGED;
			return true;
		} else if (sys == Systems.MISSILE && isSystemCharged(sys) && !isSystemDestroyed(sys)) {
			missile[POWER_LEVEL] = 0;
			missile[CHARGED_STATUS] = NOT_CHARGED;
			return true;
		} else if (sys == Systems.MINE && isSystemCharged(sys) && !isSystemDestroyed(sys)) {
			mine[POWER_LEVEL] = 0;
			mine[CHARGED_STATUS] = NOT_CHARGED;
			return true;
		} else if (sys == Systems.DRONE && isSystemCharged(sys) && !isSystemDestroyed(sys)) {
			drone[POWER_LEVEL] = 0;
			drone[CHARGED_STATUS] = NOT_CHARGED;
			return true;
		} else if (sys == Systems.BOOST && isSystemCharged(sys) && !isSystemDestroyed(sys)) {
			boost[POWER_LEVEL] = 0;
			boost[CHARGED_STATUS] = NOT_CHARGED;
			return true;
		}

		return false;
		// throw new IllegalArgumentException(sys + " is an invalid Ship System type:"
		// + "(Radar, Missile, Mine, Drone, Boost)...");
	}

	// Used by the First Officer to charge up a ship system for later use
	public void chargeSystem(Systems sys) {
		switch (sys) {
			case RADAR: {
				if (radar[POWER_LEVEL] != 3) radar[POWER_LEVEL]++;
				break;
			}
			case MISSILE: {
				if (missile[POWER_LEVEL] != 3) missile[POWER_LEVEL]++;
				break;
			}
			case MINE: {
				if (mine[POWER_LEVEL] != 3) mine[POWER_LEVEL]++;
				break;
			}
			case DRONE: {
				if (drone[POWER_LEVEL] != 4) drone[POWER_LEVEL]++;
				break;
			}
			case BOOST: {
				if (boost[POWER_LEVEL] != 6) boost[POWER_LEVEL]++;
				break;
			}

			default:
				/*
				 * throw new IllegalArgumentException (sys + " is an invalid Ship System type:" +
				 * "(Radar, Missile, Mine, Drone, Boost)...");
				 */
				return;
		}

		updateStatuses();
	}

	// Used by the Engineer to disable system temporarily for maintenance
	// HANDLE REACTOR COMPONENTS LATER
	public void disableSystemComponent(int comp) {
		if (sysComponents[comp] == ENABLED)
			sysComponents[comp] = DISABLED;
		else if (comp < 0 || comp >= 24 || sysComponents[comp] == DISABLED) {
			/*
			 * throw new IllegalArgumentException (comp + " is an invalid Ship System component:" + "(0-23)...");
			 */
			return;
		}
		// check circuit completion
		if (sysComponents[2] == DISABLED && sysComponents[1] == DISABLED && sysComponents[0] == DISABLED
				&& sysComponents[20] == DISABLED) {
			sysComponents[2] = ENABLED;
			sysComponents[1] = ENABLED;
			sysComponents[0] = ENABLED;
			sysComponents[20] = ENABLED;

		}
		if (sysComponents[8] == DISABLED && sysComponents[7] == DISABLED && sysComponents[6] == DISABLED
				&& sysComponents[18] == DISABLED) {
			sysComponents[8] = ENABLED;
			sysComponents[7] = ENABLED;
			sysComponents[6] = ENABLED;
			sysComponents[18] = ENABLED;

		}
		if (sysComponents[12] == DISABLED && sysComponents[13] == DISABLED && sysComponents[14] == DISABLED
				&& sysComponents[19] == DISABLED) {
			sysComponents[12] = ENABLED;
			sysComponents[13] = ENABLED;
			sysComponents[14] = ENABLED;
			sysComponents[19] = ENABLED;

		}

		updateStatuses();
	}

	public int getPowerLevel(Systems sys) {
		switch (sys) {
			case RADAR:
				return radar[POWER_LEVEL];
			case MISSILE:
				return missile[POWER_LEVEL];
			case MINE:
				return mine[POWER_LEVEL];
			case DRONE:
				return drone[POWER_LEVEL];
			case BOOST:
				return boost[POWER_LEVEL];

			default:
				/*
				 * throw new IllegalArgumentException (sys + " is an invalid Ship System type:" +
				 * "(Radar, Missile, Mine, Drone, Boost)...");
				 */
				return -1;
		}
	}
	
	public void setPowerLevel(Systems sys, int level) {
		switch (sys) { // If power level ever equals -1, then the level arg is WRONG
			case RADAR:
				radar[POWER_LEVEL] = ((level <= 3 && level >= 0) ? level : -1);
			case MISSILE:
				radar[POWER_LEVEL] = ((level <= 3 && level >= 0) ? level : -1);
			case MINE:
				radar[POWER_LEVEL] = ((level <= 3 && level >= 0) ? level : -1);
			case DRONE:
				radar[POWER_LEVEL] = ((level <= 4 && level >= 0) ? level : -1);
			case BOOST:
				radar[POWER_LEVEL] = ((level <= 6 && level >= 0) ? level : -1);

			default:
				/*
				 * throw new IllegalArgumentException (sys + " is an invalid Ship System type:" +
				 * "(Radar, Missile, Mine, Drone, Boost)...");
				 */
				return;
		}
	}

	public boolean isSystemCharged(Systems sys) {
		switch (sys) {
			case RADAR:
				return radar[CHARGED_STATUS] == CHARGED;
			case MISSILE:
				return missile[CHARGED_STATUS] == CHARGED;
			case MINE:
				return mine[CHARGED_STATUS] == CHARGED;
			case DRONE:
				return drone[CHARGED_STATUS] == CHARGED;
			case BOOST:
				return boost[CHARGED_STATUS] == CHARGED;

			default:
				/*
				 * throw new IllegalArgumentException (sys + " is an invalid Ship System type:" +
				 * "(Radar, Missile, Mine, Drone, Boost)...");
				 */
				return false;
		}
	}

	public boolean isSystemDestroyed(Systems sys) {
		switch (sys) {
			case RADAR:
				return radar[SYSTEM_STATUS] == DESTROYED;
			case MISSILE:
				return missile[SYSTEM_STATUS] == DESTROYED;
			case MINE:
				return mine[SYSTEM_STATUS] == DESTROYED;
			case DRONE:
				return drone[SYSTEM_STATUS] == DESTROYED;
			case BOOST:
				return boost[SYSTEM_STATUS] == DESTROYED;

			default:
				/*
				 * throw new IllegalArgumentException (sys + " is an invalid Ship System type:" +
				 * "(Radar, Missile, Mine, Drone, Boost)...");
				 */
				return false;
		}
	}

	public boolean isCompEnabled(int comp) {
		return sysComponents[comp] == ENABLED;
	}

	public void setChargedStatus(Systems sys, int status) {
		switch (sys) {
			case RADAR:
				radar[CHARGED_STATUS] = status;
			case MISSILE:
				missile[CHARGED_STATUS] = status;
			case MINE:
				mine[CHARGED_STATUS] = status;
			case DRONE:
				drone[CHARGED_STATUS] = status;
			case BOOST:
				boost[CHARGED_STATUS] = status;

			default:
				/*
				 * throw new IllegalArgumentException (sys + " is an invalid Ship System type:" +
				 * "(Radar, Missile, Mine, Drone, Boost)...");
				 */
				return;
		}
	}

	public void setSystemStatus(Systems sys, int status) {
		switch (sys) {
			case RADAR:
				radar[SYSTEM_STATUS] = status;
			case MISSILE:
				missile[SYSTEM_STATUS] = status;
			case MINE:
				mine[SYSTEM_STATUS] = status;
			case DRONE:
				drone[SYSTEM_STATUS] = status;
			case BOOST:
				boost[SYSTEM_STATUS] = status;

			default:
				/*
				 * throw new IllegalArgumentException (sys + " is an invalid Ship System type:" +
				 * "(Radar, Missile, Mine, Drone, Boost)...");
				 */
				return;
		}
	}

	public void printSystems() // For Testing purposes
	{
		System.out.println( "------------------ TACTICAL SHIP SYSTEMS -------------------" );
		System.out.print( "Current Missile power level: 	" + missile[POWER_LEVEL] + ", System Status: " );
		if (isSystemDestroyed( Systems.MISSILE ))
			System.out.printf( "DISABLED\n" );
		else System.out.printf( "ENABLED\n" );

		System.out.print( "Current Mine power level: 	" + mine[POWER_LEVEL] + ", System Status: " );
		if (isSystemDestroyed( Systems.MINE ))
			System.out.printf( "DISABLED\n\n" );
		else System.out.printf( "ENABLED\n\n" );

		System.out.println( "------------------ SENSORY SHIP SYSTEMS -------------------" );
		System.out.print( "Current Radar power level: 	" + radar[POWER_LEVEL] + ", System Status: " );
		if (isSystemDestroyed( Systems.RADAR ))
			System.out.printf( "DISABLED\n" );
		else System.out.printf( "ENABLED\n" );

		System.out.print( "Current Drone power level: 	" + drone[POWER_LEVEL] + ", System Status: " );
		if (isSystemDestroyed( Systems.DRONE ))
			System.out.printf( "DISABLED\n\n" );
		else System.out.printf( "ENABLED\n\n" );

		System.out.println( "------------------ AUXILARY SHIP SYSTEMS -------------------" );
		System.out.print( "Current Boost power level: 	" + boost[POWER_LEVEL] + ", System Status: " );
		if (isSystemDestroyed( Systems.BOOST ))
			System.out.printf( "DISABLED\n\n" );
		else System.out.printf( "ENABLED\n\n" );
	}

	public static void main(String[] args) {
		boolean test16 = true;

		Spaceship ship1 = new Spaceship();
		Spaceship ship2 = new Spaceship();
		Spaceship ship3 = new Spaceship();
		Spaceship ship4 = new Spaceship();
		Spaceship ship5 = new Spaceship();

		if (test16) {
			/*
			 * User Story #16
			 */

			// Tactical system repair test
			System.out.println( "*********************************************** "
					+ "\nUSER STORY 16 TEST #1 (TACTICAL SYSTEM DISABLEMENT)\n"
					+ "***********************************************\n" );
			System.out.printf( ">>>>>>>>>>	Ship1 ShipSystems upon creation:\n\n" );
			ship1.getShipSystem().printSystems();
			System.out.printf( "\n>>>>>>>>>> Disabling Tactical Component #0... \n\n" );
			ship1.getShipSystem().disableSystemComponent( 0 );

			System.out.printf( ">>>>>>>>>> Ship1 ShipSystems after disabling 1 Tactical component:\n\n" );
			ship1.getShipSystem().printSystems();

			System.out.printf( ">>>>>>>>>> Disabling components in circuit containing Tactical Component #0: \n\n" );
			ship1.getShipSystem().disableSystemComponent( 2 );
			ship1.getShipSystem().disableSystemComponent( 1 );
			ship1.getShipSystem().disableSystemComponent( 20 );

			System.out.printf( ">>>>>>>>>> Ship1 ShipSystems after re-enabling Comp #0:\n\n" );
			ship1.getShipSystem().printSystems();

			// Sensory system repair test
			System.out.println( "*********************************************** "
					+ "\nUSER STORY 16 TEST #2 (SENSORY SYSTEM DISABLEMENT)\n"
					+ "***********************************************\n" );
			System.out.printf( ">>>>>>>>>>	Ship2 ShipSystems upon creation:\n\n" );
			ship2.getShipSystem().printSystems();
			System.out.printf( "\n>>>>>>>>>> Disabling Sensory Component #12... \n\n" );
			ship2.getShipSystem().disableSystemComponent( 12 );

			System.out.printf( ">>>>>>>>>> Ship2 ShipSystems after disabling 1 Sensory component:\n\n" );
			ship2.getShipSystem().printSystems();

			System.out.printf( ">>>>>>>>>> Disabling components in circuit containing Sensory Component #12: \n\n" );
			ship2.getShipSystem().disableSystemComponent( 13 );
			ship2.getShipSystem().disableSystemComponent( 14 );
			ship2.getShipSystem().disableSystemComponent( 19 );

			System.out.printf( ">>>>>>>>>> Ship2 ShipSystems after re-enabling Comp #12:\n\n" );
			ship2.getShipSystem().printSystems();

			// Auxilary system repair test
			System.out.println( "*********************************************** "
					+ "\nUSER STORY 16 TEST #3 (SENSORY SYSTEM DISABLEMENT)\n"
					+ "***********************************************\n" );
			System.out.printf( ">>>>>>>>>>	Ship3 ShipSystems upon creation:\n\n" );
			ship3.getShipSystem().printSystems();
			System.out.printf( "\n>>>>>>>>>> Disabling Auxilary Component #6... \n\n" );
			ship3.getShipSystem().disableSystemComponent( 6 );

			System.out.printf( ">>>>>>>>>> Ship3 ShipSystems after disabling 1 Auxilary component:\n\n" );
			ship3.getShipSystem().printSystems();

			System.out.printf( ">>>>>>>>>> Disabling components in circuit containing Auxilary Component #6: \n\n" );
			ship3.getShipSystem().disableSystemComponent( 7 );
			ship3.getShipSystem().disableSystemComponent( 8 );
			ship3.getShipSystem().disableSystemComponent( 18 );

			System.out.printf( ">>>>>>>>>> Ship3 ShipSystems after re-enabling Comp #6:\n\n" );
			ship3.getShipSystem().printSystems();

			// Random system repair test
			System.out.println( "*********************************************** "
					+ "\nUSER STORY 16 TEST #4 (RANDOM DISABLEMENT)\n"
					+ "***********************************************\n" );
			System.out.printf( ">>>>>>>>>>	Ship4 ShipSystems upon creation:\n\n" );
			ship4.getShipSystem().printSystems();
			System.out.printf( "\n>>>>>>>>>> Disabling Auxilary Component #19... \n\n" );
			ship4.getShipSystem().disableSystemComponent( 19 );
			System.out.printf( "\n>>>>>>>>>> Disabling Tactical Component #20... \n\n" );
			ship4.getShipSystem().disableSystemComponent( 20 );
			System.out.printf( "\n>>>>>>>>>> Disabling Sensory Component #18... \n\n" );
			ship4.getShipSystem().disableSystemComponent( 18 );

			System.out.printf( ">>>>>>>>>> Ship4 ShipSystems after disabling components:\n\n" );
			ship4.getShipSystem().printSystems();

			// Random system repair test
			System.out.println( "*********************************************** "
					+ "\nUSER STORY 16 TEST #5 (NON-CIRCUIT DISABLEMENT)\n"
					+ "***********************************************\n" );
			System.out.printf( ">>>>>>>>>>	Ship5 ShipSystems upon creation:\n\n" );
			ship5.getShipSystem().printSystems();
			System.out.printf( "\n>>>>>>>>>> Disabling Sensory Component #2... \n\n" );
			ship5.getShipSystem().disableSystemComponent( 2 );
			System.out.printf( "\n>>>>>>>>>> Disabling Auxilary Component #1... \n\n" );
			ship5.getShipSystem().disableSystemComponent( 1 );
			System.out.printf( "\n>>>>>>>>>> Disabling Auxilary (NON-CIRCUIT) Component #17... \n\n" );
			ship5.getShipSystem().disableSystemComponent( 17 );

			System.out.printf( ">>>>>>>>>> Ship5 ShipSystems after disabling components:\n\n" );
			ship5.getShipSystem().printSystems();

			System.out.printf( ">>>>>>>>>> Disabling components in circuit containing components #1, #2: \n\n" );
			ship5.getShipSystem().disableSystemComponent( 0 );
			ship5.getShipSystem().disableSystemComponent( 20 );

			System.out.printf( ">>>>>>>>>> Ship5 ShipSystems after repairing curcuit:\n\n" );
			ship5.getShipSystem().printSystems();

			return;
		}

	}
}