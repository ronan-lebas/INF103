package maze;
import java.awt.*;

public class DepartureBox extends MazeBox {
	public DepartureBox(Maze maze, int x, int y,String formerLabel) {
		super(maze, x, y, "D", formerLabel, Color.RED);
	}
	public DepartureBox(Maze maze, int x, int y) {
		super(maze, x, y, "D", "E", Color.RED);
	}
	public boolean isWalkable() {
		return true;
	}
}
