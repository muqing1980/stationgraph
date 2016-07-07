package com.zte.tl.nm4.strategy;

import com.zte.tl.nm4.Condition;
import com.zte.tl.nm4.domain.Graph;
import com.zte.tl.nm4.domain.Edge;
import com.zte.tl.nm4.domain.Node;
import com.zte.tl.nm4.domain.Route;
import com.zte.tl.nm4.exception.NoSuchRouteException;

import java.util.*;

public class ShortestDistanceRoute implements Strategy<Integer,Condition> {
    @Override
    public Integer execute(Graph graph, Condition condition) throws NoSuchRouteException {
        Node root = graph.buildTree(condition.getStart(), condition.getEnd());
        List<Node> nodes = searchRoute(condition.getEnd(), root);
        List<Route> list = buildRoutes(condition.getStart(), condition.getEnd(), nodes);
        if(list.size()==0){
            throw new NoSuchRouteException();
        }
        Collections.sort(list, new Comparator<Route>() {
            @Override
            public int compare(Route route1, Route route2) {
                return route1.getDistance() - route2.getDistance();
            }
        });
        return list.get(0).getDistance();
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
        LinkedList<Edge> edges;
        for (Node node : list) {
            edges = new LinkedList<Edge>();
            Node routeNode = node;
            while (routeNode.getParent() != null) {
                edges.push(routeNode.getEdge());
                routeNode = routeNode.getParent();
            }
            result.add(new Route(start, end, edges));
        }
        return result;
    }
}
