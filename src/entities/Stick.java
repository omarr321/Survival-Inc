package entities;

import backEnd.Map;

public class Stick extends Entity {
	
	public Stick(Map containedMap, int x, int y) {
		super(containedMap, x, y);
	}
	
	public String getPrintSymbol() {
		return "/";
	}
}
