package frontEnd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import backEnd.Map.Tile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import backEnd.*;
import entities.*;

/*
 * Main driver and front-end of the game. Contains main method
 * and manages a world containing maps. Contains inherited Application
 * of JavaFX and provides rendering and keyboard control capabilites
 * 
 * @author Omar Radwan
 */
public class UI extends Application {
	private int stickI = 0;
	private int woodI = 0;
	private int stoneI = 0;
	private int plankI = 0;
	private Map dump = new Map();
	private Map _inv = new Map(11, 11, Tile.STONE);

	public static void main(String args[]) {
		launch(args);
	}

	public void start(Stage primarystage) {
		World world = new World(11, 11, 11, 11);
		Map mainMap = new Map(11, Tile.STONE);
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				mainMap.setTile(i, j, world.getMap(i, j).getAverageTile());
			}
		}
		Map map;
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				map = world.getMap(i, j);
				genTerrain(map);
				for (int k = 0; k < 2; k++) {
					genEntity(map);
				}
				removeEntitys(map, 5, 5);

				getMostEntity(map, i, j, mainMap);
			}
		}
		Player player = new Player(mainMap, 5, 5);

		mainMap(primarystage, mainMap, world, player);
	}

	private void mainMap(Stage primarystage, Map map, World world, Player player) {
		int[] nearLand = findLand(map, player);
		BorderPane all = new BorderPane();
		VBox info = new VBox();
		HBox health = new HBox();
		GridPane grid = new GridPane();
		HBox maps = new HBox(grid);
		maps.setAlignment(Pos.CENTER);
		Circle you = new Circle();
		BorderPane playerPane = new BorderPane();
		you.setStroke(Color.BLACK);
		you.setFill(Color.RED);
		you.setStrokeWidth(2);
		you.setRadius(12);
		playerPane.setCenter(you);
		info.setAlignment(Pos.CENTER);
		health.setAlignment(Pos.CENTER);
		Text title = new Text("Map: Main-map");
		player.setPlayerName("Player 1");
		Text name = new Text("Name: " + player.getPlayerName());
		Text amountHeart = new Text(50, 50, "");
		Text heal = new Text("Health: ");
		heal.setStyle("-fx-font: 24 Courier;");
		amountHeart.setStyle("-fx-font: 24 Courier;");
		name.setStyle("-fx-font: 24 Courier;");
		title.setStyle("-fx-font: 24 Courier;");

		health.getChildren().addAll(heal, amountHeart);
		info.getChildren().addAll(title, name, health);
		info.setPadding(new Insets(0, 0, 0, 0));
		for (int i = 0; i < map.getWidth(); i++) {
			for (int j = 0; j < map.getHeight(); j++) {
				BorderPane bPane = new BorderPane();
				bPane.setPrefSize(50, 50);
				Rectangle rect = new Rectangle();
				rect.setStrokeWidth(2);
				rect.setStroke(Color.RED);
				rect.setHeight(50);
				rect.setWidth(50);
				Tile tile = map.getTile(i, j);

				rect.setFill(Color.rgb(tile.red, tile.green, tile.blue));
				bPane.getChildren().add(rect);
				grid.add(bPane, i, j);
			}
		}

		try {
			drawAll(map, grid);
		} catch (IllegalArgumentException ex) {
			System.out.println("Error: columnIndex must be greater or equal to 0!");
		}

		VBox inva = new VBox();
		inva.setAlignment(Pos.CENTER_LEFT);

		HBox sticks = new HBox();
		HBox woods = new HBox();
		HBox stones = new HBox();
		HBox planks = new HBox();
		sticks.setAlignment(Pos.CENTER_LEFT);
		woods.setAlignment(Pos.CENTER_LEFT);
		stones.setAlignment(Pos.CENTER_LEFT);
		planks.setAlignment(Pos.CENTER_LEFT);

		Text invat = new Text("Inventory:");
		invat.setStyle("-fx-font: 24 Courier;");
		Text stickT = new Text(50, 50, "");
		stickT.setStyle("-fx-font: 24 Courier;");
		Text woodT = new Text(50, 50, "");
		woodT.setStyle("-fx-font: 24 Courier;");
		Text stoneT = new Text(50, 50, "");
		stoneT.setStyle("-fx-font: 24 Courier;");
		Text plankT = new Text(50, 50, "");
		plankT.setStyle("-fx-font: 24 Courier;");

		sticks.getChildren().add(stickT);
		woods.getChildren().add(woodT);
		stones.getChildren().add(stoneT);
		planks.getChildren().add(plankT);

		inva.getChildren().addAll(invat, stickT, woodT, stoneT, plankT);

		maps.setPadding(new Insets(0, 0, 0, 0));

		VBox mapsAinfo = new VBox();
		mapsAinfo.setPadding(new Insets(0, 25, 0, 220));
		mapsAinfo.setAlignment(Pos.CENTER);
		HBox mapsAinv = new HBox();
		mapsAinfo.getChildren().addAll(info, maps);
		mapsAinv.getChildren().addAll(mapsAinfo, inva);
		mapsAinv.setAlignment(Pos.TOP_LEFT);
		all.setCenter(mapsAinv);
		grid.add(playerPane, nearLand[0], nearLand[1]);
		player.setPos(nearLand[0], nearLand[1]);
		grid.setGridLinesVisible(true);
		grid.setLayoutX(225);
		grid.setLayoutY(225);

		EventHandler<ActionEvent> displayIn = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stickT.setText("Sticks: " + stickI);
				woodT.setText("Wood: " + woodI);
				stoneT.setText("Stone: " + stoneI);
				plankT.setText("Planks: " + plankI);

				final int heart = player.getHealth();
				amountHeart.setText(heart + "/100");
			}
		};

		Timeline in = new Timeline(new KeyFrame(Duration.millis(1), displayIn));
		in.setCycleCount(Timeline.INDEFINITE);
		in.play();

		Scene scene = new Scene(all);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.A) {
					if (map.getTile(player.getPosX() - 1, player.getPosY()) != Tile.WATER) {
						player.move(-1, 0);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.D) {
					if (map.getTile(player.getPosX() + 1, player.getPosY()) != Tile.WATER) {
						player.move(1, 0);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}
				if (event.getCode() == KeyCode.W) {
					if (map.getTile(player.getPosX(), player.getPosY() - 1) != Tile.WATER) {
						player.move(0, -1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.S) {
					if (map.getTile(player.getPosX(), player.getPosY() + 1) != Tile.WATER) {
						player.move(0, 1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.Q) {
					if (map.getTile(player.getPosX() - 1, player.getPosY() - 1) != Tile.WATER) {
						player.move(-1, -1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.E) {
					if (map.getTile(player.getPosX() + 1, player.getPosY() - 1) != Tile.WATER) {
						player.move(1, -1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.C) {
					if (map.getTile(player.getPosX() + 1, player.getPosY() + 1) != Tile.WATER) {
						player.move(1, 1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.Z) {
					if (map.getTile(player.getPosX() - 1, player.getPosY() + 1) != Tile.WATER) {
						player.move(-1, 1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.ENTER) {
					player.moveToMap(world.getMap(player.getPosX(), player.getPosY()));
					subMap(primarystage, world.getMap(player.getPosX(), player.getPosY()), world, player, map,
							player.getPosX(), player.getPosY());
				}

			}

		});

		primarystage.setScene(scene);
		primarystage.setWidth(1000);
		primarystage.setHeight(1000);
		primarystage.setTitle("Survival Inc.");
		primarystage.setResizable(false);
		primarystage.show();

	}

	private void subMap(Stage primarystage, Map map, World world, Player player, Map main, int xPos, int yPos) {

		int[] nearLand = findLand(map, player);
		BorderPane all = new BorderPane();
		VBox info = new VBox();
		HBox health = new HBox();
		GridPane grid = new GridPane();
		HBox maps = new HBox(grid);
		maps.setAlignment(Pos.CENTER);
		Circle you = new Circle();
		BorderPane playerPane = new BorderPane();
		you.setStroke(Color.BLACK);
		you.setFill(Color.RED);
		you.setStrokeWidth(2);
		you.setRadius(12);
		playerPane.setCenter(you);
		info.setAlignment(Pos.CENTER);
		health.setAlignment(Pos.CENTER);
		Text title = new Text("Map: Sub-Map " + "(" + xPos + "," + yPos + ")");
		player.setPlayerName("Omar Radwan");
		Text name = new Text("Name: " + player.getPlayerName());
		Text amountHeart = new Text(50, 50, "");
		Text heal = new Text("Health: ");
		heal.setStyle("-fx-font: 24 Courier;");
		amountHeart.setStyle("-fx-font: 24 Courier;");
		name.setStyle("-fx-font: 24 Courier;");
		title.setStyle("-fx-font: 24 Courier;");

		health.getChildren().addAll(heal, amountHeart);
		info.getChildren().addAll(title, name, health);
		info.setPadding(new Insets(0, 0, 0, 0));
		for (int i = 0; i < map.getWidth(); i++) {
			for (int j = 0; j < map.getHeight(); j++) {
				BorderPane bPane = new BorderPane();
				bPane.setPrefSize(50, 50);
				Rectangle rect = new Rectangle();
				rect.setStrokeWidth(0);
				rect.setHeight(50);
				rect.setWidth(50);
				Tile tile = map.getTile(i, j);

				rect.setFill(Color.rgb(tile.red, tile.green, tile.blue));
				bPane.getChildren().add(rect);
				grid.add(bPane, i, j);
			}
		}

		try {
			drawAll(map, grid);
		} catch (IllegalArgumentException ex) {
			System.out.println("Error: columnIndex must be greater or equal to 0!");
		}

		Map inv = player.getInventory();

		VBox inva = new VBox();
		inva.setAlignment(Pos.CENTER_LEFT);

		HBox sticks = new HBox();
		HBox woods = new HBox();
		HBox stones = new HBox();
		HBox planks = new HBox();
		sticks.setAlignment(Pos.CENTER_LEFT);
		woods.setAlignment(Pos.CENTER_LEFT);
		stones.setAlignment(Pos.CENTER_LEFT);
		planks.setAlignment(Pos.CENTER_LEFT);

		Text invat = new Text("Inventory:");
		invat.setStyle("-fx-font: 24 Courier;");
		Text stickT = new Text(50, 50, "");
		stickT.setStyle("-fx-font: 24 Courier;");
		Text woodT = new Text(50, 50, "");
		woodT.setStyle("-fx-font: 24 Courier;");
		Text stoneT = new Text(50, 50, "");
		stoneT.setStyle("-fx-font: 24 Courier;");
		Text plankT = new Text(50, 50, "");
		plankT.setStyle("-fx-font: 24 Courier;");

		sticks.getChildren().add(stickT);
		woods.getChildren().add(woodT);
		stones.getChildren().add(stoneT);
		planks.getChildren().add(plankT);

		inva.getChildren().addAll(invat, stickT, woodT, stoneT, plankT);

		maps.setPadding(new Insets(0, 0, 0, 0));

		VBox mapsAinfo = new VBox();
		mapsAinfo.setPadding(new Insets(0, 25, 0, 220));
		mapsAinfo.setAlignment(Pos.CENTER);
		HBox mapsAinv = new HBox();
		mapsAinfo.getChildren().addAll(info, maps);
		mapsAinv.getChildren().addAll(mapsAinfo, inva);
		mapsAinv.setAlignment(Pos.TOP_LEFT);
		all.setCenter(mapsAinv);
		grid.add(playerPane, nearLand[0], nearLand[1]);
		player.setPos(nearLand[0], nearLand[1]);
		grid.setGridLinesVisible(true);
		grid.setLayoutX(225);
		grid.setLayoutY(225);

		EventHandler<ActionEvent> hurtLava = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Entity> entities = map.getEntitiesAtPos(player.getPosX(), player.getPosY());
				for (int i = 0; i < entities.size(); i++) {
					if (entities.get(i) instanceof Lava) {
						player.setHealth(player.getHealth() - 5);
						System.out.print(player.getHealth());
					}
				}
				final int heart = player.getHealth();
				amountHeart.setText(heart + "/100");
			}
		};

		Timeline h = new Timeline(new KeyFrame(Duration.millis(1000), hurtLava));
		h.setCycleCount(Timeline.INDEFINITE);
		h.play();

		EventHandler<ActionEvent> displayIn = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stickT.setText("Sticks: " + stickI);
				woodT.setText("Wood: " + woodI);
				stoneT.setText("Stone: " + stoneI);
				plankT.setText("Planks: " + plankI);
			}
		};

		Timeline in = new Timeline(new KeyFrame(Duration.millis(1), displayIn));
		in.setCycleCount(Timeline.INDEFINITE);
		in.play();

		Scene scene = new Scene(all);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.A) {
					if (map.getTile(player.getPosX() - 1, player.getPosY()) != Tile.WATER) {
						player.move(-1, 0);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.D) {
					if (map.getTile(player.getPosX() + 1, player.getPosY()) != Tile.WATER) {
						player.move(1, 0);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}
				if (event.getCode() == KeyCode.W) {
					if (map.getTile(player.getPosX(), player.getPosY() - 1) != Tile.WATER) {
						player.move(0, -1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.S) {
					if (map.getTile(player.getPosX(), player.getPosY() + 1) != Tile.WATER) {
						player.move(0, 1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.Q) {
					if (map.getTile(player.getPosX() - 1, player.getPosY() - 1) != Tile.WATER) {
						player.move(-1, -1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.E) {
					if (map.getTile(player.getPosX() + 1, player.getPosY() - 1) != Tile.WATER) {
						player.move(1, -1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.C) {
					if (map.getTile(player.getPosX() + 1, player.getPosY() + 1) != Tile.WATER) {
						player.move(1, 1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.Z) {
					if (map.getTile(player.getPosX() - 1, player.getPosY() + 1) != Tile.WATER) {
						player.move(-1, 1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
						System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.ESCAPE) {
					player.moveToMap(main);
					mainMap(primarystage, main, world, player);
				}

				if (event.getCode() == KeyCode.F) {
					ArrayList<Entity> mapSq = map.getEntitiesAtPos(player.getPosX(), player.getPosY());

					for (int i = 0; i < mapSq.size(); i++) {
						if (mapSq.get(i) instanceof Stick) {
							new Stick(inv, 0, 0);
							stickI++;
							removeEntitys(map, player.getPosX(), player.getPosY());
						} else if (mapSq.get(i) instanceof Rock) {
							new Stone(inv, 0, 0);
							stoneI++;
							removeEntitys(map, player.getPosX(), player.getPosY());
						} else if (mapSq.get(i) instanceof Tree) {
							new Wood(inv, 0, 0);
							woodI++;
							removeEntitys(map, player.getPosX(), player.getPosY());
						}
					}

					subMap(primarystage, map, world, player, main, xPos, yPos);
				}

				if (event.getCode() == KeyCode.I) {
					player.moveToMap(_inv);
					invMap(primarystage, map, world, player, main, xPos, yPos);
				}
			}

		});

		primarystage.setScene(scene);
		primarystage.setWidth(1000);
		primarystage.setHeight(1000);
		primarystage.setTitle("Survival Inc.");
		primarystage.setResizable(false);
		primarystage.show();
	}

	private void invMap(Stage primarystage, Map map, World world, Player player, Map main, int xPos, int yPos) {
		int currentX = player.getPosX();
		int currentY = player.getPosY();

		player.setPos(0, 0);

		BorderPane playerPane = new BorderPane();
		playerPane.setCenter(playerInv());

		VBox all = new VBox();
		HBox grid = new HBox();
		GridPane inv = new GridPane();

		BorderPane _stick = new BorderPane();
		BorderPane _plank = new BorderPane();

		new Stick(_inv, 0, 0);
		new Plank(_inv, 1, 0);

		Text inva = new Text("Inventory:");
		inva.setStyle("-fx-font: 24 Courier;");

		Text craft = new Text("Crafting:");
		craft.setStyle("-fx-font: 24 courier;");

		Text stickT = new Text(50, 50, "");
		stickT.setStyle("-fx-font: 24 Courier;");
		Text woodT = new Text(50, 50, "");
		woodT.setStyle("-fx-font: 24 Courier;");
		Text stoneT = new Text(50, 50, "");
		stoneT.setStyle("-fx-font: 24 Courier;");
		Text plankT = new Text(50, 50, "");
		plankT.setStyle("-fx-font: 24 Courier;");

		EventHandler<ActionEvent> displayIn = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stickT.setText("Sticks: " + stickI);
				woodT.setText(" Wood: " + woodI);
				stoneT.setText(" Stone: " + stoneI);
				plankT.setText(" Planks: " + plankI);
			}
		};

		Timeline in = new Timeline(new KeyFrame(Duration.millis(1), displayIn));
		in.setCycleCount(Timeline.INDEFINITE);
		in.play();

		HBox sticks = new HBox();
		HBox woods = new HBox();
		HBox stones = new HBox();
		HBox planks = new HBox();

		sticks.getChildren().add(stickT);
		woods.getChildren().add(woodT);
		stones.getChildren().add(stoneT);
		planks.getChildren().add(plankT);

		HBox items = new HBox();

		items.getChildren().addAll(sticks, woods, stones, planks);

		grid.setAlignment(Pos.CENTER);

		all.getChildren().addAll(inva, items, craft, grid);

		all.setAlignment(Pos.CENTER);
		items.setAlignment(Pos.CENTER);

		for (int i = 0; i < _inv.getWidth(); i++) {
			for (int j = 0; j < _inv.getHeight(); j++) {
				BorderPane bPane = new BorderPane();
				bPane.setPrefSize(50, 50);
				Rectangle rect = new Rectangle();
				rect.setStrokeWidth(0);
				rect.setHeight(50);
				rect.setWidth(50);
				Tile tile = _inv.getTile(i, j);

				rect.setFill(Color.rgb(tile.red, tile.green, tile.blue));
				bPane.getChildren().add(rect);
				inv.add(bPane, i, j);
			}
		}

		_stick.setCenter(stick());
		inv.add(_stick, 0, 0);
		_plank.setCenter(plank());
		inv.add(_plank, 1, 0);

		inv.add(playerPane, 0, 0);
		inv.setGridLinesVisible(true);
		grid.getChildren().add(inv);

		Scene scene = new Scene(all);

		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.A) {
					player.move(-1, 0);
					inv.getChildren().remove(playerPane);
					inv.add(playerPane, player.getPosX(), player.getPosY());
					System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
				}

				if (event.getCode() == KeyCode.D) {
					player.move(1, 0);
					inv.getChildren().remove(playerPane);
					inv.add(playerPane, player.getPosX(), player.getPosY());
					System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
				}
				if (event.getCode() == KeyCode.W) {
					player.move(0, -1);
					inv.getChildren().remove(playerPane);
					inv.add(playerPane, player.getPosX(), player.getPosY());
					System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
				}

				if (event.getCode() == KeyCode.S) {
					player.move(0, 1);
					inv.getChildren().remove(playerPane);
					inv.add(playerPane, player.getPosX(), player.getPosY());
					System.out.printf("(%d,%d)%n", player.getPosX(), player.getPosY());
				}

				if (event.getCode() == KeyCode.ESCAPE) {
					player.moveToMap(map);
					player.setPos(currentX, currentY);
					subMap(primarystage, map, world, player, main, xPos, yPos);
				}

				if (event.getCode() == KeyCode.ENTER) {
					System.out.println("Enter has been pressed.");
					ArrayList<Entity> entities = _inv.getEntitiesAtPos(player.getPosX(), player.getPosY());
					Map inv = player.getInventory();
					ArrayList<Entity> invA = inv.getAllEntities();

					for (int i = 0; i < entities.size(); i++) {
						System.out.println("In for loop.");
						if (entities.get(i) instanceof Stick) {
							System.out.println("Stick");
							if (plankI >= 1) {
								plankI--;

								int temp = 1;
								while (temp > 0) {
									for (int j = 0; j < invA.size(); j++) {
										if (invA.get(j) instanceof Plank) {
											invA.get(j).moveToMap(dump);
											temp--;
											break;
										}
									}
								}

								stickI = stickI + 2;
								new Stick(inv, 0, 0);
								new Stick(inv, 0, 0);

							}
						} else if (entities.get(i) instanceof Plank) {
							System.out.println("Wood");
							if (woodI >= 1) {
								woodI--;

								int temp = 1;
								while (temp > 0) {
									for (int j = 0; j < invA.size(); j++) {
										if (invA.get(j) instanceof Wood) {
											invA.get(j).moveToMap(dump);
											temp--;
											break;
										}
									}
								}

								plankI = plankI + 4;
								new Plank(inv, 0, 0);
								new Plank(inv, 0, 0);
								new Plank(inv, 0, 0);
								new Plank(inv, 0, 0);
							}
						} else {
							System.out.println("Nothing");
						}
					}
				}
				
				if (event.getCode() == KeyCode.P) {
					printInv(player);
				}
			}
		});

		primarystage.setScene(scene);
		primarystage.setWidth(1000);
		primarystage.setHeight(1000);
		primarystage.setTitle("Survival Inc.");
		primarystage.setResizable(false);
		primarystage.show();
	}

	private void drawAll(Map map, GridPane grid) {
		ArrayList<Entity> entities = map.getVisibleEntities();
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof Stick) {
				BorderPane pane = new BorderPane();
				pane.setCenter(stick());
				grid.add(pane, entities.get(i).getPosX(), entities.get(i).getPosY());
			} else if (entities.get(i) instanceof Rock) {
				BorderPane pane = new BorderPane();
				BorderPane pane2 = new BorderPane();
				pane2.setCenter(rock());
				pane.setBottom(pane2);
				grid.add(pane, entities.get(i).getPosX(), entities.get(i).getPosY());
			} else if (entities.get(i) instanceof Tree) {
				BorderPane pane = new BorderPane();
				BorderPane pane2 = new BorderPane();
				pane2.setCenter(tree());
				pane.setBottom(pane2);
				grid.add(pane, entities.get(i).getPosX(), entities.get(i).getPosY());
			} else if (entities.get(i) instanceof Lava) {
				BorderPane pane = new BorderPane();
				pane.setCenter(lava());
				grid.add(pane, entities.get(i).getPosX(), entities.get(i).getPosY());
			}
		}
	}

	private ImageView stick() {
		ImageView stickV = null;
		try {
			FileInputStream inputStream = new FileInputStream("src/assets/Stick.png");
			Image stick = new Image(inputStream);
			stickV = new ImageView(stick);
		} catch (FileNotFoundException e) {
			System.out.print("Error: Stick.png not found");
		}

		return stickV;
	}

	private ImageView rock() {
		ImageView rockV = null;
		try {
			FileInputStream inputStream = new FileInputStream("src/assets/Rock.png");
			Image rock = new Image(inputStream);
			rockV = new ImageView(rock);
		} catch (FileNotFoundException e) {
			System.out.print("Error: Rock.png not found");
		}

		return rockV;
	}

	private ImageView tree() {
		ImageView treeV = null;
		try {
			FileInputStream inputStream = new FileInputStream("src/assets/Tree.png");
			Image tree = new Image(inputStream);
			treeV = new ImageView(tree);
		} catch (FileNotFoundException e) {
			System.out.print("Error: Tree.png not found");
		}

		return treeV;
	}

	private ImageView lava() {
		ImageView lavaV = null;
		try {
			FileInputStream inputStream = new FileInputStream("src/assets/Lava.png");
			Image lava = new Image(inputStream);
			lavaV = new ImageView(lava);
		} catch (FileNotFoundException e) {
			System.out.print("Error: Lava.png not found");
		}

		return lavaV;
	}

	private ImageView plank() {
		ImageView plankV = null;
		try {
			FileInputStream inputStream = new FileInputStream("src/assets/Plank.png");
			Image plank = new Image(inputStream);
			plankV = new ImageView(plank);
		} catch (FileNotFoundException e) {
			System.out.print("Error: Plank.png not found");
		}

		return plankV;
	}

	private ImageView playerInv() {
		ImageView playerV = null;
		try {
			FileInputStream inputStream = new FileInputStream("src/assets/PlayerInv.png");
			Image player = new Image(inputStream);
			playerV = new ImageView(player);
		} catch (FileNotFoundException e) {
			System.out.print("Error: PlayerInv.png not found");
		}

		return playerV;
	}

	private void genTerrain(Map map) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int random = Math.toIntExact(Math.round(Math.random() * 9));
				switch (random) {
				case 0:
				case 1:
				case 2:
				case 3:
					map.setTile(i, j, Tile.GRASS);
					break;
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
					map.setTile(i, j, Tile.WATER);
				}
			}
		}
		for (int l = 0; l <= 3; l++) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					map.setTile(i, j, calcTile(map, i, j));
				}
			}
		}

		removeSingles(map);
	}

	private Tile calcTile(Map map, int x, int y) {
		int random = Math.toIntExact(Math.round(Math.random() * 7));
		switch (random) {
		case 0:
			Tile tile = getTile(map, x, y, -1, -1);
			if (tile == Tile.GRASS) {
				return Tile.GRASS;
			} else if (tile == Tile.WATER) {
				return Tile.WATER;
			} else {
				return Tile.GRASS;
			}
		case 1:
			tile = getTile(map, x, y, 0, -1);
			if (tile == Tile.GRASS) {
				return Tile.GRASS;
			} else if (tile == Tile.WATER) {
				return Tile.WATER;
			} else {
				return Tile.GRASS;
			}
		case 2:
			tile = getTile(map, x, y, 1, -1);
			if (tile == Tile.GRASS) {
				return Tile.GRASS;
			} else if (tile == Tile.WATER) {
				return Tile.WATER;
			} else {
				return Tile.GRASS;
			}
		case 3:
			tile = getTile(map, x, y, -1, 0);
			if (tile == Tile.GRASS) {
				return Tile.GRASS;
			} else if (tile == Tile.WATER) {
				return Tile.WATER;
			} else {
				return Tile.GRASS;
			}
		case 4:
			tile = getTile(map, x, y, 1, 0);
			if (tile == Tile.GRASS) {
				return Tile.GRASS;
			} else if (tile == Tile.WATER) {
				return Tile.WATER;
			} else {
				return Tile.GRASS;
			}
		case 5:
			tile = getTile(map, x, y, -1, 1);
			if (tile == Tile.GRASS) {
				return Tile.GRASS;
			} else if (tile == Tile.WATER) {
				return Tile.WATER;
			} else {
				return Tile.GRASS;
			}
		case 6:
			tile = getTile(map, x, y, 0, 1);
			if (tile == Tile.GRASS) {
				return Tile.GRASS;
			} else if (tile == Tile.WATER) {
				return Tile.WATER;
			} else {
				return Tile.GRASS;
			}
		case 7:
			tile = getTile(map, x, y, 1, 1);
			if (tile == Tile.GRASS) {
				return Tile.GRASS;
			} else if (tile == Tile.WATER) {
				return Tile.WATER;
			} else {
				return Tile.GRASS;
			}
		default:
			return Tile.GRASS;
		}
	}

	private Tile getTile(Map map, int x, int y, int xOff, int yOff) {
		return map.getTile(x + xOff, y + yOff);
	}

	private void removeSingles(Map map) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int tWater = 0;
				int tGrass = 0;

				if (getTile(map, i, j, -1, -1) == Tile.GRASS) {
					tGrass++;
				} else if (getTile(map, i, j, -1, -1) == Tile.WATER) {
					tWater++;
				}

				if (getTile(map, i, j, 0, -1) == Tile.GRASS) {
					tGrass++;
				} else if (getTile(map, i, j, 0, -1) == Tile.WATER) {
					tWater++;
				}

				if (getTile(map, i, j, 1, -1) == Tile.GRASS) {
					tGrass++;
				} else if (getTile(map, i, j, 1, -1) == Tile.WATER) {
					tWater++;
				}

				if (getTile(map, i, j, -1, 0) == Tile.GRASS) {
					tGrass++;
				} else if (getTile(map, i, j, -1, 0) == Tile.WATER) {
					tWater++;
				}

				if (getTile(map, i, j, 1, 0) == Tile.GRASS) {
					tGrass++;
				} else if (getTile(map, i, j, 1, 0) == Tile.WATER) {
					tWater++;
				}

				if (getTile(map, i, j, -1, 1) == Tile.GRASS) {
					tGrass++;
				} else if (getTile(map, i, j, -1, 1) == Tile.WATER) {
					tWater++;
				}

				if (getTile(map, i, j, 0, 1) == Tile.GRASS) {
					tGrass++;
				} else if (getTile(map, i, j, 0, 1) == Tile.WATER) {
					tWater++;
				}

				if (getTile(map, i, j, 1, 1) == Tile.GRASS) {
					tGrass++;
				} else if (getTile(map, i, j, 1, 1) == Tile.WATER) {
					tWater++;
				}

				if (tWater == 0) {
					map.setTile(i, j, Tile.GRASS);
				} else if (tGrass == 0) {
					map.setTile(i, j, Tile.WATER);
				}
			}
		}
	}

	private void genEntity(Map map) {
		for (int i = 0; i < 25; i++) {
			int randomX = Math.toIntExact(Math.round(Math.random() * 10));
			int randomY = Math.toIntExact(Math.round(Math.random() * 10));

			if (checkIsEmpty(map, randomX, randomY)) {
				int temp = Math.toIntExact(Math.round(Math.random() * 3));
				switch (temp) {
				case 0:
					new Stick(map, randomX, randomY);
					break;
				case 1:
					new Rock(map, randomX, randomY);
					break;
				case 2:
					new Tree(map, randomX, randomY);
					break;
				case 3:
					new Lava(map, randomX, randomY);
				}
			}
		}

		removeOnWater(map);
		for (int i = 0; i < 2; i++) {
			entityAlgorithm(map);
		}
	}

	private void entityAlgorithm(Map map) {
		ArrayList<Entity> entities = map.getVisibleEntities();

		for (int i = 0; i < entities.size(); i++) {
			addEntity(map, 0, 1, entities, i);
			addEntity(map, 1, 0, entities, i);
			addEntity(map, 0, -1, entities, i);
			addEntity(map, -1, 0, entities, i);
		}
	}

	private void addEntity(Map map, int xOff, int yOff, ArrayList<Entity> entities, int i) {
		int random = Math.toIntExact(Math.round(Math.random() * 9));
		if (entities.get(i) instanceof Tree) {
			switch (random) {
			case 0:
			case 1:
			case 2:
				if (checkIsEmpty(map, entities.get(i).getPosX() + xOff, entities.get(i).getPosY() + yOff)) {
					if (entities.get(i).getPosX() + xOff >= 0 && entities.get(i).getPosX() + xOff < 11) {
						if (entities.get(i).getPosY() + yOff >= 0 && entities.get(i).getPosY() + yOff < 11) {
							new Tree(map, entities.get(i).getPosX() + xOff, entities.get(i).getPosY() + yOff);
						}
					}
				}
				break;
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			}
		} else if (entities.get(i) instanceof Rock) {
			switch (random) {
			case 0:
			case 1:
				if (checkIsEmpty(map, entities.get(i).getPosX() + xOff, entities.get(i).getPosY() + yOff)) {
					if (entities.get(i).getPosX() + xOff >= 0 && entities.get(i).getPosX() + xOff < 11) {
						if (entities.get(i).getPosY() + yOff >= 0 && entities.get(i).getPosY() + yOff < 11) {
							new Rock(map, entities.get(i).getPosX() + xOff, entities.get(i).getPosY() + yOff);
						}
					}
				}
				break;
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			}
		} else if (entities.get(i) instanceof Lava) {
			switch (random) {
			case 0:
				if (checkIsEmpty(map, entities.get(i).getPosX() + xOff, entities.get(i).getPosY() + yOff)) {
					if (entities.get(i).getPosX() + xOff >= 0 && entities.get(i).getPosX() + xOff < 11) {
						if (entities.get(i).getPosY() + yOff >= 0 && entities.get(i).getPosY() + yOff < 11) {
							new Lava(map, entities.get(i).getPosX() + xOff, entities.get(i).getPosY() + yOff);
						}
					}
				}
				break;
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			}
		} else if (entities.get(i) instanceof Stick) {

		}

		removeOnWater(map);
	}

	private void removeOnWater(Map map) {
		ArrayList<Entity> entities = map.getVisibleEntities();

		for (int i = 0; i < entities.size(); i++) {
			if (map.getTile(entities.get(i).getPosX(), entities.get(i).getPosY()) == Tile.WATER) {
				map.removeEntity(entities.get(i));
			}
		}
	}

	private boolean checkIsEmpty(Map map, int x, int y) {
		if (map.getEntitiesAtPos(x, y).isEmpty() == true) {
			return true;
		} else {
			return false;
		}
	}

	private void removeEntitys(Map map, int x, int y) {
		ArrayList<Entity> entities = map.getVisibleEntities();
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getPosX() == x && entities.get(i).getPosY() == y) {
				if (entities.get(i) instanceof Player) {
				} else {
					entities.get(i).moveToMap(dump);
				}
			}
		}
	}

	private int[] findLand(Map world, Player player) {
		int dir = 0;
		boolean currentNum = false;
		int num = 1;
		int current = 1;
		int max = 11;
		int currentX = player.getPosX();
		int currentY = player.getPosY();

		while (num <= max) {
			if (world.getTile(currentX, currentY) == Tile.GRASS) {
				int[] temp = { currentX, currentY };
				return temp;
			} else {
				if (currentNum == false) {
					while (current <= num) {
						System.out.printf("(%d,%d)%n", currentX, currentY);
						switch (dir) {
						case 0:
							currentX++;
							current++;
							break;
						case 1:
							currentY--;
							current++;
							break;
						case 2:
							currentX--;
							current++;
							break;
						case 3:
							currentY++;
							current++;
						}
					}
					dir++;
					dir = dir % 3;

					current = 1;

					currentNum = true;
				} else {
					while (current <= num) {
						System.out.printf("(%d,%d)%n", currentX, currentY);
						switch (dir) {
						case 0:
							currentX++;
							current++;
							break;
						case 1:
							currentY--;
							current++;
							break;
						case 2:
							currentX--;
							current++;
							break;
						case 3:
							currentY++;
							current++;
						}
					}
					num++;
					current = 1;

					dir++;
					dir = dir % 3;
					currentNum = false;
				}
			}
		}
		int[] temp = { 5, 5 };
		return temp;
	}

	private void getMostEntity(Map map, int x, int y, Map main) {
		ArrayList<Entity> entities = map.getVisibleEntities();
		int stick = 0, tree = 0, rock = 0, lava = 0;
		int check = 30;
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof Stick) {
				stick++;
			} else if (entities.get(i) instanceof Rock) {
				rock++;
			} else if (entities.get(i) instanceof Tree) {
				tree++;
			} else if (entities.get(i) instanceof Lava) {
				lava++;
			}
		}

		if (stick >= tree && stick >= rock && stick >= lava) {
			if (stick >= check) {
				new Stick(main, x, y);
			}
		} else if (tree >= stick && tree >= rock && tree >= lava) {
			if (tree >= check) {
				new Tree(main, x, y);
			}
		} else if (rock >= stick && rock >= tree && rock >= lava) {
			if (rock >= check) {
				new Rock(main, x, y);
			}
		} else if (lava >= stick && lava >= tree && lava >= rock) {
			new Lava(main, x, y);
		}
	}

	private void printInv(Player player) {
		ArrayList<Entity> entities = player.getInventory().getAllEntities();
		
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof Stick){
				System.out.printf("(Stick)%n");
			} else if (entities.get(i) instanceof Stone){
				System.out.printf("(Stone)%n");
			} else if (entities.get(i) instanceof Wood){
				System.out.printf("(Wood)%n");
			} else if (entities.get(i) instanceof Plank){
				System.out.printf("(Plank)%n");
			}
		}
	}
}