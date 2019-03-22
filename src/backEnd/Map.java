package backEnd;

import javafx.scene.paint.Color;

public class Map {
	private Color map_color;
	private Color[][] map = new Color[9][9];
	final private static Color Grass = Color.rgb(10, 151, 0);
	final private static Color Water = Color.rgb(0, 65, 245);
	final private static Color Stone = Color.rgb(141, 141, 141);

	public Map() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int random = Math.toIntExact(Math.round(Math.random() * 9));
				switch (random) {
				case 0:
					map[j][i] = Grass;
					break;
				case 1:
					map[j][i] = Grass;
					break;
				case 2:
					map[j][i] = Grass;
					break;
				case 3:
					map[j][i] = Grass;
					break;
				case 4:
					map[j][i] = Grass;
					break;
				case 5:
					map[j][i] = Grass;
					break;
				case 6:
					map[j][i] = Stone;
					break;
				case 7:
					map[j][i] = Stone;
					break;
				case 8:
					map[j][i] = Water;
					break;
				case 9:
					map[j][i] = Water;
				}
			}
		}
		for (int l = 0; l <= 2; l++) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					map[j][i] = calcColor(j, i);
				}
			}
		}
	}

	/**
	 * Returns a color that is the average of all tiles on the map
	 * 
	 * @return Average Color
	 */
	public Color getAverageColor() {
		double[] average = new double[3];
		
		// Get sum of all RBG values
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				average[0] += getColor(j,i).getRed();
				average[1] += getColor(j,i).getGreen();
				average[2] += getColor(j,i).getBlue();
			}
		}
		
		average[0] /= (double) (map.length * map[0].length);
		average[1] /= (double) (map.length * map[0].length);
		average[2] /= (double) (map.length * map[0].length);
		
		return new Color(average[0],average[1],average[2],1);
	}
	
	public Color getColor(int x, int y) {
		return map[x][y];
	}

	private String getColorType(int x, int y) {
		if (map[x][y] == Grass) {
			return "Grass";
		} else if (map[x][y] == Stone) {
			return "Stone";
		} else if (map[x][y] == Water) {
			return "Water";
		} else {
			return null;
		}
	}

	private Color calcColor(int x, int y) {
		int random = Math.toIntExact(Math.round(Math.random() * 23));
		String temp = "";

		switch (random) {
		case 0:
			try {
				temp = getColorType(x - 1, y - 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Grass;
			}
		case 1:
			try {
				temp = getColorType(x - 1, y - 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Water;
			}
		case 2:
			try {
				temp = getColorType(x - 1, y - 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Stone;
			}
		case 3:
			try {
				temp = getColorType(x - 1, y);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Grass;
			}
		case 4:
			try {
				temp = getColorType(x - 1, y);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Water;
			}
		case 5:
			try {
				temp = getColorType(x - 1, y);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Stone;
			}
		case 6:
			try {
				temp = getColorType(x - 1, y + 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Grass;
			}
		case 7:
			try {
				temp = getColorType(x - 1, y + 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Water;
			}
		case 8:
			try {
				temp = getColorType(x - 1, y + 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Stone;
			}
		case 9:
			try {
				temp = getColorType(x, y + 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Grass;
			}
		case 10:
			try {
				temp = getColorType(x, y + 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Water;
			}
		case 11:
			try {
				temp = getColorType(x, y + 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Stone;
			}
		case 12:
			try {
				temp = getColorType(x + 1, y + 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Grass;
			}
		case 13:
			try {
				temp = getColorType(x + 1, y + 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Water;
			}
		case 14:
			try {
				temp = getColorType(x + 1, y + 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Stone;
			}
		case 15:
			try {
				temp = getColorType(x + 1, y);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Grass;
			}
		case 16:
			try {
				temp = getColorType(x + 1, y);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Water;
			}
		case 17:
			try {
				temp = getColorType(x + 1, y);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Stone;
			}
		case 18:
			try {
				temp = getColorType(x + 1, y - 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Grass;
			}
		case 19:
			try {
				temp = getColorType(x + 1, y - 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Water;
			}
		case 20:
			try {
				temp = getColorType(x + 1, y - 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Stone;
			}
		case 21:
			try {
				temp = getColorType(x, y - 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Grass;
			}
		case 22:
			try {
				temp = getColorType(x, y - 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Water;
			}
		case 23:
			try {
				temp = getColorType(x, y - 1);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
			if (temp == "Grass") {
				return Grass;
			} else if (temp == "Water") {
				return Water;
			} else if (temp == "Stone") {
				return Stone;
			} else {
				return Stone;
			}
		default:
			return Grass;
		}
	}
}