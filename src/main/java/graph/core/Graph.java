package graph.core;

import java.util.*;

public class Graph {
    public static class Edge {
        public final int to; public final int w;
        public Edge(int to, int w){ this.to = to; this.w = w; }
        @Override public String toString(){ return to+"(w="+w+")"; }
    }

    private final int n;
    private final boolean directed;
    private final List<List<Edge>> adj;

    public Graph(int n, boolean directed){
        this.n = n; this.directed = directed;
        this.adj = new ArrayList<>(n);
        for(int i=0;i<n;i++) adj.add(new ArrayList<>());
    }
    public int n(){ return n; }
    public boolean directed(){ return directed; }
    public List<List<Edge>> adj(){ return adj; }

    public void addEdge(int u, int v, int w){
        adj.get(u).add(new Edge(v,w));
        if(!directed) adj.get(v).add(new Edge(u,w));
    }
}
