package pref;

import java.util.ArrayList;

public class GamePreferences {

	public static int SEG = 15;

	public static boolean FULLSCREEN = true;
	// public final static String PREF_FILE = "prefs.rdr";
	private final static int			PORT	= 2069;
	private static ArrayList<String>	servers;
	// private static ArrayList<String> users;

	static {

		servers = new ArrayList<String>();
		servers.add( "127.0.0.1" );
		servers.add( "nonlocalhost" );

	}

	public static ArrayList<String> getIPArrayList() {

		return servers;
	}

	public static String[] getIPs() {
		String[] img = new String[servers.size()];
		for (int i = 0; i < servers.size(); i++)
			img[i] = servers.get( i );
		return img;
	}

	public static void addIP(String s) {
		servers.add( s );
	}

	public static void clearIPs() {
		servers.clear();

	}

	public static int getPort() {

		return PORT;
	}

	public static void setFullscreen(boolean b) {
		FULLSCREEN = b;
	}

	public static boolean isFullscreen() {
		return FULLSCREEN;
	}

}
