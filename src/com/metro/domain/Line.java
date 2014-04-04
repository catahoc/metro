package com.metro.domain;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by catahoc on 4/5/2014.
 */
public class Line {
    public final String name;
    public final List<StationOfLine> ofStations;
    public final int color;

    public Line(String name, int color) {
        this.name = name;
        this.ofStations = new ArrayList<StationOfLine>();
        this.color = color;
    }

    public void appendStation(Station station){
        StationOfLine sol = new StationOfLine(station, this, ofStations.size());
        station.inLines.add(sol);
        ofStations.add(sol);
    }
}
