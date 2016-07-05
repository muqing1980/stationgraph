package com.zte.tl.nm4;


import com.google.common.base.Joiner;
import com.zte.tl.nm4.domain.Edge;
import com.zte.tl.nm4.domain.Node;
import com.zte.tl.nm4.domain.Route;
import com.zte.tl.nm4.exception.NoSuchRouteException;

import java.util.*;


class TravelService {
    static int routeDistance(String start, String... stations) throws NoSuchRouteException {
        Route route = GraphFactory.getInstance().getGraph().queryRouteBySpecificStations(start, stations);
        if (!route.hasRoute()) {
            throw new NoSuchRouteException();
        }
        return route.getDistance();
    }

    static List<String> queryRoutesByMaxViaStations(String start, String end, int maxStations) {
        Node root = GraphFactory.getInstance().getGraph().buildTreeForDepth(start, maxStations);
        List<Node> list = new ArrayList<Node>();
        for (int i = 0; i < maxStations; i++) {
            list.addAll(searchNodesFromTreeByDepth(root, end, i + 1));
        }
        return buildStationSeq(list);
    }

    static List<String> queryRoutesByExpectViaStations(String start, String end, int stations) {
        Node root = GraphFactory.getInstance().getGraph().buildTreeForDepth(start, stations);
        List<Node> list = searchNodesFromTreeByDepth(root, end, stations);
        return buildStationSeq(list);
    }

    private static List<String> buildStationSeq(List<Node> list) {
        List<String> routes = new ArrayList<String>();
        List<String> route;
        for (Node node : list) {
            route = new ArrayList<String>();
            Node routeNode = node;
            while (true) {
                route.add(routeNode.getName());
                if (routeNode.getParent() != null) {
                    routeNode = routeNode.getParent();
                } else {
                    break;
                }
            }
            Collections.reverse(route);
            routes.add(Joiner.on("").join(route));
        }
        return routes;
    }

    private static List<Node> searchNodesFromTreeByDepth(Node node, String end, int depth) {
        List<Node> list = node.getChildren();
        int times = depth - 1;
        List<Node> subNodes = new ArrayList<Node>();
        if (times == 0) {
            for (Node subNode : list) {
                if (subNode.getName().equals(end)) {
                    subNodes.add(subNode);
                }
            }
        } else {
            for (Node subNode : list) {
                subNodes.addAll(searchNodesFromTreeByDepth(subNode, end, times));
            }
        }
        return subNodes;
    }

    static int queryShortestRoute(String start, String end) {
        Node root = GraphFactory.getInstance().getGraph().buildTree(start, end);
        List<Node> nodes = searchRoute(end, root);
        List<Route> list = buildRoutes(start, end, nodes);
        Collections.sort(list, new Comparator<Route>() {
            @Override
            public int compare(Route route1, Route route2) {
                return route1.getDistance() - route2.getDistance();
            }
        });
        return list.get(0).getDistance();
    }

    private static List<Node> searchRoute(String end, Node node) {
        List<Node> subNodes = new ArrayList<Node>();
        List<Node> list = node.getChildren();
        if (list.size() == 0 && node.getName().equals(end)) {
            subNodes.add(node);
        } else {
            for (Node subNode : list) {
                subNodes.addAll(searchRoute(end, subNode));
            }
        }
        return subNodes;
    }

    private static List<Route> buildRoutes(String start, String end, List<Node> list) {
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

    public static List<String> queryRouteSeqByThreshold(String start, String end, int threshold) throws Exception {
        Node root = GraphFactory.getInstance().getGraph().buildTree(start, end);
        List<Node> nodes = searchRoute(end, root);
        List<Route> list = buildRoutes(start, end, nodes);
        List<Route> result = new ArrayList<Route>();
        for (Route route : list) {
            if (route.getDistance() < threshold) {
                result.add(route);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            Route route = list.get(i);
            Route newRoute = (Route) route.clone();
            newRoute.addEdges(route.getEdges());
            while (newRoute.getDistance() < threshold) {
                result.add(newRoute);
                newRoute = ((Route) newRoute.clone());
                newRoute.addEdges(route.getEdges());
            }
        }
        for(int i=0;i<list.size();i++){
            for(int j=0;j<list.size();j++){
                if(i==j){
                    continue;
                }
                if((list.get(i).getDistance()+list.get(j).getDistance()) <threshold){
                    Route route = (Route) list.get(i).clone();
                    route.addEdges(list.get(j).getEdges());
                    result.add(route);
                }
            }
        }
        return buildRouteStationSeq(result);
    }
    private static List<String> buildRouteStationSeq(List<Route> routes){
        List<String> result = new ArrayList<String>();
        for(Route route: routes){
            result.add(route.getStationSeq());
        }
        return result;
    }
}
