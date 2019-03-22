package frontEnd;

import backEnd.Map;
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

public class UI extends Application {
	public static int playerX = 4;
	public static int playerY = 4;
	public Map colorMap = new Map();
	
	public static void main(String args[]) {
		launch(args);
	}

	public void start(Stage primarystage) {
		mainMap(primarystage);
	}

	public void mainMap(Stage primarystage) {
		BorderPane all = new BorderPane();
		Pane map = new Pane();
		GridPane mapGrid = new GridPane();
		Circle player = new Circle(22);
		// Test
		Rectangle debug = new Rectangle();
		debug.setX(32);
		debug.setY(32);
		debug.setWidth(32);
		debug.setHeight(32);
		debug.setStroke(Color.BLACK);
		debug.setFill(colorMap.getAverageColor());
		map.getChildren().add(debug);
		System.out.println(colorMap.getAverageColor().getRed());
		System.out.println(colorMap.getAverageColor().getGreen());
		System.out.println(colorMap.getAverageColor().getBlue());
		// Test
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Rectangle mapSqaure = new Rectangle();
				mapSqaure.setHeight(50);
				mapSqaure.setWidth(50);
				
				mapSqaure.setFill(colorMap.getColor(j, i));
				
				mapSqaure.setStrokeWidth(0);
				mapGrid.add(mapSqaure, j, i, 1, 1);
			}
		}
		
		mapGrid.setGridLinesVisible(true);
		
		map.getChildren().addAll(mapGrid, player);
		
		all.setCenter(map);
		all.setPadding(new Insets(0, 0, 0, 0));
		
		mapGrid.setLayoutX(275);
		mapGrid.setLayoutY(275);
		mapGrid.setHgap(0);
		mapGrid.setVgap(0);
		
		player.setFill(Color.RED);
		player.setStroke(Color.BLACK);
		player.setCenterX((playerX + 1) * 100);
		player.setCenterY((playerY + 1) * 100);
		
		player.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.A) {
					System.out.printf("PlayerX: %d%nPlayerY: %d%n", playerX, playerY);
				}
				
				if (event.getCode() == KeyCode.LEFT) {
					if (playerX > 0) {
						playerX = playerX - 1;
						player.setCenterX(player.getCenterX()-50);
					} else {
						System.out.println("Cant go that way!");
					}
				} else if (event.getCode() == KeyCode.RIGHT){
					if (playerX < 8) {
						playerX = playerX + 1;
						player.setCenterX(player.getCenterX()+50);
					} else {
						System.out.println("Cant go that way!");
					}
					
				} else if (event.getCode() == KeyCode.UP) {
					if (playerY > 0) {
						playerY = playerY - 1;
						player.setCenterY(player.getCenterY()-50);
					} else {
						System.out.println("Cant go that way!");
					}
				} else if (event.getCode() == KeyCode.DOWN) {
					if (playerY < 8) {
						playerY = playerY + 1;
						player.setCenterY(player.getCenterY()+50);
					} else {
						System.out.println("Cant go that way!");
					}
				}
			}
		});
		
		Scene scene = new Scene(all, 1000, 1000);
		primarystage.setTitle("Survival Inc.");
		primarystage.setScene(scene);
		primarystage.show();
		
		player.requestFocus();
	}
}