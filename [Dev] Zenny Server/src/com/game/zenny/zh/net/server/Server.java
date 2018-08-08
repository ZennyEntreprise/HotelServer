package com.game.zenny.zh.net.server;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.game.zenny.zh.entity.Player;
import com.game.zenny.zh.net.Bridge;
import com.game.zenny.zh.net.logger.LogType;
import com.game.zenny.zh.net.logger.Logger;
import com.game.zenny.zh.net.packet.Packet;

public class Server extends Bridge {

	public static String URL = "jdbc:mysql://localhost/zenny_hotel";
	public static String LOGIN = "root";
	public static String PASSWORD = "";
	public static Connection connection;
    public static Statement statement;
	
    public static ResultSet requestDB(String query) {
    	try {
			ResultSet resultSet = statement.executeQuery(query);
			
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Database connection failure !");
			e.printStackTrace();
			System.exit(0);
		}
		
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
