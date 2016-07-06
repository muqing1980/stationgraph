package com.zte.tl.nm4.strategy;

import com.zte.tl.nm4.exception.NoSuchRouteException;

/**
 * Created by muqing on 16/7/6.
 */
public interface Strategy<T,E> {
    T execute(E e) throws NoSuchRouteException;
}
