package net;

import java.io.Serializable;

public class SendableString implements Serializable , MyPacketable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String content;

	public SendableString(String s) {
		content = s;
	}

	public String getContent() {
		return content;
	}

}
