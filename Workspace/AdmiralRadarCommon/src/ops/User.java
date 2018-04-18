package ops;

import java.io.Serializable;

import net.MyPacketable;

public class User implements Serializable, MyPacketable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private String				username;
	private String				password_enc;
	private int					success;
	private int					teamNo;
	
	private int		pin		= -1;
	private int		wins	= -1;
	private int		loss	= -1;
	private String	avatar	= "NONE";

	public User(String u, String p) {
		username = u;
		password_enc = p;
		success = -1;
		teamNo = -2;
	}

	public String getUsername() {
		return username;
	}

	public String getEncryptedPassword() {
		return password_enc;
	}

	public void loginSuccessful(int s) {
		/*
		 * success = 0 : Login successful success = 1 : Login failed Invalid Username success = 2 : Login failed Invalid
		 * Password
		 */
		success = s;
	}

	public int getResult() {
		return success;
	}

	public int getWins() {
		return wins;
	}

	public int getLosses() {
		return loss;
	}

	public String getAvatar() {
		return avatar;
	}

	public int getPin() {
		return pin;
	}

	public void setAvatar(String s) {
		avatar = s;

	}

	public void setLoss(int i) {
		loss = i;

	}

	public void setWins(int i) {
		wins = i;

	}

	public void setNewPassword(String password) {
		password_enc = password;

	}

	public void setPin(int pin) {
		this.pin = pin;
	}
	
	public void printDetails() {
		System.out.println( "Details:" );
		System.out.println( username );
		System.out.println( password_enc );
		System.out.println( pin );
		System.out.println( wins );
		System.out.println( loss );
		System.out.println( avatar );
	}

	public int getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(int teamNo) {
		this.teamNo = teamNo;
	}
}