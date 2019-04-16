package backEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.Predicate;

import entities.*;

/*
 * A game environment for entities to exist in. Each Map instance
 * contains a 2D array of tiles. Tiles represent the terrain of
 * the map which is an enumerated list of selectable landscape.
 * 
 * Entities can be binded to a Map and stored in a linear array,
 * Here, they are provided methods that allows for interaction with
 * neighboring entities. The front end job of the Map is to provide
 * data to the UI class on all render-able entities and tiles and their
 * positions and shapes.
 * 
 * @author Wyatt Phillips
 */
public class Map {

	public static final int DEFAULT_SIZE = 24;
	public static final Tile DEFAULT_TILE = Tile.GRASS;
	public static final String ALL_ENTITIES = "ALL";

	private int spawnX;
	private int spawnY;
	private String printname = "Untitled Map";

	public enum Tile {
		STONE("S", true, 122, 118, 118),
		GRASS("G", true, 21, 156, 0),
		WATER("W", false, 20, 33, 212);
		
		public final String printSymbol;
		public final int red;
		public final int green;
		public final int blue;
		public final boolean walkable;
		
		Tile(String printSymbol, boolean walkable, int red, int green, int blue) {

			this.printSymbol = printSymbol;
			this.walkable = walkable;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}
	}

	private Tile[][] tilegrid;
	private ArrayList<Entity> entities;

	/*
	 * Constructor.
	 * 
	 * @param width The amount of tiles that expand horizontally
	 * @param height The amount of tiles that expand vertically
	 * @param spawnX The default spawn point X for all entities
	 * @param spawnY The default spawn point Y for all entities
	 * @param fill The tile that will fill the entire map
	 */
	public Map(int width, int height, int spawnX, int spawnY, Tile fill) {
		this.entities = new ArrayList<Entity>();

		this.spawnX = spawnX;
		this.spawnY = spawnY;
		tilegrid = new Tile[height][width];
		tileFill(fill);
	}

	/*
	 * Constructor. Spawn is automatically set to the center of the map.
	 * 
	 * @param width The amount of tiles that expand horizontally
	 * @param height The amount of tiles that expand vertically
	 * @param fill The tile that will fill the entire map
	 */
	public Map(int width, int height, Tile fill) {
		this(width, height, width / 2, height / 2, fill);
	}

	/*
	 * Constructor. Aspect ratio of map is 1:1 and spawns are auto set to map
	 * center.
	 * 
	 * @param size The amount of tiles that expand horizontally and vertically (size = width = height)
	 * @param fill The tile that will fill the entire map
	 */
	public Map(int size, Tile fill) {
		this(size, size, fill);
	}

	/*
	 * Constructor. Width and Height are both set to DEFAULT_SIZE and spawn is
	 * centered.
	 * 
	 * @param fill The tile that will fill the entire map
	 */
	public Map(Tile fill) {
		this(DEFAULT_SIZE, DEFAULT_SIZE, fill);
	}

	/*
	 * Constructor. Width and Height are both set to DEFAULT_SIZE, spawn is
	 * centered, and default tile is DEFAULT_TILE.
	 */
	public Map() {
		this(DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_TILE);
	}

	/*
	 * Get the printer friendly name of the map.
	 * 
	 * @return String name.
	 */
	public String getName() {
		return printname;
	}

	/*
	 * Sets the printer friendly name of the map.
	 * 
	 * @param name String name.
	 */
	public void setName(String name) {
		printname = name;
	}

	/*
	 * Sets a tile at a valid position
	 * 
	 * @param x The x location of the tile (must be in bounds)
	 * @param y The y location of the tile (must be in bounds)
	 */
	public void setTile(int x, int y, Tile tile) {
		if (isValidPosition(x, y)) {
			tilegrid[y][x] = tile;
		}
	}

	/*
	 * Gets a tile from a valid position
	 * 
	 * @param x The x location of the tile (must be in bounds)
	 * @param y The y location of the tile (must be in bounds)
	 * @return The tile at (x,y) or null if not in bounds.
	 */
	public Tile getTile(int x, int y) {
		if (isValidPosition(x, y)) {
			return tilegrid[y][x];
		} else {
			return null;
		}
	}

	/*
	 * Gets the most occuring tile in the map.
	 * 
	 * @return The tile that occurs the most in map.
	 */
	public Tile getAverageTile() {
		HashMap<Tile, Integer> hm = new HashMap<Tile, Integer>();
		for(int y = 0; y < getHeight(); y++) {
			for(int x = 0; x < getWidth(); x++) {
				Tile key = getTile(x, y);
				if(hm.containsKey(key)) {
					int value = hm.get(key);
					hm.put(key, value + 1);
				} else {
					hm.put(key, 0);
				}
			}
		}
		
		Entry<Tile, Integer> maxEntry = null;

		for (Entry<Tile, Integer> entry : hm.entrySet())
		{
		    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
		    {
		        maxEntry = entry;
		    }
		}
		
		return maxEntry.getKey();
	}

	/*
	 * Gets all entities on the map.
	 * 
	 * @param filter Pass a lambda function to filter entities by their properties.
	 * @return All entities on the map that meet the filter specs.
	 */
	public ArrayList<Entity> getAllEntities(Predicate<? super Entity> filter) {
		ArrayList<Entity> list = new ArrayList<>();
		for (int i = 0; i < entities.size(); i++) {
			list.add(entities.get(i));
		}

		list.removeIf(filter.negate());
		return list;
	}
	
	/*
	 * Gets all entities on the map.
	 * 
	 * @return All entities on the map
	 */
	public ArrayList<Entity> getAllEntities() {
		return getAllEntities(n -> true);
	}
	
