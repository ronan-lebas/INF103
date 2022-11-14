package maze;
import graph.Vertex;

public abstract class MazeBox implements Vertex{
	private int x;
	private int y;
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public abstract String getLabel();
	
}
