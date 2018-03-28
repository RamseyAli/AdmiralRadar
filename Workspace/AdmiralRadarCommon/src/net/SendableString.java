package net;


public class SendableString implements MyPacketable {
	
	private final String content;

	public SendableString(String s) {
		content = s;
	}

	public String getContent() {
		return content;
	}

}
