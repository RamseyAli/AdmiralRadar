package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import game.GameMap;
import game.Role;
import game.Spaceship;
import ops.User;

public class MyPacketInputStream extends ObjectInputStream {

	public MyPacketInputStream(InputStream in) throws IOException {
		super(in);
	}
	
	
	public User getNextUser() throws IOException {
		try {
			return (User) readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
			
		}
	}
	
	public Spaceship getNextSpaceship() throws IOException {
		try {
			return (Spaceship) readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public GameMap getNextMap() throws IOException {
		try {
			return (GameMap) readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getNextString() throws IOException {
		try {
			return (String) readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Role getNextRole() throws IOException {
		try {
			return (Role) readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	
	
}
