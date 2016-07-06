package com.zte.tl.nm4;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.zte.tl.nm4.domain.Edge;

import java.util.List;
import java.util.regex.Pattern;


public class GraphFactory {

    private final static GraphFactory instance = new GraphFactory();

    static GraphFactory getInstance() {
        return instance;
    }

    private final Pattern pattern = Pattern.compile("[A-Z]{2}\\d");
    private final Graph graph;

    private GraphFactory() {
        graph = new Graph();
    }


    void create(String... args) {
        graph.clear();
        Edge edge;
        for (String str : args) {
            edge = buildEdge(str);
            graph.addEdge(edge);
        }
    }

    Graph getGraph() {
        return graph;
    }

    private Edge buildEdge(String edge) {
        Preconditions.checkNotNull(edge);
        Preconditions.checkArgument(this.pattern.matcher(edge).matches());
        List<String> edgeInfo = Splitter.fixedLength(1).splitToList(edge);
        return new Edge(edgeInfo.get(0), edgeInfo.get(1), Integer.parseInt(edgeInfo.get(2)));
    }
}
