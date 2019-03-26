package entities;
import backEnd.*;

public class Entity {
	private Map containedMap;
	
	private int posX;
	private int posY;
	public final String printSymbol;
	
	public Entity(Map containedMap, int x, int y) {
		this.posX = x;
		this.posY = y;
		this.containedMap = containedMap;
		this.printSymbol = "*";
		setPos(getPosX(), getPosY());
		containedMap.addEntity(this);
	}
	
	public Entity(Map containedMap) {
		this(containedMap, containedMap.getDefaultSpawnX(), containedMap.getDefaultSpawnY());
	}
	
	public void moveToMap(Map map) {
		containedMap.removeEntity(this);
		containedMap = map;
		setPos(getPosX(), getPosY());
		containedMap.addEntity(this);
	}
	
	public void goToSpawn() {
		if(containedMap.isValidPosition(containedMap.getDefaultSpawnX(), containedMap.getDefaultSpawnY())) {
			posX = containedMap.getDefaultSpawnX();
			posY = containedMap.getDefaultSpawnY();
		} else {
			posX = containedMap.getWidth()/2;
			posY = containedMap.getHeight()/2;
		}
	}
	
	public void setPos(int x, int y) {
		if(containedMap.isValidPosition(x, y)) {
			posX = x;
			posY = y;
		} else {
			goToSpawn();
		}
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
}
