package backEnd;

import backEnd.Map.Tile;
import entities.*;

public class Test {

	public static void main(String[] args) {
		Map cartman = new Map(6, Tile.STONE);
		Map kyle = new Map(6, Tile.GRASS);
		
		Player me = new Player(cartman);
		
		Stick yeeted = new Stick(cartman, 0, 0);

		System.out.print(cartman);
		System.out.println();
		System.out.print(kyle);
		System.out.println();
		
		yeeted.moveToMap(kyle);
		Stick boi = new Stick(kyle, 2, 2);
		Stick man = new Stick(kyle, 3, 2);

		System.out.print(cartman);
		System.out.println();
		System.out.print(kyle);
		System.out.println();
	}
}