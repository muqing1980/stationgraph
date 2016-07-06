package com.zte.tl.nm4.strategy;

import com.zte.tl.nm4.Condition;
import com.zte.tl.nm4.domain.Route;
import com.zte.tl.nm4.exception.NoSuchRouteException;

public class SpecifiedRoutes implements Strategy<Route,Condition> {
    @Override
    public Route execute(Condition condition) throws NoSuchRouteException {
        return null;
    }
}
