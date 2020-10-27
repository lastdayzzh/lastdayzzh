package com.zkcm.problemtool.common.function;

import com.zkcm.problemtool.common.exception.RedisConnectException;

@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
