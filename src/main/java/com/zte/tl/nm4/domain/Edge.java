package com.zte.tl.nm4.domain;

import com.google.common.base.Objects;


public class Edge {
    public static final Edge EMPTY = new Edge("","",0);

    private String from;
    private String to;
    private int distance = 0;

    public Edge(String from, String to, int distance) {
        this.setFrom (from);
        this.setTo(to);
        this.setDistance(distance);
    }

    public String getFrom() {
        return from;
    }

    private void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    private void setTo(String to) {
        this.to = to;
    }

    int getDistance() {
        return distance;
    }

    private void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof Edge)) return false;
        Edge edge = (Edge) that;
        return getDistance() == edge.getDistance() &&
                Objects.equal(getFrom(), edge.getFrom()) &&
                Objects.equal(getTo(), edge.getTo());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getFrom(), getTo(), getDistance());
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", distance=" + distance +
                '}';
    }
}
