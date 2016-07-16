package com.zte.tl.nm4.strategy;

import com.zte.tl.nm4.Condition;
import com.zte.tl.nm4.domain.Edge;
import com.zte.tl.nm4.domain.Graph;
import com.zte.tl.nm4.domain.Node;
import com.zte.tl.nm4.domain.Route;
import com.zte.tl.nm4.exception.NoSuchRouteException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DistanceUpperLimitRoutes implements Strategy<List<String>, Condition> {

    @Override
    public List<String> execute(Graph graph, Condition condition) throws NoSuchRouteException {
        Node root = graph.buildTree(condition.getStart(), condition.getEnd());
        List<Node> nodes = searchRoute(condition.getEnd(), root);
        List<Route> list = buildRoutes(condition.getStart(), condition.getEnd(), nodes);
        List<Route> result = new ArrayList<Route>();
        result.addAll(combinatedRoutes(list, condition.getDistanceUpperLimit()));
        return buildRouteStationSeq(result);
    }

    private List<Node> searchRoute(String end, Node node) {
        List<Node> subNodes = new ArrayList<Node>();
        List<Node> list = node.getChildren();
        if (isTerminalStation(end, node)) {
            subNodes.add(node);
        } else {
            for (Node subNode : list) {
                subNodes.addAll(searchRoute(end, subNode));
            }
        }
        return subNodes;
    }

    private boolean isTerminalStation(String end, Node node) {
        return node.getChildren().size() == 0 && node.getName().equals(end);
    }

    private List<Route> buildRoutes(String start, String end, List<Node> list) {
        List<Route> result = new ArrayList<Route>();
        for (Node node : list) {
            Route route = new Route(start, end, buildOneEdges(node));
            result.add(route);
        }
        return result;
    }


    private LinkedList<Edge> buildOneEdges(Node node) {
        LinkedList<Edge> edges = new LinkedList<Edge>();
        Node routeNode = node;
        while (routeNode.getParent() != null) {
            edges.push(routeNode.getEdge());
            routeNode = routeNode.getParent();
        }
        return edges;
    }

    private List<Route> combinatedRoutes(List<Route> original, int upperLimit) {
        List<Route> result = new ArrayList<Route>();
        for (int i = 0; i < original.size(); i++) {
            Route rout1 = original.get(i);
            if (rout1.getDistance() < upperLimit) {
                result.add(rout1);
            }
            result.addAll(superpositionRoutes(rout1, original, upperLimit));
        }
        return result;
    }

    private List<Route> superpositionRoutes(Route source, List<Route> list, int upperLimit) {
        List<Route> result = new ArrayList<Route>();
        for (Route route : list) {
            Route superposition = new Route(route.getStart(), route.getEnd(), route.getEdges());
            int distance = source.getDistance() + superposition.getDistance();
            while (distance < upperLimit) {
                result.add(join(source, superposition));
                superposition = join(superposition, route);
                distance = source.getDistance() + superposition.getDistance();
            }
        }
        return result;
    }

    private Route join(Route route1, Route route2) {
        Route result = new Route(route1.getStart(), route1.getEnd());
        result.addEdges(route1.getEdges());
        result.addEdges(route2.getEdges());
        return result;
    }

    private List<String> buildRouteStationSeq(List<Route> routes) {
        List<String> result = new ArrayList<String>();
        for (Route route : routes) {
            result.add(route.getStationSeq());
        }
        return result;
    }
}
