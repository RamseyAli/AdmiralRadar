package net;

import java.io.Serializable;
import java.util.ArrayList;

import game.Direction;

public class SendablePath implements Serializable, MyPacketable {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= 1L;
	private final ArrayList<Direction>	content;

	public SendablePath(ArrayList<Direction> a) {
		content = a;
	}

	public ArrayList<Direction> getContent() {
		return content;
	}
	
	public String toString(){
		return content.toString();
	}

}