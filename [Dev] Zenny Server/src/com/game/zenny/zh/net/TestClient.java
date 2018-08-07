package com.game.zenny.zh.net;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.game.zenny.zh.net.client.Client;
import com.game.zenny.zh.net.logger.LogType;
import com.game.zenny.zh.net.logger.Logger;

public class TestClient extends Client {

	public static void main(String[] args) {
		TestClient app = null;
		try {
			app = new TestClient(InetAddress.getLocalHost(), Bridge.defaultPort);
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
		app.connect("moi");
	}
	
	public TestClient(InetAddress serverAddress, int serverPort) throws SocketException {
		super(serverAddress, serverPort);
	}

	@Override
	public void connected() {
		Logger.log(this, LogType.INFO, "Connected !");
	}
	
	@Override
	public void userAdded(String userAddedIdentifier) {
		Logger.log(this, LogType.INFO, "User added !");
	}

	@Override
	public void userRemoved(String userRemovedIdentifier) {
		Logger.log(this, LogType.INFO, "User removed !");
	}

}
