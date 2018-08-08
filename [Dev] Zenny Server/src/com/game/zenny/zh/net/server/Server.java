package com.game.zenny.zh.net.server;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import com.game.zenny.zh.entity.Player;
import com.game.zenny.zh.net.Bridge;
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
	 * @param from player
	 */
	@Override
	public void packetAction(Packet packet, Player fromPlayer) {
		packet.serverReceivedAction(this, fromPlayer);
	}

	/**
	 * @param packet
	 * @param to player
	 */
	public void sendPacket(Packet packet, Player toPlayer) {
		getSender().sendPacket(packet, toPlayer.getPlayerAddress(), toPlayer.getPlayerPort());
	}

	/**
	 * @param packet
	 * @param to players
	 */
	public void sendPacket(Packet packet, ArrayList<Player> toPlayers) {
		for (Player player : toPlayers) {
			packet.setToPlayerIdentifier(player.getPlayerIdentifier());
			sendPacket(packet, player);
		}
	}
}
