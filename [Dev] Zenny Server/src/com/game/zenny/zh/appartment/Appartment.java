package com.game.zenny.zh.appartment;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import com.game.zenny.zh.net.server.Server;

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
	private String appartmentIdentifier;
	private String ownerPlayerIdentifier;
	private String appartmentName;
	private AppartmentStructure appartmentStructure;

	//// CONSTRUCTORS

	public Appartment(String appartmentIdentifier) {
		this.appartmentIdentifier = appartmentIdentifier;
		this.ownerPlayerIdentifier = Appartment.getAppartmentOwnerPlayerIdentifierInDB(appartmentIdentifier);
		this.appartmentName = Appartment.getAppartmentNameInDB(appartmentIdentifier);
		this.appartmentStructure = new AppartmentStructure(Appartment.getAppartmentStructureJSONInDB(appartmentIdentifier));
	}

	/**
	 * @param structure
	 */
	public Appartment(String appartmentIdentifier, String ownerPlayerIdentifier, String appartmentName,
			AppartmentStructure appartmentStructure) {
		this.appartmentIdentifier = appartmentIdentifier;
		this.ownerPlayerIdentifier = ownerPlayerIdentifier;
		this.appartmentName = appartmentName;
		this.appartmentStructure = appartmentStructure;
	}
	
	@SuppressWarnings("unchecked")
	public String toJSON() {
		JSONObject appartment = new JSONObject();
		
		appartment.put("appartmentIdentifier", appartmentIdentifier);
		appartment.put("ownerPlayerIdentifier", ownerPlayerIdentifier);
		appartment.put("appartmentName", appartmentName);
		appartment.put("appartmentStructure", appartmentStructure.toJSON());
		
		return appartment.toJSONString();
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

}
