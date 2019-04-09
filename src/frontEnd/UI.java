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
				if (tile.equals(Tile.GRASS)) {
					rect.setFill(Color.GREEN);
				} else if (tile.equals(Tile.STONE)) {
					rect.setFill(Color.GRAY);
				} else {
					rect.setFill(Color.BLUE);
				}
				grid.add(rect, i, j);
			}
		}
		grid.setGridLinesVisible(true);
		Scene scene = new Scene(all);
		primarystage.setScene(scene);
		primarystage.show();
		
	}
}