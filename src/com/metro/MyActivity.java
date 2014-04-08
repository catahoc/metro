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
import android.view.View;
import android.widget.LinearLayout;
import com.metro.domain.*;
import com.metro.domain.test.MetroBuilder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class MyActivity extends Activity {
    private LinearLayout _surface; //55.739804,37.520167
    private ViewPort _viewPort;
    private Metro _metro;

    void initViewPort(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        _viewPort = new ViewPort(size.x, size.y, 37.520167f, 55.739804f, 1/4000f, 1/2000f);
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
        _surface.addView(new View(getApplicationContext()) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                Paint paint = new Paint();
                Paint stationPaint = new Paint();
                stationPaint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.rgb(87, 12, 187));
                paint.setStrokeWidth(2);
                for(Line line: _metro.lines){
                    Pos prev = null;
                    paint.setColor(line.color);
                    for(StationOfLine sol:line.ofStations){
                        Station station = sol.station;
                        float x = _viewPort.getX(station.pos.x);
                        float y = _viewPort.getY(station.pos.y);
                        canvas.drawCircle(x, y, 3, stationPaint);
                        if(sol.index != 0){
                            Pos next = station.pos;
                            _viewPort.draw(prev.x, prev.y, next.x, next.y, canvas, paint);
                        }
                        prev = station.pos;
                    }
                }
            }


            public float getX(Pos pos){
                return pos.x;
            }
            public float getY(Pos pos){
                return pos.y;
            }
        });
        //_metro = new MetroBuilder().CreateMetroOfMoskva();
        _metro = new MetroBuilder().CreateMetroOfMoskvaReal(getResources());
    }
}
