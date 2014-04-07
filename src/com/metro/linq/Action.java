package com.metro.linq;

/**
 * Created by catahoc on 4/7/2014.
 */
public abstract class Action<T> {
    public abstract void Invoke(T arg);
}
