package com.metro.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by catahoc on 4/5/2014.
 */
public class Station {
    public final String name;
    public final Pos pos;
    public final List<StationOfLine> inLines;
    public final int id;

    public Station(String name, Pos pos, int id) {
        this.name = name;
        this.pos = pos;
        this.inLines = new ArrayList<StationOfLine>();
        this.id = id;
    }

}
