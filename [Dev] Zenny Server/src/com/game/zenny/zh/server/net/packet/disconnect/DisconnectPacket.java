package com.game.zenny.zh.server.net.packet.disconnect;

import org.json.simple.JSONArray;

import com.game.zenny.zh.server.entity.Player;
import com.game.zenny.zh.server.logger.LogType;
import com.game.zenny.zh.server.logger.Logger;
import com.game.zenny.zh.server.net.packet.Packet;
import com.game.zenny.zh.server.net.server.Server;

public class DisconnectPacket extends Packet {

	//// OBJECT
	// -- DISCONNECT PACKET
	public DisconnectPacket(Object[] datas, String fromPlayerIdentifier, String toPlayerIdentifier) {
		super(datas, fromPlayerIdentifier, toPlayerIdentifier);
	}

	@Override
	public int getPacketTypeID() {
		return 4;
	}

	@Override
	public JSONArray build(JSONArray datas) {
		return datas;
	}

	@Override
	public void serverReceivedAction(Server server, Player fromPlayer) {
		if (server.getManager().containsPlayer(fromPlayer)) {
			fromPlayer.getAppartment().removePlayer(fromPlayer);
			server.getManager().removePlayer(fromPlayer);
			Logger.log(server, LogType.INFO, "Player \""+fromPlayer.getPlayerUsername()+"\" disconnected");
		}
	}

}
