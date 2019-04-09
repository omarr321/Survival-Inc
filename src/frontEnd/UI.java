package frontEnd;

import backEnd.Map;
import backEnd.Map.Tile;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import backEnd.World;

public class UI extends Application {
	public static int playerX = 4;
	public static int playerY = 4;
	public Map colorMap = new Map();

	public static void main(String args[]) {
		launch(args);
	}

	public void start(Stage primarystage) {
		World world = new World(11);
		Map map = world.getMap(0, 0);
		map.tileFill(Tile.STONE);
		mainMap(primarystage, map);
	}

	public void mainMap(Stage primarystage, Map map) {
		subMap(primarystage, map);
	}

	public void subMap(Stage primarystage, Map map) {
		GridPane grid = new GridPane();
		Pane all = new Pane(grid);
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				Rectangle rect =  new Rectangle();
				rect.setStrokeWidth(0);
				rect.setHeight(50);
				rect.setWidth(50);
				Tile tile = map.getTile(i, j);
				
				rect.setFill(Color.rgb(tile.red, tile.green, tile.blue));
				
				grid.add(rect, i, j);
			}
		}
		grid.setGridLinesVisible(true);
		Scene scene = new Scene(all);
		primarystage.setScene(scene);
		primarystage.show();
		
	}
}

//for (int i = 0; i < 9; i++) {
//	for (int j = 0; j < 9; j++) {
//		int random = Math.toIntExact(Math.round(Math.random() * 9));
//		switch (random) {
//		case 0:
//		case 1:
//		case 2:
//		case 3:
//		case 4:
//		case 5:
//			map[j][i] = Grass;
//			break;
//		case 6:
//		case 7:
//			map[j][i] = Stone;
//			break;
//		case 8:
//		case 9:
//			map[j][i] = Water;
//		}
//	}
//}
//for (int l = 0; l <= 2; l++) {
//	for (int i = 0; i < 9; i++) {
//		for (int j = 0; j < 9; j++) {
//			map[j][i] = calcColor(j, i);
//		}
//	}
//}
//}
//
//public Color getColor(int x, int y) {
//return map[x][y];
//}
//
//private String getColorType(int x, int y) {
//if (map[x][y] == Grass) {
//	return "Grass";
//} else if (map[x][y] == Stone) {
//	return "Stone";
//} else if (map[x][y] == Water) {
//	return "Water";
//} else {
//	return null;
//}
//}
//
//private Color checkColor(Color c, int xOffSet, int yOffSet, int x, int y) {
//String temp = "";
//try {
//	temp = getColorType(x + xOffSet, y + yOffSet);
//} catch (ArrayIndexOutOfBoundsException ex) {
//}
//if (temp == "Grass") {
//	return Grass;
//} else if (temp == "Water") {
//	return Water;
//} else if (temp == "Stone") {
//	return Stone;
//} else {
//	return c;
//}
//}
//
//private Color calcColor(int x, int y) {
//int random = Math.toIntExact(Math.round(Math.random() * 23));
//switch (random) {
//case 0:
//	checkColor(Grass, -1, -1, x, y);
//case 1:
//	checkColor(Water, -1, -1, x, y);
//case 2:
//	checkColor(Stone, -1, -1, x, y);
//case 3:
//	checkColor(Grass, -1, 0, x, y);
//case 4:
//	checkColor(Water, -1, 0, x, y);
//case 5:
//	checkColor(Stone, -1, 0, x, y);
//case 6:
//	checkColor(Grass, -1, 1, x, y);
//case 7:
//	checkColor(Water, -1, 1, x, y);
//case 8:
//	checkColor(Stone, -1, 1, x, y);
//case 9:
//	checkColor(Grass, 0, 1, x, y);
//case 10:
//	checkColor(Water, 0, 1, x, y);
//case 11:
//	checkColor(Stone, 0, 1, x, y);
//case 12:
//	checkColor(Grass, 1, 1, x, y);
//case 13:
//	checkColor(Water, 1, 1, x, y);
//case 14:
//	checkColor(Stone, 1, 1, x, y);
//case 15:
//	checkColor(Grass, 1, 0, x, y);
//case 16:
//	checkColor(Water, 1, 0, x, y);
//case 17:
//	checkColor(Stone, 1, 0, x, y);
//case 18:
//	checkColor(Grass, 1, -1, x, y);
//case 19:
//	checkColor(Water, 1, -1, x, y);
//case 20:
//	checkColor(Stone, 1, -1, x, y);
//case 21:
//	checkColor(Grass, 0, -1, x, y);
//case 22:
//	checkColor(Water, 0, -1, x, y);
//case 23:
//	checkColor(Stone, 0, -1, x, y);
//default:
//	return Grass;
//}