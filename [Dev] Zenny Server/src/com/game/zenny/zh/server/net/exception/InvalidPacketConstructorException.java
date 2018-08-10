package com.game.zenny.zh.server.net.exception;

import com.game.zenny.zh.server.logger.LogType;
import com.game.zenny.zh.server.logger.Logger;

public class InvalidPacketConstructorException extends Exception {

	//// OBJECT
	// -- InvalidPacketConstructorException
	private static final long serialVersionUID = -750436884354575485L;

	/**
	 * @param none
	 */
	public InvalidPacketConstructorException() {
	}

	/**
	 * @param message
	 */
	public InvalidPacketConstructorException(String message) {
		super(message);

		Logger.logInConsole(LogType.WARNING, message);
	}

}
