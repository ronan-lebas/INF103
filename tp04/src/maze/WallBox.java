package maze;
import java.awt.*;

public class WallBox extends MazeBox {
	public WallBox(Maze maze, int x, int y, String formerLabel) {
		super(maze, x, y, "W", formerLabel, Color.BLACK);
	}
	public WallBox(Maze maze, int x, int y) {
		super(maze, x, y, "W", "W", Color.BLACK);
	}
	public boolean isWalkable() {
		return false;
	}
}
