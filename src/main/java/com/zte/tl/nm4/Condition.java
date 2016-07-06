package com.zte.tl.nm4;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class Condition {
    private Graph graph;
    private String start;
    private String end;
    private List<String> viaStations;
    private int maxViaStations;
    private int expectViaStations;
    private int distanceUpperLimit;

    private Condition(Builder builder) {
        this.graph = builder.graph;
        this.start = builder.start;
        this.end = builder.end;
        this.viaStations = builder.viaStations;
        this.maxViaStations = builder.maxViaStations;
        this.expectViaStations = builder.expectViaStations;
        this.distanceUpperLimit = builder.distanceUpperLimit;
    }

    public Graph getGraph() {
        return graph;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public List<String> getViaStations() {
        return viaStations;
    }

    public int getMaxViaStations() {
        return maxViaStations;
    }

    public int getExpectViaStations() {
        return expectViaStations;
    }

    public int getDistanceUpperLimit() {
        return distanceUpperLimit;
    }

    public static class Builder {
        private Graph graph;
        private String start;
        private String end;
        private List<String> viaStations = new ArrayList<String>();
        private int maxViaStations = -1;
        private int expectViaStations = -1;
        private int distanceUpperLimit = -1;

        public Builder(Graph graph, String start, String end) {
            Preconditions.checkNotNull(graph);
            Preconditions.checkNotNull(start);
            Preconditions.checkNotNull(end);
            this.graph = graph;
            this.start = start;
            this.end = end;
        }

        public Builder via(String station) {
            this.viaStations.add(station);
            return this;
        }

        public Builder setMaxViaStations(int maxViaStations) {
            this.maxViaStations = maxViaStations;
            return this;
        }

        public Builder setExpectViaStationsint(int expectViaStations) {
            this.expectViaStations = expectViaStations;
            return this;
        }

        public Builder setDistanceUpperLimit(int distanceUpperLimit) {
            this.distanceUpperLimit = distanceUpperLimit;
            return this;
        }

        public Condition build() {
            return new Condition(this);
        }

    }
}
