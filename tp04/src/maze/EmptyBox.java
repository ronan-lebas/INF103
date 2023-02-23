package maze;

import java.awt.*;

/**
 * A type of MazeBox that represents an empty space of the maze.
 */
public class EmptyBox extends MazeBox {

	/**
	 * Creates an empty box with the given coordinates and former label.
	 * 
	 * @param maze        the maze that this box belongs to
	 * @param x           the x-coordinate of this box
	 * @param y           the y-coordinate of this box
	 * @param formerLabel the former label of this box (can be null)
	 */
	public EmptyBox(Maze maze, int x, int y, String formerLabel) {
		super(maze, x, y, "E", formerLabel, Color.WHITE);
	}

	/**
	 * Creates an empty box with the given coordinates and a default former label of
	 * "E".
	 * 
	 * @param maze the maze that this box belongs to
	 * @param x    the x-coordinate of this box
	 * @param y    the y-coordinate of this box
	 */
	public EmptyBox(Maze maze, int x, int y) {
		super(maze, x, y, "E", "E", Color.WHITE);
	}

	/**
	 * Indicates whether or not this box is walkable.
	 * 
	 * @return true, because an empty box is always walkable
	 */
	public boolean isWalkable() {
		return true;
	}
}
