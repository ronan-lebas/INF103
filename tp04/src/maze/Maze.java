package maze;
import graph.Graph;

public class Maze implements Graph{
	public List<Vertex> getAllVertexes();
	public List<Vertex> getSuccessors(Vertex vertex);
	public int getWeight(Vertex src, Vertex dst);
}
