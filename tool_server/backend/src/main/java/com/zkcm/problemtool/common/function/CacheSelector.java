package com.zkcm.problemtool.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