	/*
	 * Gets all visible entities on the map that meet the filter requirement.
	 * 
	 * @param filter Pass a lambda function to filter entities by their properties.
	 * @return All visible entities on the map that meet filter.
	 */
	public ArrayList<Entity> getVisibleEntities(Predicate<? super Entity> filter) {
		return getAllEntities(n -> n.getVisibility() && filter.test(n));
	}

	/*
	 * Gets all visible entities on the map.
	 * 
	 * @return All visible entities on the map
	 */
	public ArrayList<Entity> getVisibleEntities() {
		return getAllEntities(n -> n.getVisibility());
	}
	
	/*
	 * Gets all entities that exists at a specific position and meet the filter requirements.
	 * 
	 * @param x The x position to look for entities.
	 * @param y The y position to look for entities.
	 * @param filter Pass a lambda function to filter entities by their properties.
	 * @return The list of entities found at that position.
	 */
	public ArrayList<Entity> getEntitiesAtPos(int x, int y, Predicate<? super Entity> filter) {
		return getAllEntities(n -> n.getPosX() == x && n.getPosY() == y && filter.test(n));
	}
	
	/*
	 * Gets all entities that exists at a specific position.
	 * 
	 * @param x The x position to look for entities.
	 * @param y The y position to look for entities.
	 * @return The list of entities found at that position.
	 */
	public ArrayList<Entity> getEntitiesAtPos(int x, int y) {
		return getAllEntities(n -> n.getPosX() == x && n.getPosY() == y);
	}
	
	/*
	 * Gets all entities that exists within the bounds of the map and meet the filter requirements.
	 * 
	 * @param x The x position of the upper-left corner of the bounds to start at
	 * @param y The y position of the upper-left corner of the bounds to start at
	 * @param w The width of the bounds in relation to x.
	 * @param h The height of the bounds in relation to y.
	 * @param filter Pass a lambda function to filter entities by their properties.
	 * @return The list of entities found in the bounds.
	 */
	public ArrayList<Entity> getEntitiesInBounds(int x, int y, int w, int h, Predicate<? super Entity> filter) {
		return getAllEntities(n -> n.getPosX() >= x && n.getPosX() <= x+w && n.getPosY() >= y && n.getPosY() <= y+h && filter.test(n));
	}

	/*
	 * Gets all entities that exists within the bounds of the map.
	 * 
	 * @param x The x position of the upper-left corner of the bounds to start at
	 * @param y The y position of the upper-left corner of the bounds to start at
	 * @param w The width of the bounds in relation to x.
	 * @param h The height of the bounds in relation to y.
	 * @return The list of entities found in the bounds.
	 */
	public ArrayList<Entity> getEntitiesInBounds(int x, int y, int w, int h) {
		return getAllEntities(
				n -> n.getPosX() >= x && n.getPosX() <= x + w && n.getPosY() >= y && n.getPosY() <= y + h);
	}

	/*
	 * Sets all tiles on the map to fill.
	 * 
	 * @param fill The tile that will replace every tile on the map.
	 */
	public void tileFill(Tile fill) {
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
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

	/*
	 * Obtains the default x spawn position for entities
	 * 
	 * @return x co-ordinate for spawn
	 */
	public int getDefaultSpawnX() {
		return spawnX;
	}

	/*
	 * Obtains the default y spawn position for entities
	 * 
	 * @return y co-ordinate for spawn
	 */
	public int getDefaultSpawnY() {
		return spawnY;
	}

	/*
	 * Sets the spawn location for all entities. Does not change position if co-ordinates
	 * are invalid to map.
	 * 
	 * @param x The x co-ordinate for spawn (Must be in bounds)
	 * @param y The y co-ordinate for spawn (Must be in bounds)
	 */
	public void setDefaultSpawn(int x, int y) {
		if (isValidPosition(x, y)) {
			spawnX = x;
			spawnY = y;
		}
	}

	/*
	 * Adds an entity to the list. Can only be called under the following
	 * conditions:
	 * 1) Entity must not exist on another Map's list.
	 * 2) Entity must have it's map reference set to this Map instance.
	 * 3) Position must be valid to render properly.
	 * 
	 * @param entity The entity to add.
	 * @return The entity added to the list.
	 */
	public Entity addEntity(Entity entity) {
		if (!entities.contains(entity)) {
			entities.add(entity);
			return entity;
		} else {
			return null;
		}
	}

	/*
	 * Removes the entity from the list. Assuming only one of the same reference
	 * exists.
	 * 
	 * @param entity The entity to remove.
	 * @return The entity removed from the list.
	 */
	public Entity removeEntity(Entity entity) {
		if (entities.contains(entity)) {
			entities.remove(entity);
			return entity;
		} else {
			return null;
		}

	}

	/*
	 * Returns a ASCII visual of the map. For debugging purposes of UI is not
	 * working. (Ex):
	 * 
	 * | [G][G][S] | | [G][S][S] | | [G][G][S] |
	 * 
	 * @return A string that is a printer friendly map visual.
	 */
	@Override
	public String toString() {

		// Separate all entities by position and determine if entities overlap by layer
		// presidence
		String[][] overlay = new String[getWidth()][getHeight()];
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				ArrayList<Entity> temp = getEntitiesAtPos(j, i);
				int index = 0;

				for (int k = 0; k < temp.size(); k++) {
					if (temp.get(k).getLayer() >= temp.get(index).getLayer()) {
						index = k;
					}
				}

				if (temp.size() > 0) {
					overlay[i][j] = temp.get(index).getPrintSymbol();
				}
			}
		}

		// Begin to print the viewport using overlay
		StringBuilder grid = new StringBuilder();
		for (int y = 0; y < getHeight(); y++) {
			grid.append("| ");
			for (int x = 0; x < getWidth(); x++) {
				grid.append("[");

				if (overlay[y][x] != null) {
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