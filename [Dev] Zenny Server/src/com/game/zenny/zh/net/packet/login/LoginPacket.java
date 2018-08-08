package com.game.zenny.zh.net.packet.login;

import java.net.InetAddress;

import org.json.simple.JSONArray;

import com.game.zenny.zh.entity.Player;
import com.game.zenny.zh.net.exception.InvalidPacketConstructorException;
import com.game.zenny.zh.net.packet.Packet;
import com.game.zenny.zh.net.server.Server;

public class LoginPacket extends Packet {

	//// OBJECT
	// -- LOGIN PACKET
	private String playerIdentifier;

	public LoginPacket(Object[] datas, String fromPlayerIdentifier, String toPlayerIdentifier) {
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

		this.playerIdentifier = (String) datas[0];
	}

	@Override
	public int getPacketTypeID() {
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray build(JSONArray datas) {
		datas.add(playerIdentifier);

		return datas;
	}

	@Override
	public void serverReceivedAction(Server server, Player fromPlayer) {
		String newPlayerIdentifier = this.playerIdentifier;
		InetAddress fromPlayerAddress = fromPlayer.getPlayerAddress();
		int fromPlayerPort = fromPlayer.getPlayerPort();

		if (server.containsPlayer(newPlayerIdentifier) && !server.containsPlayer(fromPlayerAddress, fromPlayerPort)) {
			server.sendPacket(new ErrorLoginPacket(Packet.buildDatasObject(ErrorLoginPacket.ErrorMessage.USER_IDENTIFIER_ALREADY_EXISTS.getErrorMessage()), server.getIdentifier(), fromPlayer.getPlayerIdentifier()), fromPlayer);
		} else if (!server.containsPlayer(newPlayerIdentifier) && server.containsPlayer(fromPlayerAddress, fromPlayerPort)) {
			server.sendPacket(new ErrorLoginPacket(Packet.buildDatasObject(ErrorLoginPacket.ErrorMessage.USER_IP_AND_USER_PORT_ALREADY_EXISTS.getErrorMessage()), server.getIdentifier(), fromPlayer.getPlayerIdentifier()), fromPlayer);
		} else if (server.containsPlayer(newPlayerIdentifier) && server.containsPlayer(fromPlayerAddress, fromPlayerPort)) {
			server.sendPacket(new ErrorLoginPacket(Packet.buildDatasObject(ErrorLoginPacket.ErrorMessage.USER_IDENTIFIER_USER_IP_AND_USER_PORT_ALREADY_EXISTS.getErrorMessage()), server.getIdentifier(), fromPlayer.getPlayerIdentifier()), fromPlayer);
		} else {
			fromPlayer.setPlayerIdentifier(newPlayerIdentifier);
			server.addPlayer(fromPlayer);
			server.sendPacket(new ValidLoginPacket(Packet.buildDatasObject(playerIdentifier), server.getIdentifier(), fromPlayer.getPlayerIdentifier()), fromPlayer);
		}
	}

}
