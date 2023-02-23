package graph;

import java.util.ArrayList;
/**
 * Graph is an interface for representing a graph data structure.
 */
public interface Graph {
    /**
     * Returns a list of all the vertices that are successors to the given vertex.
     * 
     * @param vertex the vertex to get the successors of
     * @return a list of all the vertices that are successors to the given vertex
     */
    ArrayList<Vertex> getSuccessors(Vertex vertex);

    /**
     * Returns a list of all the vertices in the graph.
     * 
     * @return a list of all the vertices in the graph
     */
    ArrayList<Vertex> getAllVertexes();
}
