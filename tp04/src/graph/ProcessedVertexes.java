package graph;
/**
 * ProcessedVertexes is an interface for maintaining a set of processed vertices in a graph.
 */
public interface ProcessedVertexes {
	/**
	 * Checks if a given vertex is in the set of processed vertices.
	 * 
	 * @param vertex the vertex to check for
	 * @return true if the vertex is in the set of processed vertices, false
	 *         otherwise
	 */
	public boolean isProcessed(Vertex vertex);

	/**
	 * Adds a given vertex to the set of processed vertices.
	 * 
	 * @param vertex the vertex to add
	 */
	public void add(Vertex vertex);
}
