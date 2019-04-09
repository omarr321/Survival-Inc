package entities;

import java.util.ArrayList;

import backEnd.Map;
import backEnd.Map.Tile;

public class Player extends Entity {
	
	public final String ENTITY_TYPE = "Player";
	private int layer = 256;
	
	private String player_name = "Person";
	private int health = 100;
	private int xp = 0;
	private Map inventory;
	
	public Player(Map containedMap, int x, int y) {
		super(containedMap, x, y);
		this.inventory = new Map(1, 1, 0, 0, Tile.STONE);
	}
	
	public Player(Map containedMap) {
		super(containedMap);
		this.inventory = new Map(1, 1, 0, 0, Tile.STONE);
	}
	
	/*
	 * Player will add all items in their position to their inventory.
	 */
	public void pickUpEntities() {
		ArrayList<Entity> ent = containedMap.getEntitiesAtPos(getPosX(), getPosY(), n -> n.isStorable());
		ent.remove(this);
		ent.forEach(n -> n.moveToMap(inventory));
	}
	
	public String getPlayerName() {
		return player_name;
	}
	
	public void setPlayerName(String name) {
		this.player_name = name;
	}
	
	public Map getInventory() {
		return this.inventory;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	public int getXP() {
		return xp;
	}
	
	public void setXP(int xp) {
		this.xp = xp;
	}
	
	public String getPrintSymbol() {
		return "*";
	}

	/* Sets the rendering priority of the entity. The highest numbered
	 * entity in that position of the map will be rendered in front.
	 * 
	 * @param layer The layer precedence to set the entity to.
	 */
	@Override
	public void setLayer(int layer) {
		this.layer = layer;
	}

	/* Obtains the layer precedence of the entity
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

	@Override
	public boolean isStorable() {
		return false;
	}
}