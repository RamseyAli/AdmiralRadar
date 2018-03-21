package net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import ops.User;

public class MyPacketOutputStream extends ObjectOutputStream {

	public MyPacketOutputStream(OutputStream out) throws IOException {
		super(out);
		
	}

	public void sendUser(User u) throws IOException {
		writeObject(new MyPacket<User>(u));
		flush();
		System.out.println("Sent");
	}

}
