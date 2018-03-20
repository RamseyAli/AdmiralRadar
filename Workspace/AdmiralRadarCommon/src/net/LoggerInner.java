package net;

import java.io.Serializable;

public class LoggerInner implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password_enc;
	private int success;
	
	public LoggerInner(String u, String p){
		username = u;
		password_enc = p;
		success = -1;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getEncryptedPassword(){
		return password_enc;
	}
	
	public void loginSuccessful(boolean b){
		if (b) success = 1;
		else success = 0;
	}
	
	public int getResult(){
		return success;
	}
}
