package com.metro.domain;

/**
 * Created by catahoc on 4/5/2014.
 */
public class StationOfLine {
    private Station _station;
    private Line _line;
    private int _index;

    public StationOfLine(Station station, Line line, int index) {
        _station = station;
        _line = line;
        _index = index;
    }
}
