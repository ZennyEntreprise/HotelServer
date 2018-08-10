package com.game.zenny.zh.server.net.packet;

import org.json.simple.JSONArray;

import com.game.zenny.zh.server.entity.Player;
import com.game.zenny.zh.server.logger.LogType;
import com.game.zenny.zh.server.logger.Logger;
import com.game.zenny.zh.server.net.server.Server;

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
	public void serverReceivedAction(Server server, Player fromPlayer) {
		Logger.log(server, LogType.WARNING, "Unknown Packet");
	}

}
