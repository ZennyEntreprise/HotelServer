package com.game.zenny.zh.net.packet.disconnect;

import org.json.simple.JSONArray;

import com.game.zenny.zh.net.User;
import com.game.zenny.zh.net.client.Client;
import com.game.zenny.zh.net.packet.Packet;
import com.game.zenny.zh.net.packet.user.RemoveUserPacket;
import com.game.zenny.zh.net.server.Server;

public class DisconnectPacket extends Packet {

	//// OBJECT
	// -- DISCONNECT PACKET
	public DisconnectPacket(Object[] datas, String fromUserIdentifier, String toUserIdentifier) {
		super(datas, fromUserIdentifier, toUserIdentifier);
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
	public void serverReceivedAction(Server server, User fromUser) {
		if (server.containsUser(fromUser))
			server.sendPacket(new RemoveUserPacket(Packet.buildDatasObject(fromUser.getUserIdentifier()), server.getIdentifier(), null), server.getUsers());
	}

	@Override
	public void clientReceivedAction(Client client, String fromUserIdentifier) {

	}

}
