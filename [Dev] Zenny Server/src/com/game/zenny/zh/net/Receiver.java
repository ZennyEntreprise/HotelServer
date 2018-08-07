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

import com.game.zenny.zh.net.logger.LogType;
import com.game.zenny.zh.net.logger.Logger;
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
			String fromUserIdentifier = getFromUserIdentifier(json);
			String toUserIdentifier = getToUserIdentifier(json);
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
				packet = (Packet) packetClassConstructor.newInstance((Object) datas, fromUserIdentifier,
						toUserIdentifier);
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

			User fromUser = null;
			if (bridge.containsUser(fromUserIdentifier, datagramPacket.getAddress(), datagramPacket.getPort())) {
				fromUser = bridge.getUser(fromUserIdentifier, datagramPacket.getAddress(),
						datagramPacket.getPort());
			} else {
				fromUser = new User(fromUserIdentifier, datagramPacket.getAddress(), datagramPacket.getPort());
			}

			if (packet instanceof LoginPacket || bridge.containsUser(fromUserIdentifier)) {
				bridge.packetAction(packet, fromUser);
			} else {
				continue;
			}

			Server server = (Server) bridge;

			if (toUserIdentifier.equals(PacketDestination.TO_SERVER.getPacketDestination())) {
				continue;
			} 
			else if (toUserIdentifier.equals(PacketDestination.TO_ALL_CLIENTS_WITHOUT_ME.getPacketDestination())) {
				ArrayList<User> toUsers = (ArrayList<User>) server.getUsers().clone();
				toUsers.remove(fromUser);
				server.sendPacket(packet, toUsers);
			} 
			else if (toUserIdentifier.equals(PacketDestination.TO_ALL_CLIENTS_WITH_ME.getPacketDestination())) {
				server.sendPacket(packet, server.getUsers());
			} 
			else {
				if (!bridge.containsUser(toUserIdentifier))
					continue;
				server.sendPacket(packet, bridge.getUser(toUserIdentifier));
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
	 * @return from user identifier contained in json
	 */
	public String getFromUserIdentifier(JSONObject json) {
		try {
			return (String) json.get("fromUserIdentifier");
		} catch (NullPointerException e) {
			return "unknown";
		}
	}

	/**
	 * @param json
	 * @return to user identifier contained in json
	 */
	public String getToUserIdentifier(JSONObject json) {
		try {
			return (String) json.get("toUserIdentifier");
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
