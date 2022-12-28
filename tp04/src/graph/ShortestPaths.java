package graph;

public interface ShortestPaths {
	public Vertex previous(Vertex vertex);
	public void link(Vertex vertex1, Vertex vertex2);
}
