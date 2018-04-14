package logic;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import net.MyPacketInputStream;
import net.MyPacketOutputStream;
import net.ObjEnum;
import ops.User;
import static pref.GamePreferences.*;

import static database.dbQuery.*;

import java.util.Scanner;
import java.util.ArrayList;

import game.Direction;
import game.GameMap;
import game.Position;
import game.Role;
import game.Spaceship;

import helpers.AdmRadarProtocol;

public class AdmRadarServer {
	ArrayList<Spaceship>			gameShip;
	static int						nPlayers;
	static boolean					gameOngoing;
	static boolean[]				moveComplete	= new boolean[2];
	static int						turnMiss;
	ServerSocket					serverSocket;
	static int						turn;

	public class ClientHandler implements Runnable {
		Socket					sock;
		MyPacketOutputStream	mpos;
		MyPacketInputStream		mpis;
		int						teamNo;
		int						turnNo;
		GameMap					map;
		Role					role;
		Spaceship				ship;

		public ClientHandler(Socket clientSock) {
			try {
				teamNo = -1;
				turnNo = -1;
				sock = clientSock;
				mpos = new MyPacketOutputStream( sock.getOutputStream() );
				mpis = new MyPacketInputStream( sock.getInputStream() );
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public boolean processSpecialAction(String action) {
			if (action.equalsIgnoreCase( "Spacewalk" )) {
				int teamRealNo = teamNo + 1;
				sendGlobalMessage("SERVER", "Team "+teamRealNo+" conducting spacewalk");
				gameShip.get( teamNo ).restoreSystems();
				turnMiss = 3;
				if(turn == 0) {
					turn = 4;
				} else {
					turn = 0;
				}
				return true;
			} else if (action.contains( "Drone" )) {
				String args [] = action.split(" ");
				
				int sectorGuess = Integer.parseInt(args[1]);
				boolean result;
				
				if (teamNo == 1) {
					sendGlobalMessage("SERVER","Drone activated by team 2");
					result = gameShip.get(0).checkSector(sectorGuess, SEG, SEC);
				} else {
					sendGlobalMessage("SERVER","Drone activated by team 1");
					result = gameShip.get(1).checkSector(sectorGuess, SEG, SEC);
				}
				
				if (result) {
					sendTeamMessage("SERVER","Opponent ship located in Sector "+sectorGuess,teamNo);
					return true;
				}
				else {
					sendTeamMessage("SEVER","Opponent ship not in Sector "+sectorGuess,teamNo);
					return false;
				}
			} else if (action.contains( "Sonar" )) {
				int targetTeam;
				Spaceship targetShip;

				return true; // placeholder return

			} else return false;
		}

		public void run() {
			try {
				while (true) {
					Object inputObject;
					if (( inputObject = mpis.getNextUser() ) != null) {
						myPrint( "I Have A User" );
						User u = (User) inputObject;
						String username = u.getUsername();
						String encPassword = u.getEncryptedPassword();
						
						boolean aloha = false; // To be removed in the end
						if(username.equalsIgnoreCase( "alohomora" )) {	// To be removed in the end
							aloha = true;	// To be removed in the end
						}	// To be removed in the end
						
						if(!aloha) {	// To be removed in the end
							aloha = userExists(username);
						}	// To be removed in the end
						
						if (aloha) {					// To be removed in the end	
							int success;
							
							if (username.equalsIgnoreCase( "alohomora" ))	// To be removed in the end
								success = 0;	// To be removed in the end
							else success = login( username , encPassword );
							
							u.loginSuccessful( success );
							
							if (success == 0) {
								if (username.equalsIgnoreCase( "alohomora" )) {
									u.setWins( 1 );
									u.setLoss( 0 );
									u.setAvatar( "http://www.withanaccent.com/wp-content/uploads/2012/07/avatar-aang.jpg" );
								} else {
									u.setWins( getWins( username ) );
									u.setLoss( getLosses( username ) );
									u.setAvatar( getURL( username ) );
									u.setPin( getUserPIN(username) );
								}
								
								mpos.sendUser( u );
								
								while (true) {
									ObjEnum temp = mpis.getClassOfNext();
									if (temp == ObjEnum.USER) {
										inputObject = mpis.getNextUser();
										u = (User) inputObject;
										resetPW( u.getUsername() , u.getEncryptedPassword() , u.getPin() );
										setURL( u.getUsername() , u.getAvatar() );
										mpos.sendUser( u );
									} else if (temp == ObjEnum.STRING) {
										inputObject = mpis.getNextString();
										if (nPlayers == 0) {
											myPrint( "GAME LOBBY" );
											myPrint( "Error: Not enough players" );
											myPrint( "Game Mode: Turn Based" );
										}
										
										teamNo = nPlayers / 4;
										turnNo = nPlayers;
										myPrint( "team no: " + teamNo + " turn no: " + turnNo );
										nPlayers++;
										
										while (nPlayers < 8) {
											Thread.sleep( 1 );
											// mpos.sendString("WAITING");
										}
										
										AdmRadarProtocol arp = new AdmRadarProtocol();
										
										map = new GameMap();
										map = arp.updateMap();
										mpos.sendMap( map );
										
										myPrint( teamNo + "-" + turnNo );
										
										if (turnNo == 7) {
											myPrint( "GAME BEGINS" );
											gameOngoing = true;
										}
										
										if (turnNo == 0 || turnNo == 4) {
											role = Role.CAPTAIN;
											mpos.sendRole( role );
											mpos.sendString( "Enter initial location" );
											Position pos = mpis.getNextPosition();
											System.out.println( turnNo + "Initial Position Received" );
											ship = gameShip.get( teamNo );
											ship.setPos( pos );
											gameShip.set( teamNo , ship );
											// gameShip.get(teamNo).printShip();
										} else if (turnNo == 1 || turnNo == 5) {
											role = Role.FIRST;
											mpos.sendRole( role );
										} else if (turnNo == 2 || turnNo == 6) {
											role = Role.ENGINE;
											mpos.sendRole( role );
										} else if (turnNo == 3 || turnNo == 7) {
											role = Role.RADIO;
											mpos.sendRole( role );
										}
										
										myPrint( teamNo + ":" + turnNo );
										
										ship = gameShip.get( teamNo );
										mpos.sendSpaceShip( ship );
										mpos.reset();
	
										while (true) {
											if (role == Role.RADIO) {
	
												if (teamNo == 0) {
													if (gameShip.get( 1 ) != null) {
														mpos.sendPath( gameShip.get( 1 ).getPath() );
													} else {
														mpos.sendString( "Game ended" );
														break;
													}
													
													/*
													 * while(!moveComplete[1]) { // Do nothing System.out.print(""); }
													 */
												} else {
													if (gameShip.get( 0 ) != null) {
														mpos.sendPath( gameShip.get( 0 ).getPath() );
													} else {
														mpos.sendString( "Game ended" );
														break;
													}
													/*
													 * while(!moveComplete[0]) { // Do nothing System.out.print(""); }
													 */
												}
											} else {
												
												if (turn == turnNo) {
													//myPrint( "" + turn );
													
													if (turnNo == 0 || turnNo == 4) {
														moveComplete[teamNo] = false;
													}
													
													ship = gameShip.get( teamNo );
													// ship.printShip();
													
													if (ship != null && ( turnNo == 1 || turnNo == 2 || turnNo == 5 || turnNo == 6 )) {
														mpos.sendDirection( ship.getDirection() );
													} else {
														mpos.sendString( "Your turn" );
													}
													
													if(role == Role.CAPTAIN) { 
														String temp1 = "Do you want to do any special action?";
														mpos.sendString(temp1);
														temp1 = mpis.getNextString();
														if(!temp1.equalsIgnoreCase("No")); {
															if(processSpecialAction(temp1)) { 
																temp1 += " successful";
																//mpos.sendString(temp1); 
															} else {
																temp1 += " unsuccessful";
																//mpos.sendString(temp1);
															} 
														}
														
														Direction dir = mpis.getNextDirection();
														ship = arp.processDirections(dir, ship);
														gameShip.set(teamNo, ship);
													} else {
														String action = mpis.getNextString();
														ship = arp.processCommands( action , ship );
														gameShip.set(teamNo, ship);
													}
													
													if (role == Role.ENGINE) {
														if (gameShip.get( teamNo ) == null) {
															gameOngoing = false;
															myPrint( "GAME ENDED" );
														}
														moveComplete[teamNo] = true;
													}
													
													turn++;
													if (turn == 3) {
														if(turnMiss == 0) {
															turn++;
														} else {
															turnMiss--;
															turn = 0;
														}
													} else if (turn == 7) {
														if(turnMiss == 0) {
															turn = 0;
														} else {
															turnMiss--;
															turn = 4;
														}
													}
													
													while (!moveComplete[teamNo]) {
														// Do nothing
														Thread.sleep(1);
														// System.out.print("");
													}
													
													ship = gameShip.get( teamNo );
													// ship.printShip();
													
													if (ship == null) {
														Spaceship emptyShip = new Spaceship();
														mpos.sendSpaceShip( emptyShip );
														mpos.reset();
														break;
													} else {
														mpos.sendSpaceShip( ship );
														mpos.reset();
													}
												} else if (!gameOngoing) {
														gameShip.set( teamNo , null );
														mpos.sendString( "Game Ended" );
														break;
												} else {
														Thread.sleep( 1 );
														// System.out.print("");
														// mpos.sendString("Waiting for turn");
												}
											}
										}
									} else {
										mpos.sendString( "Naughty" );
									}
								}
							} else {
								mpos.sendUser( u );
							}
						} else {
							String avatar = u.getAvatar();
							int pin = createUser(username,encPassword,avatar);
							u.setPin(pin);
							mpos.sendUser(u);
						}
					}
				}
			}
			catch (Exception ex) {
				ex.printStackTrace( System.err );
				if (sock != null && !sock.isClosed()) {
					try {
						sock.close();
					}
					catch (IOException e) {
						e.printStackTrace( System.err );
					}
				}
			}
		}
		
		public ClientHandler() {
			
		}
	}

	public static void main(String[] args) throws IOException {
		
		moveComplete[0] = false;
		moveComplete[1] = false;
		turnMiss = 0;
		gameOngoing = false;
		turn = 0;
		nPlayers = 0;
		new AdmRadarServer().go( getPort() );

	}

	public AdmRadarServer() {

		moveComplete[0] = false;
		moveComplete[1] = false;
		gameOngoing = false;
		turn = 0;
		nPlayers = 0;
		go( getPort() );
	}

	public void go(int port) {
		gameShip = new ArrayList<Spaceship>();
		Spaceship initial1 = new Spaceship();
		gameShip.add( 0 , initial1 );
		Spaceship initial2 = new Spaceship();
		gameShip.add( 1 , initial2 );

		try {
			serverSocket = new ServerSocket( port );
			myPrint( "AdmiralRaderServer running on port: " + port );
			while (true) {
				Socket clientSocket = serverSocket.accept();
				myPrint( "Got a client" );
				Thread t = new Thread( new ClientHandler( clientSocket ) );
				t.start();
			}
		}
		catch (Exception e) {
			myPrint( "Exception caught when trying to listen on port " + port + " or listening for a connection" );
			myPrint( e.getMessage() );
			if (serverSocket != null && !serverSocket.isClosed()) {
				try {
					serverSocket.close();
				}
				catch (IOException ex) {
					ex.printStackTrace( System.err );
				}
			}
		}
	}

	public boolean testLogin() {
		// Database test

		String username = "TEST_USER";
		String password = "password";
		myPrint( "Logging in with... Username: TEST_USER | Password: TEST_PASSWORD" );
		int result = login( username , password );
		if (result == 0) {
			myPrint( "Welcome " + username + "!" );
			int wins = getWins( username );
			int losses = getLosses( username );

			if (wins != -1 && losses != -1) {
				myPrint( "Your stats are " + wins + " Win(s) and " + losses + " Loss(es)." );
			} else {
				myPrint( "ERROR: Stats not loaded properly" );
			}

		} else {
			if (result == 1) {
				myPrint( "ERROR: Login Failed - Invalid username" );
			} else {
				myPrint( "ERROR: Login Failed - Invalid password" );
			}
		}
		myPrint( "What would you like the new password to be?" );
		Scanner reader = new Scanner( System.in );
		String new_pw = reader.nextLine();
		myPrint( "What is your PIN?" );
		int pin = reader.nextInt();
		result = resetPW( username , new_pw , pin );
		reader.close();
		if (result == 0) {
			myPrint( "Password changed successfully!" );
			return true;
		} else if (result == 1) {
			myPrint( "ERROR: Reset Failed - Invalid Username" );
			return false;
		} else {
			myPrint( "ERROR: Reset Failed - Invalid PIN" );
			return false;
		}
	}

	public static void myPrint(String s) {
		System.out.println( "SERVER: " + s );
	}

}