package maze;

public class WallBox extends MazeBox {
	public WallBox(Maze maze, int x, int y) {
		super(maze, x, y, "W");
	}
	public boolean isWalkable() {
		return false;
	}
}
