package pref;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GamePreferences {

	public static final String RESOURCES_PATH;
	public static final int SEG = 15;
	public static final int SEC = 3;

	public static boolean FULLSCREEN = true;
	// public final static String PREF_FILE = "prefs.rdr";
	private final static int			PORT	= 2069;
	private static ArrayList<String>	servers;

	static {

		servers = new ArrayList<String>();
		servers.add( "127.0.0.1" );
		servers.add( "nonlocalhost" );
		
		RESOURCES_PATH = getResourcePath();
		System.out.println( RESOURCES_PATH );

	}

	public static ArrayList<String> getIPArrayList() {

		return servers;
	}

	private static String getResourcePath() {
		try {
			ClassLoader loader = GamePreferences.class.getClassLoader();
			File f = null;
			f = Paths.get(new URL(loader.getResource("pref/GamePreferences.class").toString().replaceAll(" ","%20")).toURI()).toFile();
			f = f.getParentFile().getParentFile().getParentFile().getParentFile();
			return ("file://" + f.getAbsolutePath() + System.getProperty( "file.separator" ) + "Resources" + System.getProperty( "file.separator" )).replaceAll(" ","%20");
		}
		catch (URISyntaxException | MalformedURLException e) {
			e.printStackTrace();
			System.out.println( "FAILED TO INSTANTIATE FILE SYSTEM. SHUTTING DOWN." );
			System.exit( 0 );
			return null;
		}
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
