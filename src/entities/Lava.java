package entities;

import backEnd.Map;

/*
 * Lava is a non-storable entity that causes damage to players.
 * 
 * @author Omar Radwan
 */
public class Lava extends Entity {

	public final String ENTITY_TYPE = "Lava";
	private int layer = 0;
	
	/*
	 * Constructor.
	 * 
	 * @param containedMap the map to spawn into.
	 * @param x The x position to spawn to in map.
	 * @param y The y position to spawn to in map.
	 */
	public Lava(Map containedMap, int x, int y) {
		super(containedMap, x, y);
		setLayer(0);
	}

	/*
	 * Constructor. Spawns to map spawn point.
	 * 
	 * @param containedMap the map to spawn into.
	 */
	public Lava(Map containedMap) {
		super(containedMap);
		setLayer(0);
	}

	/*
	 * Obtains print symbol for use in debugging to console.
	 * 
	 * @return Print Symbol
	 */
	public String getPrintSymbol() {
		return "/";
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

	/*
	 * Returns if the entity is storable in player's inventory
	 * 
	 * @return if entity is storable
	 */
	@Override
	public boolean isStorable() {
		return false;
	}
}
