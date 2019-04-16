package backEnd;

import backEnd.Map.Tile;

/*
 * A grid container of Map instances. A world instance instantiates a 2D array of
 * maps. The primary method in world is getMap, which returms a map instance at
 * a certain position within the world.
 * 
 * @author Wyatt Phillips
 */
public class World {

	public static final int DEFAULT_SIZE = 20;

	private Map[][] mapgrid;

	/*
	 * Constructor.
	 * 
	 * @param width the amount of maps spanning horizontally
	 * @param height the amount of maps spanning vertically
	 * @param map_width the size of each map in terms of tiles spanning horizontally
	 * @param map_height the size of each map in terms of tiles spanning vertically
	 */
	public World(int width, int height, int map_width, int map_height) {
		mapgrid = new Map[width][height];
		for (int i = 0; i < mapgrid.length; i++) {
			for (int j = 0; j < mapgrid[0].length; j++) {
				this.mapgrid[i][j] = new Map(map_width, map_height, Tile.GRASS);
			}
		}
	}
	
	/*
	 * Constructor. The size of each map will be a 1:1 aspect ratio based on map_size.
	 * 
	 * @param width the amount of maps spanning horizontally
	 * @param height the amount of maps spanning vertically
	 * @param map_size the width and height of the maps
	 */
	public World(int width, int height, int map_size) {
		this(width, height, map_size, map_size);
	}
	
	/*
	 * Constructor. The width and height of each map will be set to DEFAULT_SIZE.
	 * 
	 * @param width the amount of maps spanning horizontally
	 * @param height the amount of maps spanning vertically
	 */
	public World(int width, int height) {
		this(width, height, DEFAULT_SIZE);
	}

	/*
	 * Constructor. The width and height of each map will be set to DEFAULT_SIZE and width and height of world
	 * will be set to size.
	 * 
	 * @param size the width and height of the world.
	 */
	public World(int size) {
		this(size, size);
	}

	/*
	 * Constructor. The size of each map and the world will be set to DEFAULT_SIZE.
	 */
	public World() {
		this(DEFAULT_SIZE);
	}

	/*
	 * Obtains the map at a position on the world grid
	 * 
	 * @param x The x position of the map on grid
	 * @param y The y position of the map on grid
	 * @return The map at that position
	 */
	public Map getMap(int x, int y) {
		if (x >= 0 && x < mapgrid[0].length && y >= 0 && y < mapgrid.length) {
			return mapgrid[y][x];
		} else {
			return null;
		}
	}
}
