package com.zte.tl.nm4.domain;

import com.google.common.base.Objects;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Route implements Cloneable{
    public static final Route EMPTY = new Route("", "", Collections.<Edge>emptyList());
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
        this.edges.addAll(edges);
    }


    public List<Edge> getEdges() {
        return edges;
    }

    public boolean hasRoute() {
        return this.getEdges().size() != 0;
    }

    public void addEdges(List<Edge> list) {
        this.edges.addAll(list);
        this.resetDistance();
    }

    private void resetDistance() {
        this.distance = -1;
        getDistance();
    }

    public int getDistance() {
        if (distance == -1) {
            this.distance = 0;
            for (Edge edge : this.getEdges()) {
                this.distance = this.distance + edge.getDistance();
            }
        }
        return this.distance;
    }
    public String getStationSeq(){
        String result = this.start;
        for(Edge edge: this.edges){
            result = result + edge.getTo();
        }
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new Route(this.start,this.end,this.getEdges());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route1 = (Route) o;
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
                ", edges=" + edges.toString() +
                '}';
    }
}
