package com.game.zenny.zh.appartment;

import org.json.simple.JSONArray;

public class AppartmentGroundCell {

	private boolean activated;

	//// CONSTRCUTORS

	/**
	 * @param x
	 * @param y
	 */
	public AppartmentGroundCell(boolean activated) {
		this.activated = activated;
	}

	@SuppressWarnings("unchecked")
	public String toJSON() {
		JSONArray cellDatas = new JSONArray();
		cellDatas.add(activated);
		
		return cellDatas.toJSONString();
	}
	
	//// GETTERS AND SETTERS

	/**
	 * @return the activated
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * @param activated
	 *            the activated to set
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

}
