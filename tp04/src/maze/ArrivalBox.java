package maze;

public class ArrivalBox extends MazeBox {

	public ArrivalBox(Maze maze, int x, int y) {
		super(maze,x,y,"A");
	}

	public boolean isWalkable() {
		return true;
	}
}
