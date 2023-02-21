package maze;
import java.awt.*;

public class ArrivalBox extends MazeBox {

	public ArrivalBox(Maze maze, int x, int y, String formerLabel) {
		super(maze,x,y,"A", formerLabel, Color.GREEN);
	}
	public ArrivalBox(Maze maze, int x, int y) {
		super(maze,x,y,"A", "E", Color.GREEN);
	}

	public boolean isWalkable() {
		return true;
	}
}
