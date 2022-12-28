package graph;

import java.util.HashSet;

public class ProcessedVertexesImpl extends HashSet implements ProcessedVertexes {
    public ProcessedVertexesImpl() {super();}
    public boolean isProcessed(Vertex vertex) {return super.contains(vertex);}
    public void add(Vertex vertex) {super.add(vertex);}
}
