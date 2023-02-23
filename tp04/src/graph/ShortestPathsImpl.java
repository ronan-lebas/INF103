package graph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the ShortestPaths interface.
 */
public class ShortestPathsImpl extends HashMap implements ShortestPaths {
    public ShortestPathsImpl() {
        super();
    }

    public Vertex previous(Vertex vertex) {
        return (Vertex) get(vertex);
    }

    public void link(Vertex vertex1, Vertex vertex2) {
        put(vertex1, vertex2);
    }

    public ArrayList<Vertex> getShortestPath(Vertex endVertex) {
        ArrayList<Vertex> shortestPath = new ArrayList<Vertex>();
        shortestPath.add(endVertex);
        while (containsKey(shortestPath.get(shortestPath.size() - 1))) {
            shortestPath.add(previous(shortestPath.get(shortestPath.size() - 1)));
        }
        return shortestPath;
    }

}
