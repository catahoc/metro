package com.metro.domain;

/**
 * Created by catahoc on 4/5/2014.
 */
public class StationOfLine {
    public final Station station;
    public final Line line;
    public final int index;

    public StationOfLine(Station station, Line line, int index) {
        this.station = station;
        this.line = line;
        this.index = index;
    }
}
