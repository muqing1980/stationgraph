package com.zte.tl.nm4.domain;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.zte.tl.nm4.exception.NoSuchRouteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Graph {
    private ListMultimap<String, Edge> startEdgeGraph = ArrayListMultimap.create();

    public void addEdge(Edge edge) {
        this.startEdgeGraph.put(edge.getFrom(), edge);
    }

    public void clear() {
        this.startEdgeGraph.clear();
    }

    public Route queryRouteBySpecificStations(String from, String end, List<String> viaStations) throws NoSuchRouteException {
        Preconditions.checkNotNull(from);
        Preconditions.checkNotNull(end);
        Preconditions.checkNotNull(viaStations);
        List<String> path = new ArrayList<String>();
        path.add(from);
        path.addAll(viaStations);
        path.add(end);
        List<Edge> list = new ArrayList<Edge>();
        Edge edge;
        for (int i = 1; i < path.size(); i++) {
            edge = queryEdge(path.get(i - 1), path.get(i));
            if (edge.equals(Edge.EMPTY)) {
                throw new NoSuchRouteException();
            }
            list.add(edge);
        }
        return new Route(from, end, list);
    }

    private List<Edge> queryEdgesByFrom(String from) {
        List<Edge> edges = this.startEdgeGraph.get(from);
        return (edges == null) ? Collections.<Edge>emptyList() : edges;
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

    /**
     * query routes  from start place to end , find times is  depth.
     * <rt/>return a list of routes.
     *
     * @param start start station
     * @param depth find times, depth must be more then zero
     * @return List  list of routes
     */
    public Node buildDepthTree(String start, int depth) {
        Preconditions.checkNotNull(start);
        Preconditions.checkNotNull(depth);
        Preconditions.checkArgument(depth > 0);
        Node root = new Node(start);
        buildSubTreeForDepth(root, depth);
        return root;
    }

    private void buildSubTreeForDepth(Node parent, int depth) {
        if (depth > 0) {
            List<Edge> children = this.queryEdgesByFrom(parent.getName());
            for (Edge edge : children) {
                Node node = buildNode(parent, edge);
                parent.addChild(node);
                buildSubTreeForDepth(node, depth - 1);
            }
        }
    }

    private Node buildNode(Node parent, Edge edge) {
        return new Node(parent, edge);
    }

    public Node buildTree(String start, String end) {
        Preconditions.checkNotNull(start);
        Preconditions.checkNotNull(end);
        Node root = new Node(start);
        buildSubTree(end, root);
        return root;
    }

    private void buildSubTree(String end, Node parent) {
        List<Edge> children = this.queryEdgesByFrom(parent.getName());
        for (Edge edge : children) {
            Node currentNode = buildNode(parent, edge);
            if (!edge.getTo().equals(end)) {
                if (hasCycle(parent, currentNode)) {
                    break;
                }
                buildSubTree(end, currentNode);
            }
            parent.addChild(currentNode);
        }
    }

    private boolean hasCycle(Node parent, Node current) {
        boolean result = false;
        Node parentNode = parent;
        while (parentNode.getParent() != null) {
            if (current.getName().equals(parentNode.getName())) {
                result = true;
                break;
            }
            parentNode = parentNode.getParent();
        }
        return result;
    }

    @Override
    public String toString() {
        return "Graph{" +
                startEdgeGraph.toString() +
                "};";
    }

}
