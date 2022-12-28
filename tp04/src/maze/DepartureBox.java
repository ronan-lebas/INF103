package maze;

public class DepartureBox extends MazeBox {
	public DepartureBox(Maze maze, int x, int y) {
		super(maze, x, y, "D");
	}
	public boolean isWalkable() {
		return true;
	}
}
