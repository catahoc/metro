package com.metro.domain;

import java.util.List;

/**
 * Created by catahoc on 4/5/2014.
 */
public class Metro {
    public final List<Line> lines;
    public final List<Station> stations;

    public Metro(List<Line> lines, List<Station> stations) {
        this.stations = stations;
        this.lines = lines;
    }
}
