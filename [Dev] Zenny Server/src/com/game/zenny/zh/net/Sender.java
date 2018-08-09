package com.game.zenny.zh.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.game.zenny.zh.logger.LogType;
import com.game.zenny.zh.logger.Logger;
import com.game.zenny.zh.net.packet.Packet;

public class Sender {

	//// OBJECT
	// -- SENDER
	private Bridge bridge;

	/**
	 * @param bridge
	 */
	public Sender(Bridge bridge) {
		this.bridge = bridge;
	}

	/**
	 * @param packet
	 * @param address
	 * @param port
	 */
	@SuppressWarnings("unchecked")
	public void sendPacket(Packet packet, InetAddress address, int port) {
		JSONObject buildJson = new JSONObject();
		buildJson.put("packetTypeID", packet.getPacketTypeID());
		buildJson.put("fromPlayerIdentifier", packet.getFromPlayerIdentifier());
		buildJson.put("toPlayerIdentifier", packet.getToPlayerIdentifier());
		buildJson.put("datas", packet.build(new JSONArray()));

		byte[] buffer = buildJson.toJSONString().getBytes();
		try {
			bridge.getSocket().send(new DatagramPacket(buffer, buffer.length, address, port));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject logJson = new JSONObject();
		logJson.put("toIP", address.toString());
		logJson.put("toPORT", port);
		logJson.put("packetClass", packet.getClass().getName());
		logJson.put("packetJSON", buildJson);

		Logger.log(bridge, LogType.INFO, "SENDING PACKET   :   " + logJson.toJSONString());
	}

}
