package com.metro;

import com.metro.domain.Station;

/**
 * Created by catahoc on 4/26/2014.
 */
public class Rect {
    public float x1;
    public float y1;
    public float x2;
    public float y2;

    public float getW(){
        return x2-x1;
    }
    public float getH(){
        return y2-y1;
    }

    public Rect(){
        x2 = y2 = Float.MIN_VALUE;
        x1 = y1 = Float.MAX_VALUE;
    }

    public Rect convert(ViewPort viewPort){
        Rect result = new Rect();
        result.x1 = viewPort.getX(x1);
        result.x2 = viewPort.getX(x2);
        result.y1 = viewPort.getY(y1);
        result.y2 = viewPort.getY(y2);
        if(result.x2 < result.x1){
            float buf = result.x2;
            result.x2 = result.x1;
            result.x1 = buf;
        }
        if(result.y2 < result.y1){
            float buf = result.y2;
            result.y2 = result.y1;
            result.y1 = buf;
        }
        return result;
    }

    public static Rect compute(Iterable<Station> stations){
        Rect r = new Rect();
        for(Station station: stations){
            float x = station.pos.x;
            float y = station.pos.y;
            if(x < r.x1) r.x1 = x;
            if(y < r.y1) r.y1 = y;
            if(x > r.x2) r.x2 = x;
            if(y > r.y2) r.y2 = y;
        }
        return r;
    }
}
