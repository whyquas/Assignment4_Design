package graph.scc;

import graph.core.Graph;
import graph.metrics.SimpleMetrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class TarjanSCCTest {
    @Test void simpleCycle(){
        Graph g = new Graph(3,true);
        g.addEdge(0,1,1); g.addEdge(1,2,1); g.addEdge(2,0,1);
        var scc = new TarjanSCC(g, new SimpleMetrics());
        var comps = scc.run();
        assertEquals(1, scc.compCount());
        assertEquals(3, comps.get(0).size());
    }
}
