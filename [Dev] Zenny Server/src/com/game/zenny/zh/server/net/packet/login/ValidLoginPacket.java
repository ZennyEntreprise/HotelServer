package com.game.zenny.zh.server.net.packet.login;

import org.json.simple.JSONArray;

import com.game.zenny.zh.server.entity.Player;
import com.game.zenny.zh.server.net.exception.InvalidPacketConstructorException;
import com.game.zenny.zh.server.net.packet.Packet;
import com.game.zenny.zh.server.net.server.Server;

public class ValidLoginPacket extends Packet {

	//// OBJECT
	// -- VALID LOGIN PACKET
	private String playerJson;
	
	public ValidLoginPacket(Object[] datas, String fromPlayerIdentifier, String toPlayerIdentifier) {
		super(datas, fromPlayerIdentifier, toPlayerIdentifier);
		
		if (datas.length < 1)
			try {
				throw new InvalidPacketConstructorException("Not enough arguments ! :/");
			} catch (InvalidPacketConstructorException e) {
				e.printStackTrace();
			}
		
		if (datas.length > 1)
			try {
				throw new InvalidPacketConstructorException("Too many arguments !");
			} catch (InvalidPacketConstructorException e) {
				e.printStackTrace();
			}
		
		if (!(datas[0] instanceof String) && !(datas[1] instanceof String))
			try {
				throw new InvalidPacketConstructorException("Arguments aren't correct !");
			} catch (InvalidPacketConstructorException e) {
				e.printStackTrace();
			}
		
		this.playerJson = (String) datas[0];
	}

	@Override
	public int getPacketTypeID() {
		return 2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray build(JSONArray datas) {
		datas.add(playerJson);
		
		return datas;
	}

	@Override
	public void serverReceivedAction(Server server, Player fromPlayer) {
		return;
	}

}
