package com.zte.tl.nm4.strategy;

import com.zte.tl.nm4.domain.Graph;
import com.zte.tl.nm4.exception.NoSuchRouteException;

public interface Strategy<T,E> {
    T execute(Graph graph, E e) throws NoSuchRouteException;
}
