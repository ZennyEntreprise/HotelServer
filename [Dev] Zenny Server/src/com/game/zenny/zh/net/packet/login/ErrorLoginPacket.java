package com.game.zenny.zh.net.packet.login;

import java.util.UUID;

import org.json.simple.JSONArray;

import com.game.zenny.zh.net.User;
import com.game.zenny.zh.net.client.Client;
import com.game.zenny.zh.net.exception.InvalidPacketConstructorException;
import com.game.zenny.zh.net.packet.Packet;
import com.game.zenny.zh.net.server.Server;

public class ErrorLoginPacket extends Packet {

	//// ENUM
	// -- ERROR MESSAGE
	static enum ErrorMessage {
		USER_IDENTIFIER_ALREADY_EXISTS("User identifier already exists"),
		USER_IP_AND_USER_PORT_ALREADY_EXISTS("User IP and user port already exists"),
		USER_IDENTIFIER_USER_IP_AND_USER_PORT_ALREADY_EXISTS("User identifier, user ip and user port already exists");
		
		private String errorMessage;
		
		/**
		 * @param errorMessage
		 */
		ErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		
		/**
		 * @return error message
		 */
		public String getErrorMessage() {
			return errorMessage;
		}
	}
	
	//// OBJECT
	// -- ERROR LOGIN PACKET
	private String errorMessage;
	
	public ErrorLoginPacket(Object[] datas, String fromUserIdentifier, String toUserIdentifier) {
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
		
		this.errorMessage = (String) datas[0];
	}

	@Override
	public int getPacketTypeID() {
		return 3;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray build(JSONArray datas) {
		datas.add(errorMessage);
		
		return datas;
	}

	@Override
	public void serverReceivedAction(Server server, User fromUser) {
		return;
	}

	@Override
	public void clientReceivedAction(Client client, String fromUserIdentifier) {
		if (errorMessage.equalsIgnoreCase(ErrorMessage.USER_IDENTIFIER_ALREADY_EXISTS.getErrorMessage())) {
			client.connect(UUID.randomUUID().toString());
		}
	}

}
