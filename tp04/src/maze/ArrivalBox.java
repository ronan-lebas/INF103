package maze;

import java.awt.*;

/**
 * A type of MazeBox that represents the arrival point of the maze.
 */
public class ArrivalBox extends MazeBox {

	/**
	 * Constructor for an arrival box with a specified former label.
	 * 
	 * @param maze        the maze object
	 * @param x           the x-coordinate of the box in the maze
	 * @param y           the y-coordinate of the box in the maze
	 * @param formerLabel the former label of the box
	 */
	public ArrivalBox(Maze maze, int x, int y, String formerLabel) {
		super(maze, x, y, "A", formerLabel, Color.GREEN);
	}

	/**
	 * Constructor for an arrival box with default former label.
	 * 
	 * @param maze the maze object
	 * @param x    the x-coordinate of the box in the maze
	 * @param y    the y-coordinate of the box in the maze
	 */
	public ArrivalBox(Maze maze, int x, int y) {
		super(maze, x, y, "A", "E", Color.GREEN);
	}

	/**
	 * Returns whether the box is walkable or not. Arrival box is always walkable.
	 * 
	 * @return true
	 */
	public boolean isWalkable() {
		return true;
	}
}
