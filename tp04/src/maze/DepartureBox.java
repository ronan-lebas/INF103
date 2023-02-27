package maze;

import java.awt.*;

/**
 * A type of MazeBox that represents the departure point of the maze.
 */
public class DepartureBox extends MazeBox {

	/**
	 * Constructs a new DepartureBox with the specified parameters.
	 * 
	 * @param maze        the maze that the box belongs to
	 * @param x           the x-coordinate of the box in the maze
	 * @param y           the y-coordinate of the box in the maze
	 * @param formerLabel the former label of the box
	 */
	public DepartureBox(Maze maze, int x, int y, String formerLabel) {
		super(maze, x, y, "D", formerLabel, Color.RED);
	}

	/**
	 * Constructs a new DepartureBox with the specified parameters and default label
	 * "E".
	 * 
	 * @param maze the maze that the box belongs to
	 * @param x    the x-coordinate of the box in the maze
	 * @param y    the y-coordinate of the box in the maze
	 */
	public DepartureBox(Maze maze, int x, int y) {
		super(maze, x, y, "D", "E", Color.RED);
		//the former label is "E" by default, so we can replace the departure box by an other box when we move it
	}

	/**
	 * Returns whether or not the DepartureBox is walkable.
	 * 
	 * @return true, as the DepartureBox is always walkable
	 */
	public boolean isWalkable() {
		return true;
	}
}
