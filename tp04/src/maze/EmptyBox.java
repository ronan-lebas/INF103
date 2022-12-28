package maze;

public class EmptyBox extends MazeBox {
	public EmptyBox(Maze maze, int x, int y) {
		super(maze, x, y, "E");
	}
	public boolean isWalkable() {
		return true;
	}
}
