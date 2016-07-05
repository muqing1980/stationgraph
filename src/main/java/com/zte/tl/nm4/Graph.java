package com.zte.tl.nm4;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.zte.tl.nm4.domain.Edge;
import com.zte.tl.nm4.domain.Node;
import com.zte.tl.nm4.domain.Route;
import com.zte.tl.nm4.exception.NoSuchRouteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class Graph {
    private ListMultimap<String, Edge> startEdgeGraph = ArrayListMultimap.create();
    private ListMultimap<String, Edge> endEdgeGrapth = ArrayListMultimap.create();

    void addEdge(Edge edge) {
        this.startEdgeGraph.put(edge.getFrom(), edge);
        this.endEdgeGrapth.put(edge.getTo(), edge);
    }

    void clear() {
        this.startEdgeGraph.clear();
        this.endEdgeGrapth.clear();
    }

    private Edge queryEdge(String from, String to) {
        Preconditions.checkNotNull(from);
        Preconditions.checkNotNull(to);
        List<Edge> edges = this.queryEdgesByFrom(from);
        for (Edge edge : edges) {
            if (edge.getTo().equals(to)) {
                return edge;
            }
        }
        return Edge.EMPTY;
    }

    private List<Edge> queryEdgesByFrom(String from) {
        List<Edge> edges = this.startEdgeGraph.get(from);
        return (edges == null)?Collections.<Edge>emptyList():edges;
    }

    Route queryRouteBySpecificStations(String from, String... stations) throws NoSuchRouteException {
        Preconditions.checkNotNull(from);
        Preconditions.checkNotNull(stations);
        Preconditions.checkArgument(stations.length > 0);
        Edge edge = queryEdge(from, stations[0]);
        if (edge.equals(Edge.EMPTY)) {
            return Route.EMPTY;
        }
        List<Edge> list = new ArrayList<>();
        list.add(edge);
        for (int i = 1; i < stations.length; i++) {
            edge = queryEdge(stations[i - 1], stations[i]);
            if (edge.equals(Edge.EMPTY)) {
                return Route.EMPTY;
            }
            list.add(edge);
        }
        return new Route(from, stations[stations.length - 1], list);
    }

    /**
     * query routes  from start place to end , find times is  depth.
     * <rt/>return a list of routes.
     *
     * @param start start station
     * @param depth find times
     * @return List  list of routes
     */
    Node buildTreeForDepth(String start, int depth) {
        Preconditions.checkNotNull(start);
        Preconditions.checkNotNull(depth);
        if (depth <= 0) {
            Collections.emptyList();
        }
        Node root = new Node(start);
        buildSubTreeForDepth(root, depth);
        return root;
    }

    private void buildSubTreeForDepth(Node parent, int depth) {
        if (depth == 0) {
            return;
        }
        List<Edge> children = this.queryEdgesByFrom(parent.getName());
        for (Edge edge : children) {
            Node node = buildNode(parent,edge);
            parent.addChild(node);
            buildSubTreeForDepth(node, depth - 1);
        }
    }

    private Node buildNode(Node parent,Edge edge) {
        return new Node(parent,edge);
    }

    Node buildTree(String start, String end){
        Preconditions.checkNotNull(start);
        Preconditions.checkNotNull(end);
        Node root = new Node(start);
        buildSubTree(end,root);
        return root;
    }
    private void buildSubTree(String end,Node parent){
        List<Edge> children = this.queryEdgesByFrom(parent.getName());
        for(Edge edge:children){
            boolean cycleFlag = false;
            Node node = buildNode(parent,edge);
            if (!edge.getTo().equals(end)){
                Node tmp = parent;
                while(tmp.getParent()!=null){
                    if (node.getName().equals(tmp.getName())){
                        cycleFlag = true;
                        break;
                    }
                    tmp = tmp.getParent();
                }
                if(!cycleFlag) {
                    buildSubTree(end, node);
                }
            }
            if(!cycleFlag) {
                parent.addChild(node);
            }
        }
    }

//    List<Route> queryRoutes(String start,String end){
//        List<Route> result = new ArrayList<Route>();
//        List<Edge> edges = new ArrayList<Edge>(this.queryEdgesByFrom(start));
//        for(Edge edge : edges){
//            Route route = new Route(start,end,new ArrayList<Edge>());
//            route.getEdges().addAll(findEdgeToEnd(edge,end));
//        }
//    }
//    private List<Edge> findEdgeToEnd(Edge edge ,String end){
//        if(edge.getTo().equals(end)){
//            return Lists.newArrayList(edge);
//        }
//        List<Edge> nextEdges = this.queryEdgesByFrom(edge.getTo());
//        for(Edge next: nextEdges){
//            return findEdgeToEnd(next,end);
//        }
//    }

    @Override
    public String toString() {
        return "Graph{" +
                startEdgeGraph.toString() +
                "};\n" +
                "Reverse Grapth{" +
                endEdgeGrapth.toString() +
                '}';
    }

}
