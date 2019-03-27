package entities;

import backEnd.Map;

public class Player extends Entity{
	private int health;
	
	public Player(Map containedMap) {
		super(containedMap);
		this.printSymbol = "*";
	}
	
	public String getPrintSymbol() {
		return this.printSymbol;
	}
}