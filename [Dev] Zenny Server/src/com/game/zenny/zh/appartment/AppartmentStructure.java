package com.game.zenny.zh.appartment;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AppartmentStructure {

	private AppartmentGroundCell[][] structure;

	//// CONSTUCTORS

	public AppartmentStructure(String appartmentStructureJSON) {
		try {
			JSONArray appartmentStructureXY = (JSONArray) new JSONParser().parse(appartmentStructureJSON);
			
			structure = new AppartmentGroundCell[appartmentStructureXY.size()][((JSONArray) appartmentStructureXY.get(appartmentStructureXY.size() - 1)).size()];
			
			for (int y = 0; y < appartmentStructureXY.size(); y++) {
				JSONArray appartmentStructureY = (JSONArray) appartmentStructureXY.get(y);
				for (int x = 0; x < appartmentStructureY.size(); x++) {
					JSONArray cellDatas = (JSONArray) appartmentStructureY.get(x);
					structure[y][x] = new AppartmentGroundCell((boolean) cellDatas.get(0));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param structure
	 */
	public AppartmentStructure(AppartmentGroundCell[][] structure) {
		this.structure = structure;
	}

	@SuppressWarnings("unchecked")
	public String toJSON() {
		JSONArray appartmentStructureXY = new JSONArray();
		for (int y = 0; y < structure.length; y++) {
			JSONArray appartmentStructureY = new JSONArray();
			for (int x = 0; x < structure[y].length; x++) {
				appartmentStructureY.add(structure[y][x].toJSON());
			}
			appartmentStructureXY.add(appartmentStructureY);
		}
		
		return appartmentStructureXY.toJSONString();
	}
	
	//// GETTERS AND SETTERS

	/**
	 * @return the structure
	 */
	public AppartmentGroundCell[][] getStructure() {
		return structure;
	}

	/**
	 * @param structure
	 *            the structure to set
	 */
	public void setStructure(AppartmentGroundCell[][] structure) {
		this.structure = structure;
	}

}
