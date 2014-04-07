package com.metro.linq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by catahoc on 4/7/2014.
 */
public class Linq<T> implements Iterable<T> {
    private final List<T> _src;

    public Linq(List<T> src){
        _src = src;
    }

    @Override
    public Iterator<T> iterator() {
        return _src.iterator();
    }

    public Linq<T> Take(int count){
        List<T> result = new ArrayList<T>();
        int i = 0;
        for(T item : _src){
            if(i++ != count) {
                result.add(item);
            }
            else {
                break;
            }
        }
        return new Linq<T>(result);
    }

    public int size(){
        return _src.size();
    }


    public T FirstOrNull(){
        Linq<T> took = Take(2);
        if(took.size() > 0){
            return took.At(0);
        }
        else {
            return null;
        }
    }

    public T At(int ix){
        return _src.get(ix);
    }

    public Linq<T> Where(Func<T, Boolean> pred){
        List<T> result = new ArrayList<T>();
        for(T item : _src){
            if(pred.Invoke(item)){
                result.add(item);
            }
        }
        return new Linq<T>(result);
    }

    public <K> Linq<K> Select(Func<T, K> pred){
        List<K> result = new ArrayList<K>();
        for(T item : _src){
            result.add(pred.Invoke(item));
        }
        return new Linq<K>(result);
    }
}
