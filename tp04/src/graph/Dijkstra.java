package graph;

import java.util.ArrayList;
import maze.*;
/**
 * Implementation of the Dijkstra algorithm to find the shortest paths from a start vertex to all other vertices in a graph.
 */
public class Dijkstra {

	/**
	 * Computes the shortest paths from a start vertex to all other vertices in a
	 * graph using the Dijkstra algorithm.
	 *
	 * @param graph       the graph on which to compute the shortest paths
	 * @param startVertex the start vertex from which to compute the shortest paths
	 * @param endVertex   the end vertex to which to compute the shortest paths
	 * @return an object containing the shortest paths from the start vertex to all
	 *         other vertices in the graph
	 */
	public static ShortestPaths dijkstra(Graph graph, Vertex startVertex, Vertex endVertex) {
		ProcessedVertexesImpl processedVertexes = new ProcessedVertexesImpl();
		ShortestPathsImpl shortestPaths = new ShortestPathsImpl();
		MinDistanceImpl minDistance = new MinDistanceImpl();
		ArrayList<Vertex> vertexes = graph.getAllVertexes();
		for (Vertex vertex : vertexes) {
			minDistance.setMinDistance(vertex, -1);
		}
		Vertex pivotVertex = startVertex;
		minDistance.setMinDistance(startVertex, 0);

		while (vertexes.size() > 1) {
			processedVertexes.add(pivotVertex);
			ArrayList<Vertex> successors = graph.getSuccessors(pivotVertex);
			for (int i = 0; i < successors.size(); i++) {
				if (processedVertexes.isProcessed(successors.get(i)) == false
						&& (minDistance.minDistance(pivotVertex) + 1 < minDistance.minDistance(successors.get(i))
								|| minDistance.minDistance(successors.get(i)) == -1)) {
					minDistance.setMinDistance(successors.get(i), minDistance.minDistance(pivotVertex) + 1); // specific
																												// to
																												// the
																												// case
																												// where
																												// distances
																												// are 1
					shortestPaths.link(successors.get(i), pivotVertex);
				}
			}
			vertexes.remove(pivotVertex);
			int minIndex = 0;
			for (int i = 0; i < vertexes.size(); i++) {
				if (minDistance.minDistance(vertexes.get(i)) != -1
						&& (minDistance.minDistance(vertexes.get(i)) < minDistance.minDistance(vertexes.get(minIndex))
								|| minDistance.minDistance(vertexes.get(minIndex)) == -1)) {
					minIndex = i;
				}
			}
			pivotVertex = vertexes.get(minIndex);
		}

		return shortestPaths;
	}
}
