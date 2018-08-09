package com.game.zenny.zh.net;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.DatagramPacket;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.game.zenny.zh.entity.Player;
import com.game.zenny.zh.logger.LogType;
import com.game.zenny.zh.logger.Logger;
import com.game.zenny.zh.net.packet.Packet;
import com.game.zenny.zh.net.packet.PacketDestination;
import com.game.zenny.zh.net.packet.login.LoginPacket;
import com.game.zenny.zh.net.server.Server;

public class Receiver extends Thread {

	//// OBJECT
	// -- RECEIVER
	private Bridge bridge;

	/**
	 * @param bridge
	 */
	public Receiver(Bridge bridge) {
		this.bridge = bridge;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while (true) {
			byte[] buffer = new byte[2048];
			DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

			try {
				bridge.getSocket().receive(datagramPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String jsonString = new String(datagramPacket.getData()).trim();
			JSONObject json = getJSONObject(jsonString);
			if (json == null)
				continue;

			long packetTypeID = getPacketTypeID(json);
			String fromPlayerIdentifier = getFromPlayerIdentifier(json);
			String toPlayerIdentifier = getToPlayerIdentifier(json);
			JSONArray datasArray = getDatasArray(json);
			if (datasArray == null)
				continue;

			Object[] datas = new Object[datasArray.size()]; 
			for (int i = 0; i < datasArray.size(); i++) { datas[i] = datasArray.get(i); }

			Packet packet = null;
			try {
				Class<?> packetClass = Packet.getPacketClassByID(packetTypeID);
				Constructor<?> packetClassConstructor = packetClass.getConstructor(Object[].class, String.class,
						String.class);
				packet = (Packet) packetClassConstructor.newInstance((Object) datas, fromPlayerIdentifier,
						toPlayerIdentifier);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				continue;
			}

			JSONObject logJson = new JSONObject();
			logJson.put("fromIP", datagramPacket.getAddress().toString());
			logJson.put("fromPORT", datagramPacket.getPort());
			logJson.put("packetClass", packet.getClass().getName());
			logJson.put("packetJSON", json);
			Logger.log(bridge, LogType.INFO, "RECEIVING PACKET   :   " + logJson.toJSONString());

			Player fromPlayer = null;
			if (bridge.getManager().containsPlayer(fromPlayerIdentifier)) {
				fromPlayer = bridge.getManager().getPlayer(fromPlayerIdentifier);
			} else {
				fromPlayer = new Player(fromPlayerIdentifier, datagramPacket.getAddress(), datagramPacket.getPort());
			}

			if (packet instanceof LoginPacket || bridge.getManager().containsPlayer(fromPlayerIdentifier)) {
				bridge.packetAction(packet, fromPlayer);
			} else {
				continue;
			}

			Server server = (Server) bridge;

			if (toPlayerIdentifier.equals(PacketDestination.TO_SERVER.getPacketDestination())) {
				continue;
			} 
			else if (toPlayerIdentifier.equals(PacketDestination.TO_ALL_CLIENTS_WITHOUT_ME.getPacketDestination())) {
				ArrayList<Player> toPlayers = (ArrayList<Player>) server.getManager().getPlayers().clone();
				toPlayers.remove(fromPlayer);
				server.sendPacket(packet, toPlayers);
			} 
			else if (toPlayerIdentifier.equals(PacketDestination.TO_ALL_CLIENTS_WITH_ME.getPacketDestination())) {
				server.sendPacket(packet, server.getManager().getPlayers());
			} 
			else {
				if (!bridge.getManager().containsPlayer(toPlayerIdentifier))
					continue;
				server.sendPacket(packet, bridge.getManager().getPlayer(toPlayerIdentifier));
			}

		}
	}

	// -- PARSE DATAS

	/**
	 * @param jsonString
	 * @return json object from json string
	 */
	public JSONObject getJSONObject(String jsonString) {
		try {
			return (JSONObject) new JSONParser().parse(jsonString);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * @param json
	 * @return packet id contained in json
	 */
	public long getPacketTypeID(JSONObject json) {
		try {
			return (long) json.get("packetTypeID");
		} catch (NullPointerException | ClassCastException e) {
			return 0;
		}
	}

	/**
	 * @param json
	 * @return from player identifier contained in json
	 */
	public String getFromPlayerIdentifier(JSONObject json) {
		try {
			return (String) json.get("fromPlayerIdentifier");
		} catch (NullPointerException e) {
			return "unknown";
		}
	}

	/**
	 * @param json
	 * @return to player identifier contained in json
	 */
	public String getToPlayerIdentifier(JSONObject json) {
		try {
			return (String) json.get("toPlayerIdentifier");
		} catch (NullPointerException e) {
			return "unknown";
		}
	}

	/**
	 * @param json
	 * @return packet datas array contained in json
	 */
	public JSONArray getDatasArray(JSONObject json) {
		try {
			return (JSONArray) json.get("datas");
		} catch (ClassCastException | NullPointerException e) {
			return null;
		}
	}
}
