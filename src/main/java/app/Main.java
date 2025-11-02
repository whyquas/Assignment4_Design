package app;

import app.GraphLoader.Loaded;
import graph.core.Graph;
import graph.metrics.Metrics;
import graph.metrics.SimpleMetrics;
import graph.scc.TarjanSCC;
import graph.topo.TopoSort;
import graph.dagsp.DagShortestPaths;
import graph.dagsp.DagLongestPath;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String path = args.length>0 ? args[0] : "data/tasks.sample.json";
        Loaded L = GraphLoader.fromJsonFile(path);
        Graph g = L.graph; int source = L.source; String weightModel = L.weightModel;
        if(!"edge".equalsIgnoreCase(weightModel)){
            System.out.println("WARNING: weight_model='"+weightModel+"'. В реализации используется модель edge-weights.");
        }

        Metrics M = new SimpleMetrics();
        // 1) SCC (Tarjan)
        TarjanSCC scc = new TarjanSCC(g, M);
        var components = scc.run();
        int[] compId = scc.compId();
        int compCount = scc.compCount();

        System.out.println("SCC count = "+compCount);
        for(int i=0;i<components.size();i++){
            System.out.println("  C"+i+" size="+components.get(i).size()+" : "+components.get(i));
        }
        // Condensation DAG
        Graph dag = new Graph(compCount, true);
        boolean[][] seen = new boolean[compCount][compCount];
        for(int u=0; u<g.n(); u++){
            for(var e: g.adj().get(u)){
                int cu = compId[u], cv = compId[e.to];
                if(cu!=cv && !seen[cu][cv]){ dag.addEdge(cu,cv, e.w); seen[cu][cv]=true; }
            }
        }
        var topo = TopoSort.kahn(dag, M);
        System.out.println("Topo order (components): "+topo);

        List<Integer> expanded = new ArrayList<>();
        for(int cid : topo){ expanded.addAll(components.get(cid)); }
        System.out.println("Derived order of original tasks: "+expanded);

        int sourceComp = compId[source];
        DagShortestPaths dsp = new DagShortestPaths(dag, topo, M);
        var sp = dsp.run(sourceComp);
        System.out.println("\nShortest distances from component "+sourceComp+": "+Arrays.toString(sp.dist));
        for(int t=0;t<dag.n();t++) if(sp.dist[t]<Long.MAX_VALUE){
            var pathComp = sp.buildPath(sourceComp, t);
            System.out.println("  path to C"+t+" = "+pathComp+", dist="+sp.dist[t]);
        }

        var lp = DagLongestPath.run(dag, topo);
        System.out.println("\nCritical path length = "+lp.length());
        System.out.println("Critical path (components) = "+lp.criticalPath());

        System.out.println("\nMetrics:");
        System.out.println("  scc.time(ns)="+M.timeNs("scc")+", dfs.calls="+M.get("scc.dfs.calls")+", dfs.edges="+M.get("scc.dfs.edges"));
        System.out.println("  topo.time(ns)="+M.timeNs("topo")+", pop="+M.get("topo.pop")+", push="+M.get("topo.push"));
        System.out.println("  dagsp.time(ns)="+M.timeNs("dagsp")+", relax="+M.get("dag.relax"));
    }
}
