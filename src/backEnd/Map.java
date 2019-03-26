package backEnd;
import java.util.ArrayList;

import entities.*;

public class Map {

	public static final int DEFAULT_SIZE = 24;
	public static final Tile DEFAULT_TILE = Tile.GRASS;
	
	private int spawnX;
	private int spawnY;
	
	public enum Tile {
		STONE("S"),
		GRASS("G"),
		WATER("W");
		
		public final String printSymbol;
		
		Tile(String printSymbol) {
			this.printSymbol = printSymbol;
		}
	}
	
	Tile[][] tilegrid;
	ArrayList<Entity> entities;
	
	/*
	 * Constructor.
	 * 
	 * @param width The amount of tiles that expand horizontally
	 * @param height The amount of tiles that expand vertically
	 * @param fill The tile that will fill the entire map
	 */
	public Map(int width, int height, int spawnX, int spawnY, Tile fill) {
		this.entities = new ArrayList<Entity>();
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		tilegrid = new Tile[height][width];
		tileFill(fill);
	}
	
	public Map(int width, int height, Tile fill) {
		this(width, height, width/2, height/2, fill);
	}
	
	public Map(int size, Tile fill) {
		this(size, size, fill);
	}
	
	public Map(Tile fill) {
		this(DEFAULT_SIZE, DEFAULT_SIZE, fill);
	}
	
	public Map() {
		this(DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_TILE);
	}
	
	/*
	 * Sets a tile at a valid position
	 * 
	 * @param x The x location of the tile (must be in bounds)
	 * @param y The y location of the tile (must be in bounds)
	 */
	public void setTile(int x, int y, Tile tile) {
		if(isValidPosition(x, y)) {
			tilegrid[y][x] = tile;
		}
	}
	
	/*
	 * Gets a tile from a valid position
	 * 
	 * @param x The x location of the tile (must be in bounds)
	 * @param y The y location of the tile (must be in bounds)
	 * @return The tile at (x,y)
	 */
	public Tile getTile(int x, int y) {
		if(isValidPosition(x, y)) {
			return tilegrid[y][x];
		} else {
			return null;
		}
	}
	
	/*
	 * Sets all tiles on the map to fill.
	 * 
	 * @param fill The tile that will replace every tile on the map.
	 */
	public void tileFill(Tile fill) {
		for(int y = 0; y < getHeight(); y++) {
			for(int x = 0; x < getWidth(); x++) {
				setTile(x, y, fill);
			}
		}
	}
	
	/*
	 * Gets current width of the map in units of tiles
	 * 
	 * @return The amount of tiles that expand horizontally
	 */
	public int getWidth() {
		return tilegrid[0].length;
	}
	
	/*
	 * Gets current height of the map in units of tiles
	 * 
	 * @return The amount of tiles that expand vertically
	 */
	public int getHeight() {
		return tilegrid.length;
	}
	
	/*
	 * Checks if the provided position is in bounds of the map
	 * 
	 * @param x The x position on the map
	 * @param y The y position on the map
	 * @return if the (x,y) coordinate is in bounds of the map space
	 */
	public boolean isValidPosition(int x, int y) {
		return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
	}
	
	public int getDefaultSpawnX() {
		return spawnX;
	}
	
	public int getDefaultSpawnY() {
		return spawnY;
	}
	
	public void setDefaultSpawn(int x, int y) {
		if(isValidPosition(x, y)) {
			spawnX = x;
			spawnY = y;
		}
	}
	
	public Entity addEntity(Entity entity) {
		if(!entities.contains(entity)) {
			entities.add(entity);	
			return entity;
		} else {
			return null;
		}
	}
	
	public Entity removeEntity(Entity entity) {
		if(entities.contains(entity)) {
			entities.remove(entity);
			return entity;
		} else {
			return null;
		}
		
	}
	/*
	 * Returns a ASCII visual of the map.
	 * (Ex):
	 * 
	 * | [G][G][S] |
	 * | [G][S][S] |
	 * | [G][G][S] |
	 * 
	 * @return A string that is a printer friendly map visual.
	 */
	@Override
	public String toString() {
		StringBuilder grid = new StringBuilder();
		
		String[][] overlay = new String[getHeight()][getWidth()];
		
		for(int i = 0; i < entities.size(); i++) {
			overlay[entities.get(i).getPosY()][entities.get(i).getPosX()] = entities.get(i).printSymbol;
		}
		
		for(int y = 0; y < getHeight(); y++) {
			grid.append("| ");
			for(int x = 0; x < getWidth(); x++) {
				grid.append("[");
				
				if(overlay[y][x] != null) {
					grid.append(overlay[y][x]);
				} else {
					grid.append(getTile(x, y).printSymbol);
				}
				grid.append("]");
			}
			grid.append(" |\n");
		}
		
		return grid.toString();
	}
}
