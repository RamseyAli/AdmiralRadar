package net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyPacketOutputStream extends ObjectOutputStream {

	public MyPacketOutputStream(OutputStream out) throws IOException {
		super(out);
		
	}

	public void sendLoggerInner(LoggerInner li) throws IOException {
		writeObject(new MyPacket<LoggerInner>(li));
		flush();
		System.out.println("Sent");
	}

}
