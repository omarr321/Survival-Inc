package frontEnd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import backEnd.Map;
import backEnd.Map.Tile;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import entities.Entity;
import entities.Player;
import entities.Rock;
import entities.Stick;
import entities.Tree;

public class UI extends Application {

	public static void main(String args[]) {
		launch(args);
	}

	public void start(Stage primarystage) {
		World world = new World(11, 11, 11, 11);
		Map map;
		for (int i = 0; i < 11; i++) {
				for (int j = 0; j < 11; j++) {
					map = world.getMap(i, j);
					genTerrain(map);
					for (int k = 0; k < 2; k++) {
						genEntity(map);
					}
					removeEntitys(map, 5, 5);
			}
		}
		map = world.getMap(0, 0);
		Player player = new Player(map, 5, 5);
		
		mainMap(primarystage, map, player);
	}

	private void mainMap(Stage primarystage, Map map, Player player) {
		subMap(primarystage, map, player);
	}

	private void subMap(Stage primarystage, Map map, Player player) {
		GridPane grid = new GridPane();
		Pane all = new Pane(grid);
		Circle you = new Circle();
		BorderPane playerPane = new BorderPane();
		you.setStroke(Color.BLACK);
		you.setFill(Color.RED);
		you.setStrokeWidth(2);
		you.setRadius(12);
		playerPane.setCenter(you);
		
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

		drawAll(map, grid);
		
		grid.add(playerPane, player.getPosY(), player.getPosX());
		grid.setGridLinesVisible(true);
		grid.setLayoutX(500 - 275);
		grid.setLayoutY(500 - 275);
		Scene scene = new Scene(all);

		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.LEFT) {
					if (map.getTile(player.getPosX() - 1, player.getPosY()) != Tile.WATER) {
						player.move(-1, 0);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.RIGHT) {
					if (map.getTile(player.getPosX() + 1, player.getPosY()) != Tile.WATER) {
						player.move(1, 0);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
					}
				}
				if (event.getCode() == KeyCode.UP) {
					if (map.getTile(player.getPosX(), player.getPosY() - 1) != Tile.WATER) {
						player.move(0, -1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
					}
				}

				if (event.getCode() == KeyCode.DOWN) {
					if (map.getTile(player.getPosX(), player.getPosY() + 1) != Tile.WATER) {
						player.move(0, 1);
						grid.getChildren().remove(playerPane);
						grid.add(playerPane, player.getPosX(), player.getPosY());
					}
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
				
				if(getTile(map, i, j, -1, -1) == Tile.GRASS) {
					tGrass++;
				} else if(getTile(map, i, j, -1, -1) == Tile.WATER) {
					tWater++;
				}
				
				if(getTile(map, i, j, 0, -1) == Tile.GRASS) {
					tGrass++;
				} else if(getTile(map, i, j, 0, -1) == Tile.WATER) {
					tWater++;
				}
				
				if(getTile(map, i, j, 1, -1) == Tile.GRASS) {
					tGrass++;
				} else if(getTile(map, i, j, 1, -1) == Tile.WATER) {
					tWater++;
				}
				
				if(getTile(map, i, j, -1, 0) == Tile.GRASS) {
					tGrass++;
				} else if(getTile(map, i, j, -1, 0) == Tile.WATER) {
					tWater++;
				}
				
				if(getTile(map, i, j, 1, 0) == Tile.GRASS) {
					tGrass++;
				} else if(getTile(map, i, j, 1, 0) == Tile.WATER) {
					tWater++;
				}
				
				if(getTile(map, i, j, -1, 1) == Tile.GRASS) {
					tGrass++;
				} else if(getTile(map, i, j, -1, 1) == Tile.WATER) {
					tWater++;
				}
				
				if(getTile(map, i, j, 0, 1) == Tile.GRASS) {
					tGrass++;
				} else if(getTile(map, i, j, 0, 1) == Tile.WATER) {
					tWater++;
				}
				
				if(getTile(map, i, j, 1, 1) == Tile.GRASS) {
					tGrass++;
				} else if(getTile(map, i, j, 1, 1) == Tile.WATER) {
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
		for(int i = 0; i < 25; i++) {
			int randomX = Math.toIntExact(Math.round(Math.random() * 10));
			int randomY = Math.toIntExact(Math.round(Math.random() * 10));
			
			if (checkIsEmpty(map, randomX, randomY)) {
				int temp = Math.toIntExact(Math.round(Math.random() * 2));
				switch (temp) {
				case 0:
					new Stick(map, randomX, randomY);
					 break;
				case 1:
					new Rock(map, randomX, randomY);
					break;
				case 2:
					new Tree(map, randomX, randomY);
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
			switch(random) {
			case 0:
			case 1:
			case 2:
				if (checkIsEmpty(map, entities.get(i).getPosX() + xOff, entities.get(i).getPosY() + yOff)) {
					new Tree(map, entities.get(i).getPosX() + xOff, entities.get(i).getPosY() + yOff);
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
			switch(random) {
			case 0:
				if (checkIsEmpty(map, entities.get(i).getPosX() + xOff, entities.get(i).getPosY() + yOff)) {
					new Rock(map, entities.get(i).getPosX() + xOff, entities.get(i).getPosY() + yOff);
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
		for(int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getPosX() == x && entities.get(i).getPosY() == y) {
				map.removeEntity(entities.get(i));
			}
		}
	}
}