package visual.util;

import java.util.ArrayList;

public class Preferences {

	private final static boolean FULLSCREEN = false;
	private final static String PREF_FILE = "prefs.rdr";
	private static ArrayList<String> servers;
	private static ArrayList<String> users; 
	
	static{
		
		servers = new ArrayList<String>();
		servers.add("localhost");
		servers.add("nonlocalhost");
		
	}
	
	
	public static String[] getIPs(){
		String[] img = new String[servers.size()];
		for (int i = 0; i < servers.size(); i++) img[i] = servers.get(i);
		return img;
	}
	
	public static void addIP(String s){
		servers.add(s);
	}
	
}
