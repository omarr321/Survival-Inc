package entities;

import backEnd.Map;

public class Player extends Entity {

	public final String ENTITY_TYPE = "Player";

	private int health;
	private int layer = 256;

	public Player(Map containedMap, int x, int y) {
		super(containedMap, x, y);
	}

	public Player(Map containedMap) {
		super(containedMap);
	}

	public String getPrintSymbol() {
		return "*";
	}

	/*
	 * Sets the rendering priority of the entity. The highest numbered entity in
	 * that position of the map will be rendered in front.
	 * 
	 * @param layer The layer precedence to set the entity to.
	 */
	@Override
	public void setLayer(int layer) {
		this.layer = layer;
	}

	/*
	 * Obtains the layer precedence of the entity
	 * 
	 * @return layer precedence
	 */
	@Override
	public int getLayer() {
		return layer;
	}

	/*
	 * Gets the print friendly name of the entitie's type
	 * 
	 * @return Print friendly entity type
	 */
	@Override
	public String getType() {
		return ENTITY_TYPE;
	}
}