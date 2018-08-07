package com.game.zenny.zh.net;

import java.net.InetAddress;

public class User {

	//// OBJECT
	// -- USER
	private String userIdentifier;
	private InetAddress userAddress;
	private int userPort;

	public User(String userIdentifier) {
		this(userIdentifier, null, 0);
	}

	public User(String userIdentifier, InetAddress userAddress, int userPort) {
		this.userIdentifier = userIdentifier;
		this.userAddress = userAddress;
		this.userPort = userPort;
	}

	/**
	 * @return the userIdentifier
	 */
	public String getUserIdentifier() {
		return userIdentifier;
	}

	/**
	 * @param userIdentifier
	 *            the userIdentifier to set
	 */
	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	/**
	 * @return the userAddress
	 */
	public InetAddress getUserAddress() {
		return userAddress;
	}

	/**
	 * @param userAddress
	 *            the userAddress to set
	 */
	public void setUserAddress(InetAddress userAddress) {
		this.userAddress = userAddress;
	}

	/**
	 * @return the userPort
	 */
	public int getUserPort() {
		return userPort;
	}

	/**
	 * @param userPort
	 *            the userPort to set
	 */
	public void setUserPort(int userPort) {
		this.userPort = userPort;
	}

}
