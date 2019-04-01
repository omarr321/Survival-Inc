package entities;

import backEnd.Map;


public class Stick extends Entity {
	
	private int layer = 256;
	
	public Stick(Map containedMap, int x, int y) {
		super(containedMap, x, y);
		setLayer(0);
	}
	
	public Stick(Map containedMap) {
		super(containedMap);
		setLayer(0);
	}
	
	public String getPrintSymbol() {
		return "/";
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
}
