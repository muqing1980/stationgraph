package com.zte.tl.nm4.domain;

import com.google.common.base.Objects;

import java.util.LinkedList;
import java.util.List;


public class Route implements Cloneable {
    private String start;
    private String end;
    private List<Edge> edges = new LinkedList<Edge>();
    private int distance = -1;

    public Route(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public Route(String start, String end, List<Edge> edges) {
        this(start, end);
        this.addEdges(edges);
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getDistance() {
        return this.distance;
    }

    public void addEdges(List<Edge> list) {
        this.edges.addAll(list);
        this.recalculateDistance();
    }

    private void recalculateDistance() {
        this.distance = calculateDistance();
    }

    private int calculateDistance() {
        int num = 0;
        for (Edge edge : this.getEdges()) {
            num = num + edge.getDistance();
        }
        return num;
    }


    public String getStationSeq() {
        StringBuilder seq = new StringBuilder(this.start);
        for (Edge edge : this.edges) {
            seq.append(edge.getTo());
        }
        return seq.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new Route(this.start, this.end, this.getEdges());
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof Route)) return false;
        Route route1 = (Route) that;
        return Objects.equal(start, route1.start) &&
                Objects.equal(end, route1.end) &&
                Objects.equal(getEdges(), route1.getEdges());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(start, end, getEdges());
    }

    @Override
    public String toString() {
        return "Route{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", distance='" + this.getDistance() + '\'' +
                ", edges=" + edges.toString() +
                '}';
    }
}
