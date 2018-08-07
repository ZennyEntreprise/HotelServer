package com.game.zenny.zh.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map.Entry;

import com.game.zenny.zh.net.logger.LogType;
import com.game.zenny.zh.net.logger.Logger;
import com.game.zenny.zh.net.packet.Packet;
import com.game.zenny.zh.net.packet.UnknownPacket;
import com.game.zenny.zh.net.packet.disconnect.DisconnectPacket;
import com.game.zenny.zh.net.packet.login.ErrorLoginPacket;
import com.game.zenny.zh.net.packet.login.LoginPacket;
import com.game.zenny.zh.net.packet.login.ValidLoginPacket;
import com.game.zenny.zh.net.packet.user.AddUserPacket;
import com.game.zenny.zh.net.packet.user.RemoveUserPacket;

public abstract class Bridge {

	//// STATIC
	public static int defaultPort = 4000;

	//// OBJECT
	// -- BRIDGE
	private String identifier;
	private DatagramSocket socket;
	private Sender sender;
	private Receiver receiver;

	/**
	 * @param socket
	 * @param identifier
	 */
	public Bridge(DatagramSocket socket, String identifier) {
		setIdentifier(identifier);

		this.socket = socket;

		Logger.log(this, LogType.INFO, "Adding default packets...");
		registerPackets();
		Logger.log(this, LogType.INFO, Packet.getPackets().size() + " default packets added : ");

		for (Entry<Long, Class<?>> packet : Packet.getPackets().entrySet())
			Logger.log(this, LogType.INFO, " - " + packet.getValue().getSimpleName());

		sender = new Sender(this);
		receiver = new Receiver(this);
		receiver.start();
	}

	/**
	 * @return identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the socket
	 */
	public DatagramSocket getSocket() {
		return socket;
	}

	/**
	 * @param socket
	 *            the socket to set
	 */
	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}

	/**
	 * @return the sender
	 */
	public Sender getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(Sender sender) {
		this.sender = sender;
	}

	/**
	 * @return the receiver
	 */
	public Receiver getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver
	 *            the receiver to set
	 */
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	// -- PACKET

	private void registerPackets() {
		Packet.registerPacket(0, UnknownPacket.class);
		Packet.registerPacket(1, LoginPacket.class);
		Packet.registerPacket(2, ValidLoginPacket.class);
		Packet.registerPacket(3, ErrorLoginPacket.class);
		Packet.registerPacket(4, DisconnectPacket.class);
		Packet.registerPacket(5, AddUserPacket.class);
		Packet.registerPacket(6, RemoveUserPacket.class);
	}

	/**
	 * @param packet
	 * @param fromUser
	 */
	public abstract void packetAction(Packet packet, User fromUser);

	// -- USER
	private ArrayList<User> users = new ArrayList<User>();

	/**
	 * @return user list
	 */
	public ArrayList<User> getUsers() {
		return users;
	}

	/**
	 * @param user
	 */
	public void addUser(User user) {
		users.add(user);
	}

	/**
	 * @param identifier
	 */
	public void addUser(String identifier) {
		users.add(new User(identifier));
	}

	/**
	 * @param userIdentifier
	 * @param userAddress
	 * @param userPort
	 */
	public void addUser(String userIdentifier, InetAddress userAddress, int userPort) {
		users.add(new User(userIdentifier, userAddress, userPort));
	}

	/**
	 * @param user
	 * @return true if the user is contained in user list
	 */
	public boolean containsUser(User user) {
		return users.contains(user);
	}

	/**
	 * @param userIdentifier
	 * @return true if the user list contains user with this userIdentifier
	 */
	public boolean containsUser(String userIdentifier) {
		for (User user : users)
			if (user.getUserIdentifier().equals(userIdentifier))
				return true;
		return false;
	}

	/**
	 * @param userAddress
	 * @param userPort
	 * @return true if the user list contains user with this userAddress and
	 *         this userPort
	 */
	public boolean containsUser(InetAddress userAddress, int userPort) {
		try {
			for (User user : users)
				if (user.getUserAddress().toString().equals(userAddress.toString()) && user.getUserPort() == userPort)
					return true;
		} catch (NullPointerException e) {
			return false;
		}
		return false;
	}

	/**
	 * @param userIdentifier
	 * @param userAddress
	 * @param userPort
	 * @return true if the user list contains user with this userIdentifier,
	 *         this userAddress and this userPort
	 */
	public boolean containsUser(String userIdentifier, InetAddress userAddress, int userPort) {
		return containsUser(userIdentifier) && containsUser(userAddress, userPort);
	}

	/**
	 * @param userIdentifier
	 * @return the user where his userIdentifier is the same
	 */
	public User getUser(String userIdentifier) {
		for (User user : users)
			if (user.getUserIdentifier().equals(userIdentifier))
				return user;
		return null;
	}

	/**
	 * @param userAddress
	 * @param userPort
	 * @return the user where his userAddress and his userPort are the same
	 */
	public User getUser(InetAddress userAddress, int userPort) {
		for (User user : users)
			if (user.getUserAddress().toString().equals(userAddress.toString()) && user.getUserPort() == userPort)
				return user;
		return null;
	}

	/**
	 * @param userIdentifier
	 * @param userAddress
	 * @param userPort
	 * @return the user where his userIdentifier, his userAddress and his
	 *         userPort are the same
	 */
	public User getUser(String userIdentifier, InetAddress userAddress, int userPort) {
		for (User user : users)
			if (user.getUserIdentifier().equals(userIdentifier) && user.getUserAddress().toString().equals(userAddress.toString())
					&& user.getUserPort() == userPort)
				return user;
		return null;
	}

	/**
	 * @param user
	 */
	public void removeUser(User user) {
		users.remove(user);
	}

	/**
	 * @param identifier
	 */
	public void removeUser(String identifier) {
		for (User user : users)
			if (user.getUserIdentifier().equals(identifier))
				users.remove(user);
	}

	/**
	 * @param userAddress
	 * @param userPort
	 */
	public void removeUser(InetAddress userAddress, int userPort) {
		for (User user : users)
			if (user.getUserAddress().toString().equals(userAddress.toString()) && user.getUserPort() == userPort)
				users.remove(user);
	}

	/**
	 * @param userIdentifier
	 * @param userAddress
	 * @param userPort
	 */
	public void removeUser(String userIdentifier, InetAddress userAddress, int userPort) {
		for (User user : users)
			if (user.getUserIdentifier().equals(identifier) && user.getUserAddress().toString().equals(userAddress.toString())
					&& user.getUserPort() == userPort)
				users.remove(user);
	}
}
