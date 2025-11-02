package graph.scc;

import graph.core.Graph;
import graph.metrics.Metrics;

import java.util.*;

public class TarjanSCC {
    private final Graph g; private final Metrics metrics;
    private int time = 0, compCount = 0;
    private final int[] disc, low, compId;
    private final boolean[] inStack;
    private final Deque<Integer> st = new ArrayDeque<>();

    public TarjanSCC(Graph g, Metrics metrics){
        this.g = g; this.metrics = metrics;
        int n = g.n();
        disc = new int[n]; low = new int[n]; compId = new int[n]; inStack = new boolean[n];
        Arrays.fill(disc, -1); Arrays.fill(low, -1); Arrays.fill(compId, -1);
    }

    public List<List<Integer>> run(){
        long t0 = System.nanoTime();
        for(int u=0; u<g.n(); u++) if(disc[u]==-1) dfs(u);
        long t1 = System.nanoTime();
        metrics.setTimeNs("scc", t1-t0);
        return buildComponentLists();
    }

    private void dfs(int u){
        metrics.inc("scc.dfs.calls",1);
        disc[u]=low[u]=time++; st.push(u); inStack[u]=true;
        for(Graph.Edge e: g.adj().get(u)){
            metrics.inc("scc.dfs.edges",1);
            int v=e.to;
            if(disc[v]==-1){
                dfs(v);
                low[u]=Math.min(low[u], low[v]);
            }
            if(inStack[v]) low[u]=Math.min(low[u], disc[v]);
        }
        if(low[u]==disc[u]){
            while(true){
                int v=st.pop(); inStack[v]=false; compId[v]=compCount;
                if(v==u) break;
            }
            compCount++;
        }
    }

    private List<List<Integer>> buildComponentLists(){
        List<List<Integer>> comps = new ArrayList<>();
        for(int i=0;i<compCount;i++) comps.add(new ArrayList<>());
        for(int v=0; v<g.n(); v++) comps.get(compId[v]).add(v);
        return comps;
    }

    public int[] compId(){ return compId; }
    public int compCount(){ return compCount; }
}
