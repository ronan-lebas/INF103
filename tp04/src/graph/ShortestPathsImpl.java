package graph;

import java.util.HashMap;

public class ShortestPathsImpl extends HashMap implements ShortestPaths {
    public ShortestPathsImpl() {super();}
    public Vertex previous(Vertex vertex) {return (Vertex) get(vertex);}
    public void link(Vertex vertex1, Vertex vertex2) {put(vertex1,vertex2);}}
