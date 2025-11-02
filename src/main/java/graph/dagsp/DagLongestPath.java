package graph.dagsp;

import graph.core.Graph;

import java.util.*;

public class DagLongestPath {
    public static class Result {
        public final long[] best; public final int[] parent; public final int start, end;
        public Result(long[] best, int[] parent, int start, int end){ this.best=best; this.parent=parent; this.start=start; this.end=end; }
        public List<Integer> criticalPath(){
            List<Integer> path = new ArrayList<>();
            if(end==-1) return path;
            int cur=end; while(cur!=-1){ path.add(cur); if(cur==start) break; cur=parent[cur]; }
            Collections.reverse(path); return path;
        }
        public long length(){ return end==-1?0:best[end]; }
    }

    public static Result run(Graph dag, List<Integer> topo){
        int n = dag.n();
        long[] best = new long[n]; Arrays.fill(best, Long.MIN_VALUE/4);
        int[] parent = new int[n]; Arrays.fill(parent, -1);
        int[] indeg = new int[n]; for(int u=0;u<n;u++) for(var e: dag.adj().get(u)) indeg[e.to]++;
        for(int i=0;i<n;i++) if(indeg[i]==0) best[i]=0;
        for(int u: topo){
            if(best[u]==Long.MIN_VALUE/4) continue;
            for(var e: dag.adj().get(u)){
                long cand = best[u] + e.w;
                if(cand > best[e.to]){ best[e.to]=cand; parent[e.to]=u; }
            }
        }
        int end=-1; long bestVal=Long.MIN_VALUE/4; int start=-1;
        for(int v=0; v<n; v++) if(best[v]>bestVal){ bestVal=best[v]; end=v; }
        if(end!=-1){ int s=end; while(parent[s]!=-1) s=parent[s]; start=s; }
        return new Result(best,parent,start,end);
    }
}
