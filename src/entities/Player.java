package entities;

import backEnd.Map;

public class Player extends Entity{
	
	private int health;
	
	public Player(Map containedMap, int x, int y) {
		super(containedMap, x, y);
	}
	
	public Player(Map containedMap) {
		super(containedMap);
	}
	
	public String getPrintSymbol() {
		return "*";
	}
}