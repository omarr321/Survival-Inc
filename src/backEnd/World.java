package backEnd;

public class World {
	private Map[][] world_space;
	
	public World() {
		for(int i = 0; i < world_space.length; i++) {
			for(int j = 0; j < world_space[0].length; j++) {
				world_space[j][i] = new Map();
			}
		}
	}
}
