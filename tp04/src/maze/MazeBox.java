package maze;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

import graph.Vertex;

public abstract class MazeBox implements Vertex{
	private Maze maze;
	private int x;
	private int y;
	private String label;
	private Color color;
	private boolean selected;
    public boolean isSelected() {return selected;}
    public void setSelected(boolean selected) {this.selected = selected;}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Color getColor() {
		return color;
	}
	public final String getLabel() {
		return this.label;
	}
	public abstract boolean isWalkable();
	public MazeBox(Maze maze, int x, int y, String label, Color color) {
		this.maze = maze;
		this.x = x;
		this.y = y;
		this.label = label;
		this.color = color;

	}
	
}
