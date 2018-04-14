package net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import game.Position;
import game.Direction;
import game.GameMap;
import game.Role;
import game.Spaceship;
import game.Systems;
import ops.User;

public class MyPacketOutputStream extends ObjectOutputStream {

	private boolean debug;
	private String name;

	public MyPacketOutputStream(OutputStream out) throws IOException {
		this( out , false , "" );

	}

	public MyPacketOutputStream(OutputStream out, boolean debug) throws IOException {
		this( out , debug , "" );

	}
	
	public MyPacketOutputStream(OutputStream out, boolean debug, String n) throws IOException {
		super( out );
		this.debug = debug;
		name = n;

	}

	public void sendUser(User u) throws IOException {
		if (debug) System.out.println( "Sending User" );
		writeUnshared( new MyPacket( u ) );
		flush();
	}

	public void sendPosition(Position p) throws IOException {
		if (debug) System.out.println( "Sending Position" );
		writeUnshared( new MyPacket( p ) );
		flush();
	}

	public void sendSpaceShip(Spaceship s) throws IOException {
		if (debug) System.out.println( "Sending Ship" );
		writeUnshared( new MyPacket( s ) );
		flush();
	}

	public void sendMap(GameMap m) throws IOException {
		if (debug) System.out.println( "Sending Map" );
		writeUnshared( new MyPacket( m ) );
		flush();
	}

	public void sendString(String s) throws IOException {
		if (debug) System.out.println( name + " Sending String: " + s);
		writeUnshared( new MyPacket( new SendableString( s ) ) );
		flush();
	}

	public void sendRole(Role r) throws IOException {
		if (debug) System.out.println( "Sending Role" );
		writeUnshared( new MyPacket( r ) );
		flush();
	}

	public void sendDirection(Direction d) throws IOException {
		if (debug) System.out.println( "Sending Direction" + d );
		writeUnshared( new MyPacket( d ) );
		flush();
	}
	
	public void sendSystem(Systems s) throws IOException {
		if (debug) System.out.println( "Sending System" + s );
		writeUnshared( new MyPacket( s ) );
		flush();
	}

	public void sendPath(ArrayList<Direction> p) throws IOException {
		if (debug) System.out.println( "Sending Path" );
		writeUnshared( new MyPacket( new SendablePath( p ) ) );
		flush();
	}

}
