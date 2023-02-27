package maze;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

import graph.Vertex;
/**
 * An abstract class representing a box in a maze.
 * Implements the Vertex interface for graph algorithms.
 */
public abstract class MazeBox implements Vertex {
	private Maze maze;
	private int x;
	private int y;
	private String label;
	private String formerLabel;

	/**
	 * Get the former label of the box.
	 * 
	 * @return The former label of the box.
	 */
	public String getFormerLabel() {
		return formerLabel;
	}

	/**
	 * Set the former label of the box.
	 * 
	 * @param formerLabel The former label of the box.
	 */
	public void setFormerLabel(String formerLabel) {
		this.formerLabel = formerLabel;
	}

	private Color color;
	private boolean selected;

	/**
	 * Check if the box is selected.
	 * 
	 * @return true if the box is selected, false otherwise, this attribute is used in the drag computations.
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Set whether the box is selected.
	 * 
	 * @param selected Whether the box is selected.
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Get the x-coordinate of the box in the maze.
	 * 
	 * @return The x-coordinate of the box.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the y-coordinate of the box in the maze.
	 * 
	 * @return The y-coordinate of the box.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Get the color of the box. We use this attribute so that the color is only known by the box and not by the maze.
	 * 
	 * @return The color of the box.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Get the label of the box.
	 * 
	 * @return The label of the box.
	 */
	public final String getLabel() {
		return this.label;
	}

	/**
	 * Check if the box is walkable.
	 * 
	 * @return true if the box is walkable, false otherwise.
	 */
	public abstract boolean isWalkable();

	/**
	 * Create a new MazeBox with the given parameters.
	 * 
	 * @param maze        The maze containing the box.
	 * @param x           The x-coordinate of the box.
	 * @param y           The y-coordinate of the box.
	 * @param label       The label of the box.
	 * @param formerLabel The former label of the box.
	 * @param color       The color of the box.
	 */
	public MazeBox(Maze maze, int x, int y, String label, String formerLabel, Color color) {
		this.maze = maze;
		this.x = x;
		this.y = y;
		this.label = label;
		this.formerLabel = formerLabel;
		this.color = color;

	}

}
