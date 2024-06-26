package maze;

import java.awt.*;
/**
 * A type of MazeBox that represents a wall of the maze.
 */
public class WallBox extends MazeBox {

	/**
	 * Constructs a new WallBox object with the given parameters.
	 * 
	 * @param maze        the maze that the box belongs to
	 * @param x           the x coordinate of the box in the maze
	 * @param y           the y coordinate of the box in the maze
	 * @param formerLabel the former label of the box before it became a wall box
	 */
	public WallBox(Maze maze, int x, int y, String formerLabel) {
		super(maze, x, y, "W", formerLabel, Color.BLACK);
	}

	/**
	 * Constructs a new WallBox object with the given parameters and a default label.
	 * 
	 * @param maze the maze that the box belongs to
	 * @param x    the x coordinate of the box in the maze
	 * @param y    the y coordinate of the box in the maze
	 */
	public WallBox(Maze maze, int x, int y) {
		super(maze, x, y, "W", "W", Color.BLACK);
		//default label is "W"
	}

	/**
	 * Returns false since a wall box is not walkable.
	 * 
	 * @return false
	 */
	public boolean isWalkable() {
		return false;
	}
}
