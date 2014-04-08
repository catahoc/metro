package com.metro;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by catahoc on 4/8/2014.
 */
public class ViewPort {
    private final float my;
    private final float mx;
    private final float ky;
    private final float kx;
    private final int w;
    private final int h;

    public ViewPort(int w, int h, float clon, float clat, float lonip, float latip){
        this.w = w;
        this.h = h;
        kx = 1.0f/latip;
        ky = -1.0f/lonip;
        mx = 0.5f*w-clon/latip;
        my = 0.5f*h+clat/lonip;
    }

    public float getX(float lon){
        return lon*kx+mx;
    }
    public float getY(float lat){
        return lat*ky+my;
    }

    public void draw(float srcLon, float srcLat, float dstLon, float dstLat, Canvas canvas, Paint paint){
        float x = getX(srcLon);
        float y = getY(srcLat);
        float dx = getX(dstLon);
        float dy = getY(dstLat);
        canvas.drawLine(x, y, dx, dy, paint);
    }
}
