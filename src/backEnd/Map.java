package backEnd;

import javafx.scene.paint.Color;

public class Map {
	private Color[][] map = new Color[9][9];
	final private static Color Grass = Color.rgb(10, 151, 0);
	final private static Color Water = Color.rgb(0, 65, 245);
	final private static Color Stone = Color.rgb(141, 141, 141);
	
	public Map() {
		for (int i = 0; i < 9; i++) {
			for (int j= 0; j < 9; j++) {
				int random = Math.toIntExact(Math.round(Math.random() * 2));
				switch(random) {
				case 0:
					map[j][i] = Grass;
					break;
				case 1:
					map[j][i] = Water;
					break;
				case 2:
					map[j][i] = Stone;
				}
			}
		}
	}
	
	public Color getColor(int x, int y) {
		return map[x][y];
	}
}
