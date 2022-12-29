package graph;

import java.util.ArrayList;

public interface ShortestPaths {
	public Vertex previous(Vertex vertex);
	public void link(Vertex vertex1, Vertex vertex2);
	public ArrayList<Vertex> getShortestPath(Vertex endVertex);
}
