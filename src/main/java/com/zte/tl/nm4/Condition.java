package com.zte.tl.nm4;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class Condition {
    private String start;
    private String end;
    private List<String> viaStations;
    private int maxViaStations;
    private int expectViaStations;
    private int distanceUpperLimit;

    private Condition(Builder builder) {
        this.start = builder.start;
        this.end = builder.end;
        this.viaStations = builder.viaStations;
        this.maxViaStations = builder.maxViaStations;
        this.expectViaStations = builder.expectViaStations;
        this.distanceUpperLimit = builder.distanceUpperLimit;
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
        private String start;
        private String end;
        private List<String> viaStations = new ArrayList<String>();
        private int maxViaStations = -1;
        private int expectViaStations = -1;
        private int distanceUpperLimit = -1;

        public Builder(String start, String end) {
            Preconditions.checkNotNull(start);
            Preconditions.checkNotNull(end);
            this.start = start.toUpperCase();
            this.end = end.toUpperCase();
        }

        public Builder via(String station) {
            Preconditions.checkNotNull(station);
            this.viaStations.add(station.toUpperCase());
            return this;
        }

        public Builder maxViaStations(int maxViaStations) {
            Preconditions.checkArgument(maxViaStations > 0);
            this.maxViaStations = maxViaStations;
            return this;
        }

        public Builder expectViaStations(int expectViaStations) {
            Preconditions.checkArgument(expectViaStations > 0);
            this.expectViaStations = expectViaStations;
            return this;
        }

        public Builder distanceUpperLimit(int distanceUpperLimit) {
            Preconditions.checkArgument(distanceUpperLimit > 0);
            this.distanceUpperLimit = distanceUpperLimit;
            return this;
        }

        public Condition build() {
            return new Condition(this);
        }

    }
}
