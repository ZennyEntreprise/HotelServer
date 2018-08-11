package com.game.zenny.zh.server;

import java.net.InetAddress;
import java.util.ArrayList;

import com.game.zenny.zh.server.appartment.Appartment;
import com.game.zenny.zh.server.entity.Player;

public class Manager {

	// -- USERS
	private ArrayList<Player> allPlayers = new ArrayList<Player>();

	/**
	 * @return player list
	 */
	public ArrayList<Player> getPlayers() {
		return allPlayers;
	}

	/**
	 * @param player
	 */
	public void addPlayer(Player player) {
		allPlayers.add(player);
	}

	/**
	 * @param player
	 * @return true if the player is contained in player list
	 */
	public boolean containsPlayer(Player player) {
		return allPlayers.contains(player);
	}

	/**
	 * @param playerIdentifier
	 * @return true if the player list contains player with this
	 *         playerIdentifier
	 */
	public boolean containsPlayer(String playerIdentifier) {
		for (Player player : allPlayers)
			if (player.getPlayerIdentifier().equals(playerIdentifier))
				return true;
		return false;
	}

	/**
	 * @param playerAddress
	 * @param playerPort
	 * @return true if the player list contains player with this playerAddress
	 *         and this playerPort
	 */
	public boolean containsPlayer(InetAddress playerAddress, int playerPort) {
		try {
			for (Player player : allPlayers)
				if (player.getPlayerAddress().toString().equals(playerAddress.toString())
						&& player.getPlayerPort() == playerPort)
					return true;
		} catch (NullPointerException e) {
			return false;
		}
		return false;
	}

	/**
	 * @param playerIdentifier
	 * @return the player where his playerIdentifier is the same
	 */
	public Player getPlayer(String playerIdentifier) {
		for (Player player : allPlayers)
			if (player.getPlayerIdentifier().equals(playerIdentifier))
				return player;
		return null;
	}

	/**
	 * @param player
	 */
	public void removePlayer(Player player) {
		allPlayers.remove(player);
	}

	/**
	 * @param identifier
	 */
	public void removePlayer(String identifier) {
		for (Player player : allPlayers)
			if (player.getPlayerIdentifier().equals(identifier))
				allPlayers.remove(player);
	}

	// -- APPARTMENTS
	private ArrayList<Appartment> allAppartments = new ArrayList<Appartment>();

	/**
	 * @return all appartments
	 */
	public ArrayList<Appartment> getAppartments() {
		return allAppartments;
	}

	/**
	 * @param appartment
	 */
	public void addAppartment(Appartment appartment) {
		allAppartments.add(appartment);
	}

	/**
	 * @param appartment
	 * @return
	 */
	public boolean containsAppartment(Appartment appartment) {
		return allAppartments.contains(appartment);
	}

	/**
	 * @param appartmentIdentifier
	 * @return
	 */
	public boolean containsAppartment(String appartmentIdentifier) {
		for (Appartment appartment : allAppartments)
			if (appartment.getAppartmentIdentifier().equals(appartmentIdentifier))
				return true;
		return false;
	}

	/**
	 * @param playerIdentifier
	 * @return
	 */
	public Appartment getAppartment(String appartmentIdentifier) {
		for (Appartment appartment : allAppartments)
			if (appartment.getAppartmentIdentifier().equals(appartmentIdentifier))
				return appartment;
		return null;
	}

	public void removeAppartment(Appartment appartment) {
		allAppartments.remove(appartment);
	}
}
