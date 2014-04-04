package com.metro.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by catahoc on 4/5/2014.
 */
public class Line {
    private String _name;
    private List<StationOfLine> _ofStations;

    public Line(String name) {
        _name = name;
        _ofStations = new ArrayList<StationOfLine>();
    }

    public void appendStation(Station station){
        StationOfLine sol = new StationOfLine(station, this, _ofStations.size());
        station.addSol(sol);
        _ofStations.add(sol);
    }
}
