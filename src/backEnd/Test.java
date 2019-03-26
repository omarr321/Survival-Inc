package backEnd;

import backEnd.Map.Tile;
import entities.*;

public class Test {

	public static void main(String[] args) {
		Map cartman = new Map(6, Tile.STONE);
		Map kyle = new Map(6, Tile.GRASS);
		
		Entity brick = new Entity(cartman, 3, 1);
		Entity stick = new Entity(cartman, 0, 3);

		System.out.print(cartman);
		System.out.println();
		
		stick.setPos(4, 3);
		
		System.out.print(cartman);
		System.out.println();
	}
}