package com.game.zenny.zh.net.client;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.game.zenny.zh.net.Bridge;
import com.game.zenny.zh.net.User;
import com.game.zenny.zh.net.logger.LogType;
import com.game.zenny.zh.net.logger.Logger;
import com.game.zenny.zh.net.packet.Packet;
import com.game.zenny.zh.net.packet.PacketDestination;
import com.game.zenny.zh.net.packet.disconnect.DisconnectPacket;
import com.game.zenny.zh.net.packet.login.LoginPacket;

public abstract class Client extends Bridge {

	//// OBJECT
	// -- CLIENT
	private InetAddress serverAddress;
	private int serverPort;

	/**
	 * @param serverAddress
	 * @param serverPort
	 * @throws SocketException
	 */
	public Client(InetAddress serverAddress, int serverPort) throws SocketException {
		super(new DatagramSocket(), "");

		Logger.log(this, LogType.INFO, "Creating client...");
		this.serverAddress = serverAddress;
		Logger.log(this, LogType.INFO, "Server address defined on : " + serverAddress.getHostName());
		this.serverPort = serverPort;
		Logger.log(this, LogType.INFO, "Server port defined on : " + serverPort);
		Logger.log(this, LogType.INFO, "Client created !");
	}

	/**
	 * @return the serverAddress
	 */
	public InetAddress getServerAddress() {
		return serverAddress;
	}

	/**
	 * @param serverAddress
	 *            the serverAddress to set
	 */
	public void setServerAddress(InetAddress serverAddress) {
		this.serverAddress = serverAddress;
	}

	/**
	 * @return the serverPort
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * @param serverPort
	 *            the serverPort to set
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	// -- CONNECTION

	/**
	 * @param clientID
	 */
	public void connect(String userIdentifier) {
		Logger.log(this, LogType.NORMAL, "Trying to connect with that clientID: " + userIdentifier);
		sendPacket(new LoginPacket(Packet.buildDatasObject(userIdentifier), getIdentifier(), PacketDestination.TO_SERVER.getPacketDestination()));
	}

	public void disconnect() {
		Logger.log(this, LogType.NORMAL, "Disconnecting...");
		sendPacket(new DisconnectPacket(Packet.buildDatasObject(), getIdentifier(), PacketDestination.TO_SERVER.getPacketDestination()));
	}
	
	// -- PACKET

	/**
	 * @param packet
	 * @param user
	 */
	@Override
	public void packetAction(Packet packet, User fromUser) {
		packet.clientReceivedAction(this, fromUser.getUserIdentifier());
	}

	/**
	 * @param packet
	 */
	public void sendPacket(Packet packet) {
		getSender().sendPacket(packet, this.serverAddress, this.serverPort);
	}

	// -- EVENTS
	
	public abstract void connected();
	
	/**
	 * @param userAddedIdentifier
	 */
	public abstract void userAdded(String userAddedIdentifier);
	
	/**
	 * @param userRemovedIdentifier
	 */
	public abstract void userRemoved(String userRemovedIdentifier);
}
