package com.zte.tl.nm4.strategy;

import com.zte.tl.nm4.Condition;
import com.zte.tl.nm4.domain.Route;
import com.zte.tl.nm4.exception.NoSuchRouteException;

import java.util.List;

public class SpecifiedViaStopNumRoute implements Strategy<List<Route>,Condition> {
    @Override
    public List<Route> execute(Condition condition) throws NoSuchRouteException {
        return null;
    }
}
