package graph;
/**
 * Distance is an interface for measuring the distance between vertexes in a graph.
 */
public interface Distance {
	/**
	 * Returns the distance between two given vertexes.
	 * 
	 * @param vertex1 the first vertex
	 * @param vertex2 the second vertex
	 * @return the distance between the two given vertexes
	 */
	public int distance(Vertex vertex1, Vertex vertex2);
}
