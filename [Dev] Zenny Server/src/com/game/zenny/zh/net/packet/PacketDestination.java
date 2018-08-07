package com.game.zenny.zh.net.packet;

public enum PacketDestination {
	TO_SERVER("to_server"), TO_ALL_CLIENTS_WITHOUT_ME("to_all_clients_without_me"), TO_ALL_CLIENTS_WITH_ME("to_all_clients_with_me");
	
	private String packetDestination;
	
	/**
	 * @param packetDestination
	 */
	private PacketDestination(String packetDestination) {
		this.packetDestination = packetDestination;
	}
	
	/**
	 * @return packet destination
	 */
	public String getPacketDestination() {
		return packetDestination;
	}
}
