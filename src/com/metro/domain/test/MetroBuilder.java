package com.metro.domain.test;

import android.content.res.Resources;
import android.graphics.Color;
import com.metro.R;
import com.metro.domain.*;

import java.io.*;
import java.util.*;

/**
 * Created by catahoc on 4/5/2014.
 */
public class MetroBuilder {
    private class StationInfo{
        public final float X;
        public final float Y;
        public final String name;
        public final int id;

        private StationInfo(float x, float y, String name, int id) {
            this.X = x;
            Y = y;
            this.name = name;
            this.id = id;
        }
    }

    private class LineInfo{
        public int color;
        public boolean isLooped = false;
        public int id;
        public List<StationInfo> stations;

        public LineInfo(){
            stations = new ArrayList<StationInfo>();
        }
    }

    public Metro CreateMetroOfMoskvaReal(Resources resources){
        String[] csvStations = readLines(resources, R.raw.stations);
        String[] csvLines = readLines(resources, R.raw.lines);
        String[] csvTransfers = readLines(resources, R.raw.transfers);

        Map<String, LineInfo> linesToStationsMap = new HashMap<String, LineInfo>();
        for(String item : csvStations) {
            String[] splitted = item.split(",");
            String line = splitted[3];
            StationInfo info = new StationInfo(Float.parseFloat(splitted[5]), Float.parseFloat(splitted[4]), splitted[0], Integer.parseInt(splitted[6]));
            if(!linesToStationsMap.containsKey(line)){
                linesToStationsMap.put(line, new LineInfo());
            }
            linesToStationsMap.get(line).stations.add(info);
        }
        for(String item : csvLines){
            String[] splitted = item.split(",");
            int color = Color.rgb(Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]));
            String lineName = splitted[0];
            if(linesToStationsMap.containsKey(lineName)){
                LineInfo linfo = linesToStationsMap.get(lineName);
                if (splitted[5].equals("looped")) {
                    linfo.isLooped = true;
                }
                linfo.id = Integer.parseInt(splitted[6]);
                linfo.color = color;
            }
        }

        List<Line> lines = new ArrayList<Line>();
        List<Station> stations = new ArrayList<Station>();
        for(Map.Entry<String, LineInfo> entry: linesToStationsMap.entrySet()){
            LineInfo linfo = entry.getValue();
            Line line = new Line(entry.getKey(), linfo.color, linfo.isLooped, linfo.id);
            for(StationInfo info: linfo.stations){
                float x = info.X;
                float y = info.Y;
                Station station = new Station(info.name, new Pos(x, y), info.id);
                line.appendStation(station);
                stations.add(station);
            }
            lines.add(line);
        }

        ArrayList<Transfer> transfers = new ArrayList<Transfer>();
        for(String csvTransfer: csvTransfers){
            if(csvTransfer.startsWith(";") || csvTransfer.isEmpty()){
                continue;
            }
            String[] idstrs = csvTransfer.split(",");
            List<Station> tstations = new ArrayList<Station>();
            for(String idstr: idstrs){
                int id = Integer.parseInt(idstr);
                for(Station station: stations){
                    if(station.id == id){
                        tstations.add(station);
                    }
                }
            }
            transfers.add(new Transfer(tstations));
        }

        return new Metro(lines, stations, transfers);
    }

    public String[] readLines(Resources res, int resId) {
        try {
            InputStream stream = res.openRawResource(resId);
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            List<String> lines = new ArrayList<String>();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
            return lines.toArray(new String[lines.size()]);
        }
        catch(Exception ex){
            return null;
        }
    }

    public Metro CreateMetroOfMoskva(){
        int w = 5;
        int h = 5;
        Station[][] map = new Station[w][];
        List<Station> stations = new ArrayList<Station>();
        for (int x = 0; x < w; ++x){
            map[x] = new Station[h];
            for (int y = 0; y < h; ++y) {
                if(x == y || (w-x-1) == y) {
                    Pos pos = new Pos(x*180, y*150);
                    Station s = new Station(String.format("s-{0}-{1}", x, y), pos, y*w+x+1);
                    map[x][y] = s;
                    stations.add(s);
                }
            }
        }
        Line line1 = new Line("Таганско-Краснопресненская", Color.rgb(182, 29, 142), false, 1);
        Line line2 = new Line("Сокольническая", Color.RED, false, 2);
        for (int ix = 0; ix < w; ++ix){
            line1.appendStation(map[ix][ix]);
            line2.appendStation(map[ix][w-ix-1]);
        }

        Line circle = new Line("Кольцевая", Color.rgb(116, 92, 47), true, 3);
        circle.appendStation(map[1][1]);
        circle.appendStation(map[1][3]);
        circle.appendStation(map[3][3]);
        circle.appendStation(map[3][1]);
        circle.appendStation(map[1][1]);

        Line[] lines = {line1, line2, circle};
        return new Metro(Arrays.asList(lines), stations, new ArrayList<Transfer>());
    }

}
