package com.zte.tl.nm4;


import com.zte.tl.nm4.domain.Graph;
import com.zte.tl.nm4.domain.Route;
import com.zte.tl.nm4.exception.NoSuchRouteException;
import com.zte.tl.nm4.strategy.*;

import java.util.List;


class TravelService {
    private static TravelService instance = new TravelService();

    static TravelService getInstance() {
        return instance;
    }

    private Graph graph;

    private TravelService() {
        graph = GraphFactory.getInstance().getGraph();
    }

    int calculateDistance(Condition condition) throws NoSuchRouteException {
        Strategy<Route, Condition> strategy = new SpecifiedRoutes();
        Route route = strategy.execute(graph, condition);
        return route.getDistance();
    }

    int calculateShortestDistance(Condition condition) throws NoSuchRouteException {
        Strategy<Integer, Condition> strategy = new ShortestDistanceRoute();
        return strategy.execute(graph, condition);
    }

    List<String> queryRoutesByMaxViaStations(Condition condition) throws NoSuchRouteException {
        Strategy<List<String>, Condition> strategy = new MaxViaStopNumRoutes();
        return strategy.execute(graph, condition);
    }

    List<String> queryRoutesByExpectViaStations(Condition condition) throws NoSuchRouteException {
        Strategy<List<String>, Condition> strategy = new SpecifiedViaStopNumRoute();
        return strategy.execute(graph, condition);
    }

    List<String> queryRouteSeqByThreshold(Condition condition) throws NoSuchRouteException {
        Strategy<List<String>, Condition> strategy = new DistanceUpperLimitRoutes();
        return strategy.execute(graph, condition);
    }
}
