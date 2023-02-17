package maze;
import java.awt.*;

public class WallBox extends MazeBox {
	public WallBox(Maze maze, int x, int y) {
		super(maze, x, y, "W", Color.BLACK);
	}
	public boolean isWalkable() {
		return false;
	}
}
