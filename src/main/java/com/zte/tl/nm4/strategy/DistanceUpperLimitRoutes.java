package com.zte.tl.nm4.strategy;

import com.zte.tl.nm4.Condition;
import com.zte.tl.nm4.exception.NoSuchRouteException;

import java.util.List;

public class DistanceUpperLimitRoutes implements Strategy<List<String>, Condition> {
    @Override
    public List<String> execute(Condition condition) throws NoSuchRouteException {
        return null;
    }
}
