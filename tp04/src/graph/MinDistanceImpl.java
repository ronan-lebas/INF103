package graph;

import java.util.HashMap;

public class MinDistanceImpl extends HashMap implements MinDistance {
    public MinDistanceImpl() {super();}
    public int minDistance(Vertex vertex) {return (int) get(vertex);}
    public void setMinDistance(Vertex vertex, int i) {put(vertex, (Integer) i);}

}
