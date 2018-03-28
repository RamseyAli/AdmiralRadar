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

	public MyPacketOutputStream(OutputStream out) throws IOException {
		super(out);
		
	}

	public void sendUser(User u) throws IOException {
		writeUnshared(new MyPacket(u));
		flush();
	}
	
	public void sendPosition(Position p) throws IOException {
		writeUnshared(new MyPacket(p));
		flush();
	}
	
	public void sendSpaceShip(Spaceship s) throws IOException {
		writeUnshared(new MyPacket(s));
		flush();
	}
	
	public void sendMap(GameMap m) throws IOException {
		writeUnshared(new MyPacket(m));
		flush();
	}
	
	public void sendString(String s) throws IOException {
		writeUnshared(new MyPacket(new SendableString(s)));
		flush();
	}
	
	public void sendRole(Role r) throws IOException {
		writeUnshared(new MyPacket(r));
		flush();
	}

}
