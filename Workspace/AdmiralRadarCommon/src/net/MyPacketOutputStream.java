package net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import game.Position;
import game.GameMap;
import game.Role;
import game.Spaceship;
import ops.User;

public class MyPacketOutputStream extends ObjectOutputStream {

	private static final boolean NET_DEBUG = true;
	
	public MyPacketOutputStream(OutputStream out) throws IOException {
		super(out);
		
	}

	public void sendUser(User u) throws IOException {
		if (NET_DEBUG) System.out.println("Sending User");
		writeUnshared(new MyPacket(u));
		flush();
	}
	
	public void sendPosition(Position p) throws IOException {
		if (NET_DEBUG) System.out.println("Sending Posi");
		writeUnshared(new MyPacket(p));
		flush();
	}
	
	public void sendSpaceShip(Spaceship s) throws IOException {
		if (NET_DEBUG) System.out.println("Sending Ship");
		writeUnshared(new MyPacket(s));
		flush();
	}
	
	public void sendMap(GameMap m) throws IOException {
		if (NET_DEBUG) System.out.println("Sending Map");
		writeUnshared(new MyPacket(m));
		flush();
	}
	
	public void sendString(String s) throws IOException {
		//if (NET_DEBUG) System.out.println("Sending String");
		writeUnshared(new MyPacket(new SendableString(s)));
		flush();
	}
	
	public void sendRole(Role r) throws IOException {
		if (NET_DEBUG) System.out.println("Sending Role");
		writeUnshared(new MyPacket(r));
		flush();
	}

}
