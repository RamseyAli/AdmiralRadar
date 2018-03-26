package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import game.Position;
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
			buffer = ((MyPacket<?>) readUnshared());
			return buffer.getObject().getClass();
			
		
	}
	
	@SuppressWarnings("unchecked")
	public User getNextUser() throws IOException {
		try {
			if(buffer == null)
				return ((MyPacket<User>) readUnshared()).getObject();
			else
			{
				MyPacket<?> temp = buffer;
				buffer = null;
				return ((MyPacket<User>)temp).getObject();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Spaceship getNextSpaceship() throws IOException {
		try {
			if(buffer == null)
				return ((MyPacket<Spaceship>) readUnshared()).getObject();
			else
			{
				MyPacket<?> temp = buffer;
				buffer = null;
				return ((MyPacket<Spaceship>)temp).getObject();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public GameMap getNextMap() throws IOException {
		try {
			if(buffer == null)
				return ((MyPacket<GameMap>) readUnshared()).getObject();
			else
			{
				MyPacket<?> temp = buffer;
				buffer = null;
				return ((MyPacket<GameMap>)temp).getObject();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Position getNextPosition() throws IOException {
		try {
			if(buffer == null)
				return ((MyPacket<Position>) readUnshared()).getObject();
			else
			{
				MyPacket<?> temp = buffer;
				buffer = null;
				return ((MyPacket<Position>)temp).getObject();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public String getNextString() throws IOException {
		try {
			if(buffer == null)
				return ((MyPacket<String>) readUnshared()).getObject();
			else
			{
				MyPacket<?> temp = buffer;
				buffer = null;
				return ((MyPacket<String>)temp).getObject();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Role getNextRole() throws IOException {
		try {
			if(buffer == null)
				return ((MyPacket<Role>) readObject()).getObject();
			else
			{
				MyPacket<?> temp = buffer;
				buffer = null;
				return ((MyPacket<Role>)temp).getObject();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
