package com.game.zenny.zh.net.server;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import com.game.zenny.zh.net.Bridge;
import com.game.zenny.zh.net.User;
import com.game.zenny.zh.net.logger.LogType;
import com.game.zenny.zh.net.logger.Logger;
import com.game.zenny.zh.net.packet.Packet;

public class Server extends Bridge {

	//// STATIC
	public static void main(String[] args) {
		try {
			new Server(Bridge.defaultPort, "server");
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	//// OBJECT
	// -- SERVER
	private int serverPort;

	/**
	 * @param serverPort
	 * @param identifier
	 * @throws SocketException
	 */
	public Server(int serverPort, String identifier) throws SocketException {
		super(new DatagramSocket(serverPort), identifier);

		Logger.log(this, LogType.INFO, "Creating server...");
		this.serverPort = serverPort;
		Logger.log(this, LogType.INFO, "Listening port defined on: " + serverPort);
		Logger.log(this, LogType.INFO, "Server created !");
	}

	/**
	 * @return serverPort
	 */
	public int getServerPort() {
		return serverPort;
	}

	// -- PACKET

	/**
	 * @param packet
	 * @param from user
	 */
	@Override
	public void packetAction(Packet packet, User fromUser) {
		packet.serverReceivedAction(this, fromUser);
	}

	/**
	 * @param packet
	 * @param to user
	 */
	public void sendPacket(Packet packet, User toUser) {
		getSender().sendPacket(packet, toUser.getUserAddress(), toUser.getUserPort());
	}

	/**
	 * @param packet
	 * @param to users
	 */
	public void sendPacket(Packet packet, ArrayList<User> toUsers) {
		for (User user : toUsers) {
			packet.setToUserIdentifier(user.getUserIdentifier());
			sendPacket(packet, user);
		}
	}
}
