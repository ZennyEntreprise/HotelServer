package com.game.zenny.zh.net.packet.login;

import org.json.simple.JSONArray;

import com.game.zenny.zh.net.User;
import com.game.zenny.zh.net.client.Client;
import com.game.zenny.zh.net.exception.InvalidPacketConstructorException;
import com.game.zenny.zh.net.packet.Packet;
import com.game.zenny.zh.net.server.Server;

public class ValidLoginPacket extends Packet {

	//// OBJECT
	// -- VALID LOGIN PACKET
	private String userIdentifier;
	
	public ValidLoginPacket(Object[] datas, String fromUserIdentifier, String toUserIdentifier) {
		super(datas, fromUserIdentifier, toUserIdentifier);
		
		if (datas.length == 0)
			try {
				throw new InvalidPacketConstructorException("No argument ! :/");
			} catch (InvalidPacketConstructorException e) {
				e.printStackTrace();
			}
		
		if (datas.length > 1)
			try {
				throw new InvalidPacketConstructorException("Too many arguments !");
			} catch (InvalidPacketConstructorException e) {
				e.printStackTrace();
			}
		
		if (!(datas[0] instanceof String))
			try {
				throw new InvalidPacketConstructorException("First argument is not a string !");
			} catch (InvalidPacketConstructorException e) {
				e.printStackTrace();
			}
		
		this.userIdentifier = (String) datas[0];
	}

	@Override
	public int getPacketTypeID() {
		return 2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray build(JSONArray datas) {
		datas.add(userIdentifier);
		
		return datas;
	}

	@Override
	public void serverReceivedAction(Server server, User fromUser) {
		return;
	}

	@Override
	public void clientReceivedAction(Client client, String fromUserIdentifier) {
		client.setIdentifier(userIdentifier);
		client.connected();
	}

}
