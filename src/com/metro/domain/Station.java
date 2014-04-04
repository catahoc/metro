package com.metro.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by catahoc on 4/5/2014.
 */
public class Station {
    private String _name;
    private Pos _pos;
    private List<StationOfLine> _inLines;

    public Station(String name, Pos pos) {
        _name = name;
        _pos = pos;
        _inLines = new ArrayList<StationOfLine>();
    }

    public void addSol(StationOfLine sol) {
        _inLines.add(sol);
    }
}
