package com.game.zenny.zh.net.packet.login;

import java.net.InetAddress;

import org.json.simple.JSONArray;

import com.game.zenny.zh.net.User;
import com.game.zenny.zh.net.client.Client;
import com.game.zenny.zh.net.exception.InvalidPacketConstructorException;
import com.game.zenny.zh.net.packet.Packet;
import com.game.zenny.zh.net.packet.user.AddUserPacket;
import com.game.zenny.zh.net.server.Server;

public class LoginPacket extends Packet {

	//// OBJECT
	// -- LOGIN PACKET
	private String userIdentifier;

	public LoginPacket(Object[] datas, String fromUserIdentifier, String toUserIdentifier) {
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
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray build(JSONArray datas) {
		datas.add(userIdentifier);

		return datas;
	}

	@Override
	public void serverReceivedAction(Server server, User fromUser) {
		String newUserIdentifier = this.userIdentifier;
		InetAddress fromUserAddress = fromUser.getUserAddress();
		int fromUserPort = fromUser.getUserPort();

		if (server.containsUser(newUserIdentifier) && !server.containsUser(fromUserAddress, fromUserPort)) {
			server.sendPacket(new ErrorLoginPacket(Packet.buildDatasObject(ErrorLoginPacket.ErrorMessage.USER_IDENTIFIER_ALREADY_EXISTS.getErrorMessage()), server.getIdentifier(), fromUser.getUserIdentifier()), fromUser);
		} else if (!server.containsUser(newUserIdentifier) && server.containsUser(fromUserAddress, fromUserPort)) {
			server.sendPacket(new ErrorLoginPacket(Packet.buildDatasObject(ErrorLoginPacket.ErrorMessage.USER_IP_AND_USER_PORT_ALREADY_EXISTS.getErrorMessage()), server.getIdentifier(), fromUser.getUserIdentifier()), fromUser);
		} else if (server.containsUser(newUserIdentifier) && server.containsUser(fromUserAddress, fromUserPort)) {
			server.sendPacket(new ErrorLoginPacket(Packet.buildDatasObject(ErrorLoginPacket.ErrorMessage.USER_IDENTIFIER_USER_IP_AND_USER_PORT_ALREADY_EXISTS.getErrorMessage()), server.getIdentifier(), fromUser.getUserIdentifier()), fromUser);
		} else {
			fromUser.setUserIdentifier(newUserIdentifier);
			server.addUser(fromUser);
			server.sendPacket(new ValidLoginPacket(Packet.buildDatasObject(userIdentifier), server.getIdentifier(), fromUser.getUserIdentifier()), fromUser);
			server.sendPacket(new AddUserPacket(Packet.buildDatasObject(userIdentifier), server.getIdentifier(), null), server.getUsers());
		}
	}

	@Override
	public void clientReceivedAction(Client client, String fromUserIdentifier) {
		return;
	}

}
