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
		writeObject(new MyPacket<User>(u));
		flush();
	}
	
	public void sendPosition(Position p) throws IOException {
		writeObject(new MyPacket<Position>(p));
		flush();
	}
	
	public void sendSpaceShip(Spaceship s) throws IOException {
		writeObject(new MyPacket<Spaceship>(s));
		flush();
	}
	
	public void sendMap(GameMap m) throws IOException {
		writeObject(new MyPacket<GameMap>(m));
		flush();
	}
	
	public void sendString(String s) throws IOException {
		writeObject(new MyPacket<String>(s));
		flush();
	}
	
	public void sendRole(Role r) throws IOException {
		writeObject(new MyPacket<Role>(r));
		flush();
	}

}
