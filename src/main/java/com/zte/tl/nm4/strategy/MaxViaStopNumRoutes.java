package com.zte.tl.nm4.strategy;

import com.google.common.base.Joiner;
import com.zte.tl.nm4.Condition;
import com.zte.tl.nm4.domain.Graph;
import com.zte.tl.nm4.domain.Node;
import com.zte.tl.nm4.exception.NoSuchRouteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxViaStopNumRoutes implements Strategy<List<String>, Condition> {
    @Override
    public List<String> execute(Graph graph, Condition condition) throws NoSuchRouteException {
        Node root = graph.buildDepthTree(condition.getStart(), condition.getMaxViaStations());
        List<Node> list = new ArrayList<Node>();
        for (int i = 0; i < condition.getMaxViaStations(); i++) {
            list.addAll(searchNodesFromTreeByLevel(root, condition.getEnd(), i + 1));
        }
        return buildStationSeq(list);
    }

    private List<String> buildStationSeq(List<Node> list) {
        List<String> routes = new ArrayList<String>();
        List<String> route;
        for (Node node : list) {
            route = buildRouteByNode(node);
            Collections.reverse(route);
            routes.add(Joiner.on("").join(route));
        }
        return routes;
    }

    private List<String> buildRouteByNode(Node node) {
        List<String> result = new ArrayList<String>();
        while (true) {
            result.add(node.getName());
            if (node.getParent() == null) {
                break;
            }
            node = node.getParent();
        }
        return result;
    }

    private List<Node> searchNodesFromTreeByLevel(Node node, String end, int level) {
        List<Node> list = node.getChildren();
        int times = level - 1;
        List<Node> subNodes = new ArrayList<Node>();
        for (Node subNode : list) {
            if (times != 0) {
                subNodes.addAll(searchNodesFromTreeByLevel(subNode, end, times));
            } else if (isTerminalNode(end, times, subNode)) {
                subNodes.add(subNode);
            }
        }
        return subNodes;
    }

    private boolean isTerminalNode(String end, int level, Node subNode) {
        return level == 0 && subNode.getName().equals(end);
    }
}
