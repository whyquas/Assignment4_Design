package graph.topo;

import graph.core.Graph;
import graph.metrics.Metrics;

import java.util.*;

public class TopoSort {
    public static List<Integer> kahn(Graph dag, Metrics metrics){
        int n = dag.n();
        int[] indeg = new int[n];
        for(int u=0; u<n; u++) for(var e: dag.adj().get(u)) indeg[e.to]++;
        Deque<Integer> q = new ArrayDeque<>();
        for(int i=0;i<n;i++) if(indeg[i]==0) q.add(i);
        List<Integer> order = new ArrayList<>();
        long t0 = System.nanoTime();
        while(!q.isEmpty()){
            int u = q.remove();
            metrics.inc("topo.pop",1);
            order.add(u);
            for(var e: dag.adj().get(u)){
                metrics.inc("topo.push",1);
                if(--indeg[e.to]==0) q.add(e.to);
            }
        }
        long t1 = System.nanoTime();
        metrics.setTimeNs("topo", t1-t0);
        if(order.size()!=n) throw new IllegalStateException("Graph is not a DAG (cycle detected in condensation?)");
        return order;
    }
}
