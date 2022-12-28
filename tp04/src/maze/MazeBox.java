package maze;
import java.util.ArrayList;

import graph.Vertex;

public abstract class MazeBox implements Vertex{
	private Maze maze;
	private int x;
	private int y;
	private String label;
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public final String getLabel() {
		return this.label;
	}
	public abstract boolean isWalkable();
	public MazeBox(Maze maze, int x, int y, String label) {
		this.maze = maze;
		this.x = x;
		this.y = y;
		this.label = label;

	}
	
}
