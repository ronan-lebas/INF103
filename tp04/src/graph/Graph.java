package graph;

import java.util.ArrayList;

public interface Graph {
    ArrayList<Vertex> getSuccessors(Vertex vertex);
    ArrayList<Vertex> getAllVertexes();
}
