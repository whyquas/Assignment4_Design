# Assignment 4 — Graph Scheduling Project

This project is my implementation for Assignment 4 (“Smart City / Smart Campus Scheduling”).

I implemented the full required pipeline:

SCC → Condensation DAG → Topological Sort → Shortest & Longest Paths in DAG

I use:
- Tarjan algorithm for SCC
- Kahn algorithm for topological sort
- DP over topological ordering for shortest and longest paths in DAG

### Weight model
I chose edge weights (allowed by the assignment).  
This is the model used for all path calculations.

### Datasets
There are 9 datasets in /data, as required:

- 3 small (6–10 nodes)
- 3 medium (10–20 nodes, mixed / multiple SCCs)
- 3 large (20–50 nodes, performance)

All datasets follow the provided JSON input format.

### Program output
The program prints:
- SCC components + sizes
- condensation DAG of components
- topological order of components
- derived order of original tasks (after compression)
- shortest paths from the given source component
- longest path (critical path) and its length
- metrics: DFS calls, edges processed, relaxations, push/pop counters, and time in ns

### How to build & run
```bash
mvn -q -DskipTests package
java -jar target/SmartCityGraphProject-1.0.0.jar data/tasks.sample.json
```

If no input file is passed, it uses data/tasks.sample.json by default.

### Tests
JUnit tests are included under src/test/java, covering:

- SCC correctness
- topological sorting
- shortest / longest DAG DP logic

---

This implementation follows the required package structure (graph.scc, graph.topo, graph.dagsp) and builds from a clean clone.
