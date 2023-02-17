package maze;
import java.awt.*;

public class DepartureBox extends MazeBox {
	public DepartureBox(Maze maze, int x, int y) {
		super(maze, x, y, "D", Color.RED);
	}
	public boolean isWalkable() {
		return true;
	}
}
