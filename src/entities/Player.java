package entities;

import backEnd.Map;

public class Player extends Entity {

	public final String ENTITY_TYPE = "Player";
<<<<<<< refs/remotes/origin/BaseCode

	private int health;
	private int layer = 256;

=======
	private int layer = 256;
	
	private String player_name = "Person";
	private int health = 100;
	private int xp = 0;
	
>>>>>>> Created test in shell for back-end engine.
	public Player(Map containedMap, int x, int y) {
		super(containedMap, x, y);
	}

	public Player(Map containedMap) {
		super(containedMap);
	}
<<<<<<< refs/remotes/origin/BaseCode

=======
	
	public String getPlayerName() {
		return player_name;
	}
	
	public void setPlayerName(String name) {
		this.player_name = name;
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
	
>>>>>>> Created test in shell for back-end engine.
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