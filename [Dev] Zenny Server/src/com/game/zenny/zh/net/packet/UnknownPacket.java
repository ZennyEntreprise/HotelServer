package com.game.zenny.zh.net.packet;

import org.json.simple.JSONArray;

import com.game.zenny.zh.net.User;
import com.game.zenny.zh.net.client.Client;
import com.game.zenny.zh.net.logger.LogType;
import com.game.zenny.zh.net.logger.Logger;
import com.game.zenny.zh.net.server.Server;

public class UnknownPacket extends Packet {

	//// OBJECT
	// -- UNKNOWN PACKET
	public UnknownPacket(Object[] datas, String from, String to) {
		super(datas, from, to);
	}

	@Override
	public int getPacketTypeID() {
		return 0;
	}

	@Override
	public JSONArray build(JSONArray datas) {
		return datas;
	}

	@Override
	public void serverReceivedAction(Server server, User fromUser) {
		Logger.log(server, LogType.WARNING, "Unknown Packet");
	}

	@Override
	public void clientReceivedAction(Client client, String fromUserIdentifier) {
		Logger.log(client, LogType.WARNING, "Unknown Packet");
	}

}
