package com.game.zenny.zh.server.net.packet.appartment;

import org.json.simple.JSONArray;

import com.game.zenny.zh.server.entity.Player;
import com.game.zenny.zh.server.net.exception.InvalidPacketConstructorException;
import com.game.zenny.zh.server.net.packet.Packet;
import com.game.zenny.zh.server.net.server.Server;

public class AppartmentToGoPacket extends Packet {

	private String appartmentJSON;
	
	public AppartmentToGoPacket(Object[] datas, String fromPlayerIdentifier, String toPlayerIdentifier) {
		super(datas, fromPlayerIdentifier, toPlayerIdentifier);
		
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

		this.appartmentJSON = (String) datas[0];
	}

	@Override
	public int getPacketTypeID() {
		return 6;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray build(JSONArray datas) {
		datas.add(appartmentJSON);
		
		return datas;
	}

	@Override
	public void serverReceivedAction(Server server, Player fromPlayer) {
		return;
	}

}
