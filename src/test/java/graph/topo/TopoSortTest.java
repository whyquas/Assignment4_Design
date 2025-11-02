package graph.topo;

import graph.core.Graph;
import graph.metrics.SimpleMetrics;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TopoSortTest {
    @Test void dagOrder(){
        Graph g = new Graph(4,true);
        g.addEdge(0,1,1); g.addEdge(1,2,1); g.addEdge(0,2,1); g.addEdge(2,3,1);
        var order = TopoSort.kahn(g, new SimpleMetrics());
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(0) < order.indexOf(2));
        assertTrue(order.indexOf(2) < order.indexOf(3));
    }
}
