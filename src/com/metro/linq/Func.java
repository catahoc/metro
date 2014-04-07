package com.metro.linq;

public abstract class Func<T, K> {
    public abstract K Invoke(T arg);
}
