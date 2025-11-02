package graph.dagsp;

import graph.core.Graph;
import graph.metrics.SimpleMetrics;
import graph.topo.TopoSort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class DagPathsTest {
    @Test void shortestAndLongest(){
        Graph g = new Graph(5,true);
        g.addEdge(0,1,2); g.addEdge(0,2,1); g.addEdge(2,3,2); g.addEdge(1,3,2); g.addEdge(3,4,3);
        var topo = TopoSort.kahn(g, new SimpleMetrics());
        var sp = new DagShortestPaths(g, topo, new SimpleMetrics()).run(0);
        assertEquals(1+2+3, sp.dist[4]); // 0->2->3->4
        var lp = DagLongestPath.run(g, topo);
        assertTrue(lp.length() >= 0);
        assertFalse(lp.criticalPath().isEmpty());
    }
}
