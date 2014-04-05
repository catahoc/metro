package com.metro;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
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
    private LinearLayout _surface;
    private Metro _metro;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        _surface = (LinearLayout) this.findViewById(R.id.surface);
        _surface.addView(new View(getApplicationContext()) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.rgb(87, 12, 187));
                paint.setStrokeWidth(10);
                for(Line line: _metro.lines){
                    Pos prev = null;
                    paint.setColor(line.color);
                    for(StationOfLine sol:line.ofStations){
                        Station station = sol.station;
                        if(sol.index != 0){
                            Pos next = station.pos;
                            canvas.drawLine(getX(prev), getY(prev), getX(next), getY(next), paint);
                        }
                        prev = station.pos;
                    }
                }
            }


            public float getX(Pos pos){
                return pos.x * 180;
            }
            public float getY(Pos pos){
                return pos.y * 150;
            }
        });
        _metro = new MetroBuilder().CreateMetroOfMoskvaReal(getResources());
    }
}
