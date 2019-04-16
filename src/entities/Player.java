package entities;

import java.util.ArrayList;

import backEnd.Map;
import backEnd.Map.Tile;

/*
 * Player is special entity that is attached to the front-end using controls.
 * It is the main spectator in the game.
 * 
 * @author Wyatt Phillips
 */
public class Player extends Entity {

	public final String ENTITY_TYPE = "Player";
	
	private int layer = 256;
	
	private String player_name = "Person";
	private int health = 100;
	private int xp = 0;
	private Map inventory;
	
	/*
	 * Constructor.
	 * 
	 * @param containedMap the map to spawn into.
	 * @param x The x position to spawn to in map.
	 * @param y The y position to spawn to in map.
	 */
	public Player(Map containedMap, int x, int y) {
		super(containedMap, x, y);
		this.inventory = new Map(1, 1, 0, 0, Tile.STONE);
	}
	
	/*
	 * Constructor. Spawns to map spawn point.
	 * 
	 * @param containedMap the map to spawn into.
	 */
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
	
	/*
	 * Get the player's printable name;
	 * 
	 * @return Player Name
	 */
	public String getPlayerName() {
		return player_name;
	}
	
	/*
	 * Sets a printable name for the player to have.
	 * 
	 * @param name The printable name for the player.
	 */
	public void setPlayerName(String name) {
		this.player_name = name;
	}
	
	/*
	 * Obtain the map instance of the player's inventory containing storable entities.
	 * 
	 * @return The map for inventory
	 */
	public Map getInventory() {
		return this.inventory;
	}
	
	/*
	 * Obtain the player's health (100 = Full Health, 0 = Dead)
	 * 
	 * @return Player's health
	 */
	public int getHealth() {
		return health;
	}
	
	/*
	 * Set the player's health (100 = Full Health, 0 = Dead)
	 * 
	 * @param health The integer to set health to
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/*
	 * Obtain the player's experience points (Starts at 0)
	 * 
	 * @return Player's experience points
	 */
	public int getXP() {
		return xp;
	}
	
	/*
	 * Set the player's experience points (Starts at 0)
	 * 
	 * @param xp The integer to set xp to
	 */
	public void setXP(int xp) {
		this.xp = xp;
	}
	
	/*
	 * Obtains print symbol for use in debugging to console.
	 * 
	 * @return Print Symbol
	 */
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
	
	/*
	 * Returns if the entity is storable in player's inventory.
	 * 
	 * @return if entity is storable. This should be set to false for a player lol.
	 */
	@Override
	public boolean isStorable() {
		return false;
	}
}