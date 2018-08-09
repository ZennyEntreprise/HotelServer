package com.game.zenny.zh.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map.Entry;

import com.game.zenny.zh.entity.Player;
import com.game.zenny.zh.logger.LogType;
import com.game.zenny.zh.logger.Logger;
import com.game.zenny.zh.net.packet.Packet;
import com.game.zenny.zh.net.packet.UnknownPacket;
import com.game.zenny.zh.net.packet.disconnect.DisconnectPacket;
import com.game.zenny.zh.net.packet.login.ErrorLoginPacket;
import com.game.zenny.zh.net.packet.login.LoginPacket;
import com.game.zenny.zh.net.packet.login.ValidLoginPacket;

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
	}

	/**
	 * @param packet
	 * @param fromUser
	 */
	public abstract void packetAction(Packet packet, Player fromUser);

	// -- USER
	private ArrayList<Player> allPlayers = new ArrayList<Player>();

	/**
	 * @return player list
	 */
	public ArrayList<Player> getPlayers() {
		return allPlayers;
	}

	/**
	 * @param player
	 */
	public void addPlayer(Player player) {
		allPlayers.add(player);
	}

	/**
	 * @param playerIdentifier
	 * @param playerAddress
	 * @param playerPort
	 */
	public void addPlayer(String playerIdentifier, InetAddress playerAddress, int playerPort) {
		allPlayers.add(new Player(playerIdentifier, playerAddress, playerPort));
	}
	
	/**
	 * @param playerIdentifier
	 * @param playerAddress
	 * @param playerPort
	 * @param username
	 * @param credits
	 */
	public void addPlayer(String playerIdentifier, InetAddress playerAddress, int playerPort, String username, int credits) {
		allPlayers.add(new Player(playerIdentifier, playerAddress, playerPort, username, credits));
	}

	/**
	 * @param player
	 * @return true if the player is contained in player list
	 */
	public boolean containsPlayer(Player player) {
		return allPlayers.contains(player);
	}

	/**
	 * @param playerIdentifier
	 * @return true if the player list contains player with this playerIdentifier
	 */
	public boolean containsPlayer(String playerIdentifier) {
		for (Player player : allPlayers)
			if (player.getPlayerIdentifier().equals(playerIdentifier))
				return true;
		return false;
	}

	/**
	 * @param playerAddress
	 * @param playerPort
	 * @return true if the player list contains player with this playerAddress and
	 *         this playerPort
	 */
	public boolean containsPlayer(InetAddress playerAddress, int playerPort) {
		try {
			for (Player player : allPlayers)
				if (player.getPlayerAddress().toString().equals(playerAddress.toString()) && player.getPlayerPort() == playerPort)
					return true;
		} catch (NullPointerException e) {
			return false;
		}
		return false;
	}

	/**
	 * @param playerIdentifier
	 * @return the player where his playerIdentifier is the same
	 */
	public Player getPlayer(String playerIdentifier) {
		for (Player player : allPlayers)
			if (player.getPlayerIdentifier().equals(playerIdentifier))
				return player;
		return null;
	}

	/**
	 * @param player
	 */
	public void removePlayer(Player player) {
		allPlayers.remove(player);
	}

	/**
	 * @param identifier
	 */
	public void removePlayer(String identifier) {
		for (Player player : allPlayers)
			if (player.getPlayerIdentifier().equals(identifier))
				allPlayers.remove(player);
	}

}
