package maze;
import java.awt.*;

public class EmptyBox extends MazeBox {
	public EmptyBox(Maze maze, int x, int y) {
		super(maze, x, y, "E", Color.WHITE);
	}
	public boolean isWalkable() {
		return true;
	}
}
