package graph;

import java.util.ArrayList;
/**
 * ShortestPaths is an interface for computing and retrieving the shortest path between two vertexes in a graph.
 */
public interface ShortestPaths {
	/**
	 * Returns the previous vertex on the shortest path to a given vertex.
	 * 
	 * @param vertex the vertex to get the previous vertex of
	 * @return the previous vertex on the shortest path to the given vertex
	 */
	public Vertex previous(Vertex vertex);

	/**
	 * Creates a link between two vertexes in the graph.
	 * 
	 * @param vertex1 the first vertex to link
	 * @param vertex2 the second vertex to link
	 */
	public void link(Vertex vertex1, Vertex vertex2);

	/**
	 * Returns the shortest path between two vertexes in the graph.
	 * 
	 * @param endVertex the end vertex of the shortest path
	 * @return an ArrayList of vertexes representing the shortest path between the
	 *         start vertex and the end vertex
	 */
	public ArrayList<Vertex> getShortestPath(Vertex endVertex);
}
