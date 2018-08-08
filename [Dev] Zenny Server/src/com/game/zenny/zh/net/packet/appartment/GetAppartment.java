package com.game.zenny.zh.net.packet.appartment;

import org.json.simple.JSONArray;

import com.game.zenny.zh.entity.Player;
import com.game.zenny.zh.net.packet.Packet;
import com.game.zenny.zh.net.server.Server;

public class GetAppartment extends Packet {

	public GetAppartment(Object[] datas, String fromPlayerIdentifier, String toPlayerIdentifier) {
		super(datas, fromPlayerIdentifier, toPlayerIdentifier);
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
		
	}

}
