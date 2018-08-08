package com.game.zenny.zh.net.packet.disconnect;

import org.json.simple.JSONArray;

import com.game.zenny.zh.entity.Player;
import com.game.zenny.zh.net.packet.Packet;
import com.game.zenny.zh.net.server.Server;

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
		if (server.containsPlayer(fromPlayer)) {
			server.removePlayer(fromPlayer);
			System.out.println(fromPlayer.getUsername() + " disconnected");
		}
	}

}
