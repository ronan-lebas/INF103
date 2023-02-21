package maze;
import java.awt.*;

public class EmptyBox extends MazeBox {
	public EmptyBox(Maze maze, int x, int y, String formerLabel) {
		super(maze, x, y, "E", formerLabel, Color.WHITE);
	}
	public EmptyBox(Maze maze, int x, int y) {
		super(maze, x, y, "E", "E", Color.WHITE);
	}
	public boolean isWalkable() {
		return true;
	}
}
