package maze;
import java.awt.*;

public class ArrivalBox extends MazeBox {

	public ArrivalBox(Maze maze, int x, int y) {
		super(maze,x,y,"A",Color.GREEN);
	}

	public boolean isWalkable() {
		return true;
	}
}
