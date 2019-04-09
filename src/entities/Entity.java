package entities;
import backEnd.*;

/*
 * Entities represent the foundation for all in game objects.
 * They can be physical objects such as sticks or even things
 * as complicated as players.
 * 
 * @author Wyatt Phillips
 */
public abstract class Entity {
	protected Map containedMap;
	private int layer = 0;
	
	protected int posX;
	protected int posY;
	protected boolean visible = true;
	
	/*
	 * Constructor.
	 * 
	 * @param containedMap The map to spawn the Entity into.
	 * @param x The x position it spawn the entity.
	 * @param y The y position it spawn the entity.
	 */
	public Entity(Map containedMap, int x, int y) {
		this.posX = x;
		this.posY = y;
		this.containedMap = containedMap;
		if(!setPos(getPosX(), getPosY())) {
			goToSpawn();
		}
		containedMap.addEntity(this);
	}
	
	/*
	 * Constructor.
	 * Spawns the entity to the contained map's default spawn.
	 * 
	 * @param containedMap The map to spawn the Entity into.
	 */
	public Entity(Map containedMap) {
		this(containedMap, containedMap.getDefaultSpawnX(), containedMap.getDefaultSpawnY());
	}
	
	/*
	 * Changes what map contains this entity. The containedMap
	 * variable is changed allowing the entity to interact with
	 * the right map's methods.
	 * 
	 * Note that the entity is removed from the previous map to
	 * avoid entity duplication or entity super position.
	 * 
	 * @param map The map to move the entity into.
	 */
	public void moveToMap(Map map) {
		containedMap.removeEntity(this);
		containedMap = map;
		if(!setPos(getPosX(), getPosY())) {
			goToSpawn();
		}
		containedMap.addEntity(this);
	}
	
	/*
	 * Sets the position of the entity in accordance with the
	 * binded map's boundaries. If position is not in bounds,
	 * the entity will not move.
	 * 
	 * @param x The x position where entity moves to (if in bounds)
	 * @param y The y position where entity moves to (if in bounds)
	 * @return If position was in bounds
	 */
	public boolean setPos(int x, int y) {
		if(containedMap.isValidPosition(x, y)) {
			posX = x;
			posY = y;
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Moves entity by an increment of x and y.
	 * 
	 * @param x The increment that entity moves horizontally
	 * @param y The increment that entity moves vertically
	 * @return If position was in bounds
	 */
	public boolean move(int x, int y) {
		return setPos(getPosX()+x,getPosY()+y);
	}
	
	/*
	 * Set's the object's position to the spawn point.
	 * Otherwise, attempt to go to the center of the map.
	 */
	public void goToSpawn() {
		if(!setPos(containedMap.getDefaultSpawnX(),containedMap.getDefaultSpawnY())) {
			setPos(containedMap.getWidth()/2,containedMap.getHeight()/2);
		}
	}
	
	/*
	 * Gets current X position of the entity.
	 * 
	 * @return Entitie's X position in contained map
	 */
	public int getPosX() {
		return posX;
	}
	
	/*
	 * Gets current Y position of the entity.
	 * 
	 * @return Entitie's Y position in contained map
	 */
	public int getPosY() {
		return posY;
	}
	
	/*
	 * Gets the current visibility of object within map
	 * 
	 * @return If object is visible on contained map
	 */
	public boolean getVisibility() {
		return visible;
	}
	
	/*
	 * Sets the visibility of player to map. This also
	 * hides the physical existence which prevents
	 * collisions and interactions with other entities.
	 * 
	 * @param visible Sets if object is visible
	 */
	public void setVisibility(boolean visible)  {
		this.visible = visible;
	}
	
	public void setLayer(int layer) {
		
	}
	public abstract int getLayer();
	public abstract String getType();
	public abstract String getPrintSymbol();
}
