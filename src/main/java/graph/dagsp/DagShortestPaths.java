package graph.dagsp;

import graph.core.Graph;
import graph.metrics.Metrics;

import java.util.*;

public class DagShortestPaths {
    private final Graph dag; private final List<Integer> topo; private final Metrics metrics;
    public DagShortestPaths(Graph dag, List<Integer> topo, Metrics metrics){ this.dag=dag; this.topo=topo; this.metrics=metrics; }

    public static class Result {
        public final long[] dist; public final int[] parent;
        public Result(long[] d, int[] p){ this.dist=d; this.parent=p; }
        public List<Integer> buildPath(int s, int t){
            if(dist[t]==Long.MAX_VALUE) return List.of();
            List<Integer> path=new ArrayList<>(); int cur=t; while(cur!=-1){ path.add(cur); if(cur==s) break; cur=parent[cur]; }
            Collections.reverse(path); return path;
        }
    }

    public Result run(int source){
        long t0 = System.nanoTime();
        int n = dag.n();
        long[] dist = new long[n]; Arrays.fill(dist, Long.MAX_VALUE);
        int[] parent = new int[n]; Arrays.fill(parent, -1);
        dist[source]=0;
        for(int u: topo){
            if(dist[u]==Long.MAX_VALUE) continue;
            for(var e: dag.adj().get(u)){
                long cand = dist[u] + e.w;
                if(cand < dist[e.to]){ dist[e.to]=cand; parent[e.to]=u; metrics.inc("dag.relax",1); }
            }
        }
        long t1 = System.nanoTime(); metrics.setTimeNs("dagsp", t1-t0);
        return new Result(dist,parent);
    }
}
