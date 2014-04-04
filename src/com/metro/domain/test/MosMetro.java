package com.metro.domain.test;

import com.metro.domain.Line;
import com.metro.domain.Metro;
import com.metro.domain.Pos;
import com.metro.domain.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by catahoc on 4/5/2014.
 */
public class MosMetro {
    public Metro CreateMetroOfMoskva(){
        int w = 5;
        int h = 5;
        Station[][] map = new Station[w][];
        for (int x = 0; x < w; ++x){
            map[x] = new Station[h];
            for (int y = 0; y < h; ++y) {
                if(x == y || (w-x-1) == y) {
                    Pos pos = new Pos(x, y);
                    Station s = new Station(String.format("s-{0}-{1}", x, y), pos);
                    map[x][y] = s;
                }
            }
        }
        Line line1 = new Line("Боготимирязевская");
        Line line2 = new Line("Сатаниско-Прозадрищенская");
        for (int ix = 0; ix < w; ++ix){
            line1.appendStation(map[ix][ix]);
            line2.appendStation(map[ix][w-ix-1]);
        }

        Line circle = new Line("Кольцево-Зацепочная");
        circle.appendStation(map[1][1]);
        circle.appendStation(map[1][3]);
        circle.appendStation(map[3][3]);
        circle.appendStation(map[3][1]);

        Line[] lines = {line1, line2, circle};
        return new Metro(Arrays.asList(lines));
    }

}
