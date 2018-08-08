package com.game.zenny.zh.entity;

import java.net.InetAddress;

public class Player {

	//// OBJECT
	// -- USER
	private String playerIdentifier;
	private InetAddress playerAddress;
	private int playerPort;

	public Player(String playerIdentifier) {
		this(playerIdentifier, null, 0);
	}

	public Player(String playerIdentifier, InetAddress playerAddress, int playerPort) {
		this.playerIdentifier = playerIdentifier;
		this.playerAddress = playerAddress;
		this.playerPort = playerPort;
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

}
