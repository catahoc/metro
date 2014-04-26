package com.metro;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.metro.domain.*;
import com.metro.domain.test.MetroBuilder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {
    private float latip = 1/2000f;
    private float lonip = 1/4000f;
    private LinearLayout _surface; //55.739804,37.520167
    private ViewPort _viewPort;
    private View _canvasHolder;
    private Metro _metro;

    void createView(){
        _canvasHolder = new View(getApplicationContext()) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                Paint paint = new Paint();
                Paint stationPaint = new Paint();
                Paint textPaint = new Paint();
                textPaint.setStrokeWidth(13);
                textPaint.setColor(Color.RED);
                textPaint.setTextSize(16);
                stationPaint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.rgb(87, 12, 187));
                for(Line line: _metro.lines){
                    Pos prev = null;
                    paint.setColor(line.color);
                    List<StationOfLine> stations = line.ofStations;
                    if(line.isLooped){
                        stations = (List<StationOfLine>) ((ArrayList<StationOfLine>)stations).clone();
                        stations.add(stations.get(0));
                    }
                    boolean first = true;
                    for(StationOfLine sol:stations){
                        Station station = sol.station;
                        float x = _viewPort.getX(station.pos.x);
                        float y = _viewPort.getY(station.pos.y);
                        canvas.drawCircle(x, y, 3, stationPaint);
                        canvas.drawText(Integer.toString(station.id), x, y, textPaint);
                        if(!first){
                            Pos next = station.pos;
                            _viewPort.draw(prev.x, prev.y, next.x, next.y, canvas, paint);
                        }
                        else{
                            first = false;
                        }
                        prev = station.pos;
                    }
                }
                for(Transfer transfer: _metro.transfers){
                    Rect rect = Rect.compute(transfer.stations).convert(_viewPort);
                    float cx = (rect.x1+rect.x2)/2.0f;
                    float cy = (rect.y1+rect.y2)/2.0f;
                    float h = rect.getH();
                    float w = rect.getW();
                    float radius = (float) (Math.sqrt((h*h+w*w))/2.0f)+5.0f;
                    Paint tfp = new Paint();
                    tfp.setStyle(Paint.Style.FILL_AND_STROKE);
                    tfp.setColor(Color.GREEN);
                    tfp.setAlpha(100);
                    canvas.drawCircle(cx, cy, radius, tfp);
                }
            }
        };
    }

    void initViewPort(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        _viewPort = new ViewPort(size.x, size.y, 55.739804f, 37.520167f, 1/16000f, 1/8000f);
    }
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewPort();
        setContentView(R.layout.main);
        _surface = (LinearLayout) this.findViewById(R.id.surface);
        createView();
        _surface.addView(_canvasHolder);
        //_metro = new MetroBuilder().CreateMetroOfMoskva();
        _metro = new MetroBuilder().CreateMetroOfMoskvaReal(getResources());
        _canvasHolder.setOnTouchListener(new View.OnTouchListener() {
            Float x;
            Float y;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x  =event.getX();
                        y = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float newx  =event.getX();
                        float newy = event.getY();
                        float dx = newx-x;
                        float dy = newy-y;
                        _viewPort.moveCenter(-dx, -dy);
                        _canvasHolder.invalidate();
                        x = newx;
                        y = newy;
                        return true;
                }
                return false;
            }
        });
    }
}
