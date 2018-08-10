package com.game.zenny.zh.server;

import java.net.SocketException;

import com.game.zenny.zh.server.net.Bridge;
import com.game.zenny.zh.server.net.server.Server;

public class AppServer extends Server {

	public static void main(String[] args) {
		try {
			new AppServer(Bridge.defaultPort, "server");
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public AppServer(int serverPort, String identifier) throws SocketException {
		super(serverPort, identifier);
	}

}
