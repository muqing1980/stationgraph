package com.zte.tl.nm4.strategy;

import com.zte.tl.nm4.Condition;
import com.zte.tl.nm4.domain.Graph;
import com.zte.tl.nm4.domain.Route;
import com.zte.tl.nm4.exception.NoSuchRouteException;

public class SpecifiedRoutes implements Strategy<Route, Condition> {

    @Override
    public Route execute(Graph graph, Condition condition) throws NoSuchRouteException {
        return  graph.queryRouteBySpecificStations(condition.getStart(),condition.getEnd(),condition.getViaStations());
    }
}
