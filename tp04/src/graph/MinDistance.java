package graph;
/**
 * MinDistance is an interface for maintaining minimum distances between vertexes in a graph.
 */
public interface MinDistance {
	/**
	 * Returns the minimum distance to a given vertex.
	 * 
	 * @param vertex the vertex to get the minimum distance to
	 * @return the minimum distance to the given vertex
	 */
	public int minDistance(Vertex vertex);

	/**
	 * Sets the minimum distance to a given vertex to a given value.
	 * 
	 * @param vertex the vertex to set the minimum distance for
	 * @param i      the value to set the minimum distance to
	 */
	public void setMinDistance(Vertex vertex, int i);
}
