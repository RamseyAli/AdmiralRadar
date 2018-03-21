package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import game.GameMap;
import game.Role;
import game.Spaceship;
import ops.User;

public class MyPacketInputStream extends ObjectInputStream {

	MyPacket<?> buffer;
	public MyPacketInputStream(InputStream in) throws IOException {
		super(in);
	}
	
	
	public Class<?> getClassOfNext() throws ClassNotFoundException, IOException{
			buffer = ((MyPacket<?>) readObject());
			return buffer.getClass();
			
		
	}
	
	@SuppressWarnings("unchecked")
	public User getNextUser() throws IOException {
		try {
			return ((MyPacket<User>) readObject()).getObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Spaceship getNextSpaceship() throws IOException {
		try {
			return ((MyPacket<Spaceship>) readObject()).getObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public GameMap getNextMap() throws IOException {
		try {
			return ((MyPacket<GameMap>) readObject()).getObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public String getNextString() throws IOException {
		try {
			return ((MyPacket<String>) readObject()).getObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Role getNextRole() throws IOException {
		try {
			return ((MyPacket<Role>) readObject()).getObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
