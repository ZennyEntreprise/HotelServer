package com.game.zenny.zh.net.packet;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;

import com.game.zenny.zh.net.User;
import com.game.zenny.zh.net.client.Client;
import com.game.zenny.zh.net.server.Server;

public abstract class Packet {

	//// STATIC
	private static HashMap<Long, Class<?>> packets = new HashMap<Long, Class<?>>();

	/**
	 * @return packet class list
	 */
	public static HashMap<Long, Class<?>> getPackets() {
		return packets;
	}

	/**
	 * @param packetID
	 * @param packetClass
	 */
	public static void registerPacket(long packetTypeID, Class<?> packetClass) {
		packets.put(packetTypeID, packetClass);
	}

	/**
	 * @param packetID
	 * @return
	 */
	public static Class<?> getPacketClassByID(long packetID) {
		for (Entry<Long, Class<?>> packet : packets.entrySet())
			if (packet.getKey() == packetID)
				return packet.getValue();
		return getPacketClassByID(0);
	}

	/**
	 * @param datas
	 * @return
	 */
	public static Object[] buildDatasObject(Object... datas) {
		return datas;
	}

	//// OBJECT
	// -- PACKET
	private String fromUserIdentifier, toUserIdentifier;

	/**
	 * @param datas
	 * @param fromUserIdentifier
	 * @param toUserIdentifier
	 */
	public Packet(Object[] datas, String fromUserIdentifier, String toUserIdentifier) {
		this.fromUserIdentifier = fromUserIdentifier;
		this.toUserIdentifier = toUserIdentifier;
	}

	/**
	 * @return packet id
	 */
	public abstract int getPacketTypeID();

	/**
	 * @param datas
	 * @return completed datas
	 */
	public abstract JSONArray build(JSONArray datas);

	/**
	 * @param server
	 * @param fromUser
	 */
	public abstract void serverReceivedAction(Server server, User fromUser);

	/**
	 * @param client
	 * @param fromUserIdentifier
	 */
	public abstract void clientReceivedAction(Client client, String fromUserIdentifier);

	/**
	 * @return the fromUserIdentifier
	 */
	public String getFromUserIdentifier() {
		return fromUserIdentifier;
	}

	/**
	 * @param fromUserIdentifier
	 *            the fromUserIdentifier to set
	 */
	public void setFromUserIdentifier(String fromUserIdentifier) {
		this.fromUserIdentifier = fromUserIdentifier;
	}

	/**
	 * @return the toUserIdentifier
	 */
	public String getToUserIdentifier() {
		return toUserIdentifier;
	}

	/**
	 * @param toUserIdentifier
	 *            the toUserIdentifier to set
	 */
	public void setToUserIdentifier(String toUserIdentifier) {
		this.toUserIdentifier = toUserIdentifier;
	}

}
