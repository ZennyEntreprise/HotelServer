package com.game.zenny.zh.server.appartment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.game.zenny.zh.server.entity.Player;
import com.game.zenny.zh.server.net.packet.Packet;
import com.game.zenny.zh.server.net.packet.appartment.AddPlayerToAppartmentPacket;
import com.game.zenny.zh.server.net.packet.appartment.RemovePlayerToAppartmentPacket;
import com.game.zenny.zh.server.net.server.Server;

public class Appartment {

	//// STATIC
	
	/**
	 * @param appartmentIdentifier
	 * @return appartment owner player identifier
	 */
	public static String getAppartmentOwnerPlayerIdentifierInDB(String appartmentIdentifier) {
		try {
			ResultSet query = Server.requestDB("SELECT ownerPlayerIdentifier FROM appartments WHERE appartmentIdentifier = '"+appartmentIdentifier+"'");
			query.next();
			String ownerPlayerIdentifier = query.getString("ownerPlayerIdentifier");
			query.close();
			return ownerPlayerIdentifier;
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * @param appartmentIdentifier
	 * @return appartment name
	 */
	public static String getAppartmentNameInDB(String appartmentIdentifier) {
		try {
			ResultSet query = Server.requestDB("SELECT appartmentName FROM appartments WHERE appartmentIdentifier = '"+appartmentIdentifier+"'");
			query.next();
			String appartmentName = query.getString("appartmentName");
			query.close();
			return appartmentName;
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * @param appartmentIdentifier
	 * @return appartment structure json
	 */
	public static String getAppartmentStructureJSONInDB(String appartmentIdentifier) {
		try {
			ResultSet query = Server.requestDB("SELECT appartmentStructure FROM appartments WHERE appartmentIdentifier = '"+appartmentIdentifier+"'");
			query.next();
			String appartmentStructure = query.getString("appartmentStructure");
			query.close();
			return appartmentStructure;
		} catch (SQLException e) {
			return null;
		}
	}
	
	//// OBJECT
	// -- APPARTMENT
	private Server server;
	private String appartmentIdentifier;
	private String ownerPlayerIdentifier;
	private String appartmentName;
	private AppartmentStructure appartmentStructure;
	private ArrayList<Player> playersInAppartment;

	//// CONSTRUCTORS

	/**
	 * @param appartmentIdentifier
	 */
	public Appartment(Server server, String appartmentIdentifier) {
		this.server = server;
		this.appartmentIdentifier = appartmentIdentifier;
		this.ownerPlayerIdentifier = Appartment.getAppartmentOwnerPlayerIdentifierInDB(appartmentIdentifier);
		this.appartmentName = Appartment.getAppartmentNameInDB(appartmentIdentifier);
		this.appartmentStructure = new AppartmentStructure(Appartment.getAppartmentStructureJSONInDB(appartmentIdentifier));
		this.playersInAppartment = new ArrayList<Player>();
	}

	/**
	 * @param structure
	 */
	public Appartment(Server server, String appartmentIdentifier, String ownerPlayerIdentifier, String appartmentName,
			AppartmentStructure appartmentStructure, ArrayList<Player> playersInAppartment) {
		this.server = server;
		this.appartmentIdentifier = appartmentIdentifier;
		this.ownerPlayerIdentifier = ownerPlayerIdentifier;
		this.appartmentName = appartmentName;
		this.appartmentStructure = appartmentStructure;
		this.playersInAppartment = playersInAppartment;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject appartment = new JSONObject();
		
		appartment.put("appartmentIdentifier", appartmentIdentifier);
		appartment.put("ownerPlayerIdentifier", ownerPlayerIdentifier);
		appartment.put("appartmentName", appartmentName);
		appartment.put("appartmentStructure", appartmentStructure.toJSON());
		JSONArray playersInAppartmentJSON = new JSONArray();
		for (Player player : playersInAppartment) {
			playersInAppartmentJSON.add(player.toJSON());
		}
		appartment.put("playersInAppartment", playersInAppartmentJSON);
		
		return appartment;
	}
	
	public void addPlayer(Player player) {
		server.sendPacket(new AddPlayerToAppartmentPacket(Packet.buildDatasObject(player.toJSON().toJSONString()), server.getIdentifier(), null), playersInAppartment);
		
		playersInAppartment.add(player);
	}
	
	public void removePlayer(Player player) {
		playersInAppartment.remove(player);
		
		if (playersInAppartment.size() == 0) {
			server.getManager().removeAppartment(this);
		} else {
			server.sendPacket(new RemovePlayerToAppartmentPacket(Packet.buildDatasObject(player.toJSON().toJSONString()), server.getIdentifier(), null), playersInAppartment);
		}
	}
	
	////GETTERS AND SETTERS
	
	/**
	 * @return the appartmentIdentifier
	 */
	public String getAppartmentIdentifier() {
		return appartmentIdentifier;
	}

	/**
	 * @param appartmentIdentifier the appartmentIdentifier to set
	 */
	public void setAppartmentIdentifier(String appartmentIdentifier) {
		this.appartmentIdentifier = appartmentIdentifier;
	}

	/**
	 * @return the ownerPlayerIdentifier
	 */
	public String getOwnerPlayerIdentifier() {
		return ownerPlayerIdentifier;
	}

	/**
	 * @param ownerPlayerIdentifier the ownerPlayerIdentifier to set
	 */
	public void setOwnerPlayerIdentifier(String ownerPlayerIdentifier) {
		this.ownerPlayerIdentifier = ownerPlayerIdentifier;
	}

	/**
	 * @return the appartmentName
	 */
	public String getAppartmentName() {
		return appartmentName;
	}

	/**
	 * @param appartmentName the appartmentName to set
	 */
	public void setAppartmentName(String appartmentName) {
		this.appartmentName = appartmentName;
	}

	/**
	 * @return the appartmentStructure
	 */
	public AppartmentStructure getAppartmentStructure() {
		return appartmentStructure;
	}

	/**
	 * @param appartmentStructure the appartmentStructure to set
	 */
	public void setAppartmentStructure(AppartmentStructure appartmentStructure) {
		this.appartmentStructure = appartmentStructure;
	}

	/**
	 * @return the playersInAppartment
	 */
	public ArrayList<Player> getPlayersInAppartment() {
		return playersInAppartment;
	}

	/**
	 * @param playersInAppartment the playersInAppartment to set
	 */
	public void setPlayersInAppartment(ArrayList<Player> playersInAppartment) {
		this.playersInAppartment = playersInAppartment;
	}

}
