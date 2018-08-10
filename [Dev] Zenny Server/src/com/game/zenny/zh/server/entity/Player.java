package com.game.zenny.zh.server.entity;

import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import com.game.zenny.zh.server.appartment.Appartment;
import com.game.zenny.zh.server.net.server.Server;

public class Player {

	/**
	 * @param playerIdentifier
	 * @return
	 */
	public static String getPlayerUsernameInDB(String playerIdentifier) {
		try {
			ResultSet query = Server.requestDB(
					"SELECT playerUsername FROM players WHERE playerIdentifier = '" + playerIdentifier + "'");
			query.next();
			String username = query.getString("playerUsername");
			query.close();
			return username;
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * @param playerIdentifier
	 * @return
	 */
	public static int getPlayerCreditsInDB(String playerIdentifier) {
		try {
			ResultSet query = Server
					.requestDB("SELECT playerCredits FROM players WHERE playerIdentifier = '" + playerIdentifier + "'");
			query.next();
			int playerCredits = query.getInt("playerCredits");
			query.close();
			return playerCredits;
		} catch (SQLException e) {
			return 0;
		}
	}

	public static String getPlayerSkinURLInDB(String playerIdentifier) {
		try {
			ResultSet query = Server.requestDB(
					"SELECT playerSkinURL FROM players WHERE playerIdentifier = '" + playerIdentifier + "'");
			query.next();
			String username = query.getString("playerSkinURL");
			query.close();
			return username;
		} catch (SQLException e) {
			return null;
		}
	}
	
	//// OBJECT
	// -- USER
	private String playerIdentifier;
	private InetAddress playerAddress;
	private int playerPort;
	private String playerUsername;
	private int playerCredits;
	private String playerSkinURL;
	private Appartment appartment;

	/**
	 * @param playerIdentifier
	 * @param playerAddress
	 * @param playerPort
	 */
	public Player(String playerIdentifier, InetAddress playerAddress, int playerPort) {
		this(playerIdentifier, playerAddress, playerPort, Player.getPlayerUsernameInDB(playerIdentifier),
				Player.getPlayerCreditsInDB(playerIdentifier), Player.getPlayerSkinURLInDB(playerIdentifier), null);
	}

	/**
	 * @param playerIdentifier
	 * @param playerAddress
	 * @param playerPort
	 * @param username
	 */
	public Player(String playerIdentifier, InetAddress playerAddress, int playerPort, String playerUsername,
			int playerCredits, String playerSkinURL, Appartment appartment) {
		this.playerIdentifier = playerIdentifier;
		this.playerAddress = playerAddress;
		this.playerPort = playerPort;
		this.playerUsername = playerUsername;
		this.playerCredits = playerCredits;
		this.playerSkinURL = playerSkinURL;
		this.appartment = appartment;
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject playerJSON = new JSONObject();
		playerJSON.put("playerIdentifier", playerIdentifier);
		playerJSON.put("playerAddress", playerAddress.getHostAddress().toString());
		playerJSON.put("playerPort", playerPort);
		playerJSON.put("playerUsername", playerUsername);
		playerJSON.put("playerCredits", playerCredits);
		playerJSON.put("playerSkinURL", playerSkinURL);

		return playerJSON;
	}

	/**
	 * @return the playerIdentifier
	 */
	public String getPlayerIdentifier() {
		return playerIdentifier;
	}

	/**
	 * @param playerIdentifier
	 *            the playerIdentifier to set
	 */
	public void setPlayerIdentifier(String playerIdentifier) {
		this.playerIdentifier = playerIdentifier;
	}

	/**
	 * @return the playerAddress
	 */
	public InetAddress getPlayerAddress() {
		return playerAddress;
	}

	/**
	 * @param playerAddress
	 *            the playerAddress to set
	 */
	public void setPlayerAddress(InetAddress playerAddress) {
		this.playerAddress = playerAddress;
	}

	/**
	 * @return the playerPort
	 */
	public int getPlayerPort() {
		return playerPort;
	}

	/**
	 * @param playerPort
	 *            the playerPort to set
	 */
	public void setPlayerPort(int playerPort) {
		this.playerPort = playerPort;
	}

	/**
	 * @return the username
	 */
	public String getPlayerUsername() {
		return playerUsername;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setPlayerUsername(String playerUsername) {
		this.playerUsername = playerUsername;
	}

	/**
	 * @return the playerCredits
	 */
	public int getPlayerCredits() {
		return playerCredits;
	}

	/**
	 * @param playerCredits
	 *            the playerCredits to set
	 */
	public void setPlayerCredits(int playerCredits) {
		this.playerCredits = playerCredits;
	}

	/**
	 * @return the appartment
	 */
	public Appartment getAppartment() {
		return appartment;
	}

	/**
	 * @param appartment
	 *            the appartment to set
	 */
	public void setAppartment(Appartment appartment) {
		this.appartment = appartment;
	}

}
