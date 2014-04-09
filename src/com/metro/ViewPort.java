package com.metro;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by catahoc on 4/8/2014.
 */
public class ViewPort {
    private float my;
    private float mx;
    private float ky;
    private float kx;
    private float clon;
    private float clat;

    private final int w;
    private final int h;
    private final float lonip;
    private final float latip;

    public ViewPort(int w, int h, float clon, float clat, float lonip, float latip){
        this.w = w;
        this.h = h;
        this.lonip = lonip;
        this.latip = latip;
        this.clon = clon;
        this.clat = clat;
        changeCenter(clon, clat);
    }

    public void changeCenter(float clon, float clat){
        kx = 1.0f/latip;
        ky = -1.0f/lonip;
        mx = 0.5f*w-clat/latip;
        my = 0.5f*h+clon/lonip;
    }

    public float getX(float lon){
        return lon*kx+mx;
    }
    public float getY(float lat){
        return lat*ky+my;
    }

    public void moveCenter(float dx, float dy){
        clon -= dy*lonip;
        clat += dx*latip;
        changeCenter(clon, clat);
    }

    public void draw(float srcLon, float srcLat, float dstLon, float dstLat, Canvas canvas, Paint paint){
        float x = getX(srcLon);
        float y = getY(srcLat);
        float dx = getX(dstLon);
        float dy = getY(dstLat);
        canvas.drawLine(x, y, dx, dy, paint);
    }

    public float getClon() {
        return clon;
    }

    public float getClat() {
        return clat;
    }
}
