package graph.metrics;

import java.util.concurrent.ConcurrentHashMap;

public class SimpleMetrics implements Metrics {
    private final ConcurrentHashMap<String, Long> m = new ConcurrentHashMap<>();
    @Override public void inc(String key, long delta){ m.merge(key, delta, Long::sum); }
    @Override public long get(String key){ return m.getOrDefault(key,0L); }
    @Override public void setTimeNs(String key, long ns){ m.put("time:"+key, ns); }
    @Override public long timeNs(String key){ return m.getOrDefault("time:"+key, 0L); }
}
