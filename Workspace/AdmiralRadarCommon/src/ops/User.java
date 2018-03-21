package ops;

import java.io.Serializable;

public class User implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password_enc;
	private int success;
	
	private int wins = -1;
	private int loss = -1;
	private String avatar = "NONE";
	
	public User(String u, String p){
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
	
	public int getWins(){		
		return wins;
	}
	
	public int getLosses(){		
		return loss;
	}
	
	public String getAvatar(){
		return avatar;
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
}
