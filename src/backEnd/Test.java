package backEnd;

import java.util.ArrayList;

import backEnd.Map.Tile;
import entities.*;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		Map world = new Map(10, Tile.STONE);
		new Stick(world, 2, 1);
		new Stick(world, 3, 1);
		new Stick(world, 5, 1);
		new Stick(world, 4, 3);
		new Stick(world, 6, 6);
		
		
		
		System.out.print(world);
	}
}