package backEnd;

import java.util.ArrayList;
import java.util.Scanner;

import backEnd.Map.Tile;
import entities.*;

public class Test {
	
	public static void printGUI(Map map, Player player) {
		StringBuilder items = new StringBuilder();
		ArrayList<Entity> stuff = map.getEntitiesAtPos(player.getPosX(), player.getPosY());
		stuff.remove(player);
		for(int i = 0; i < stuff.size(); i++) {
			items.append(stuff.get(i).getType());
			items.append(" ");
		}
		
		System.out.printf("%n%nPlayer: %s | Health: %d | Items: %s%n", player.getPlayerName(), player.getHealth(), items);
		System.out.print(map);
		System.out.printf("XP: %d | Command: ", player.getXP());
	}
	
	public static void generateScene(Map map, int iterations) {
		for(int i = 0; i < iterations; i++) {
			new Tree(map, (int) (Math.random()*map.getWidth()), (int) (Math.random()*map.getHeight()));
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		boolean exit = false;
		
		Map world = new Map(12, 12, 1, 1, Tile.STONE);
		world.setName("Cedar Point");
		Player me = new Player(world);
		
		generateScene(world, 20);
		
		System.out.print("Name your player: ");
		me.setPlayerName(sc.nextLine());
		
		while(!exit) {
			printGUI(world, me);
			String com = sc.next();
			switch (com) {
				case "w": 	me.move(0, -1);
							break;
				case "a": 	me.move(-1, 0);
							break;
				case "s": 	me.move(0, 1);
							break;
				case "d": 	me.move(1, 0);
							break;
				case "q":	exit = true;
							break;
			}
		}	
	}
}