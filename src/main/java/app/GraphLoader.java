package app;

import com.google.gson.*;
import graph.core.Graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GraphLoader {
    public static class Input {
        public boolean directed;
        public int n;
        public JsonArray edges;
        public Integer source;
        public String weight_model;
    }

    public static class Loaded {
        public Graph graph;
        public int source;
        public String weightModel;
    }

    public static Loaded fromJsonFile(String path) throws IOException {
        String s = Files.readString(Path.of(path));
        Gson gson = new Gson();
        Input in = gson.fromJson(s, Input.class);
        if (!in.directed) {
            throw new IllegalArgumentException("Input graph must be directed for SCC/condensation.");
        }
        Graph g = new Graph(in.n, true);
        for (JsonElement e : in.edges) {
            JsonObject o = e.getAsJsonObject();
            int u = o.get("u").getAsInt();
            int v = o.get("v").getAsInt();
            int w = o.has("w") ? o.get("w").getAsInt() : 1;
            g.addEdge(u, v, w);
        }
        Loaded L = new Loaded();
        L.graph = g;
        L.source = (in.source == null ? 0 : in.source);
        L.weightModel = (in.weight_model == null ? "edge" : in.weight_model);
        return L;
    }
}
