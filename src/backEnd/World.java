package backEnd;

/*
 * 
 */
public class World {
	
	 public static final int DEFAULT_SIZE = 20;
	
	 private Map[][] mapgrid;
	 
	 public World(int width, int height) {
		 mapgrid = new Map[width][height];
		 for(int i = 0; i < mapgrid.length; i++) {
			 for(int j = 0; j < mapgrid[0].length; j++) {
				 this.mapgrid[i][j] = new Map();
			 }
		 }
	 }
	 
	 public World(int size) {
		 this(size, size);
	 }
	 
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
		 if(x >= 0 && x < mapgrid[0].length && y >= 0 && y < mapgrid.length) {
			 return mapgrid[y][x];
		 } else {
			 return null;
		 }
	 }