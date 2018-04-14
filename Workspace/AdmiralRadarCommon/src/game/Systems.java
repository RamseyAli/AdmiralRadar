package game;

import java.io.Serializable;

import net.MyPacketable;

public enum Systems implements Serializable, MyPacketable {
		
	MINE, MISSILE, DRONE, RADAR, BOOST, SCENARIO; // Not using or checking for this atm
	
	private String payload;

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
}
