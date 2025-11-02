package graph.metrics;

public interface Metrics {
    void inc(String key, long delta);
    long get(String key);
    void setTimeNs(String key, long ns);
    long timeNs(String key);
}
