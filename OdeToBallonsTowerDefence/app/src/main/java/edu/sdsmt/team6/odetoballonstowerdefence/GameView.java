package edu.sdsmt.team6.odetoballonstowerdefence;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.Balloon;

public class GameView extends View {
    private ArrayList<Balloon> bloons = null;

    private Bitmap redBloon = null;

    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {

        //Any view setup (initial drawing of Bloons?)
        Resources res = getResources();
        redBloon = BitmapFactory.decodeResource(res, R.drawable.redbloon);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //If there are Bloons to be drawn, draw them
        drawBloons(canvas);
    }

    public void setBloons() {
        bloons = new ArrayList<Balloon>();
        bloons.add(new Balloon(300, 5, 10, 20));
        bloons.add(new Balloon(300, 500, 10, 20));
        bloons.add(new Balloon(100, 200, 10, 20));

        invalidate();
    }

    private  void drawBloons(Canvas canvas) {
        for (Balloon b : bloons
             ) {
            Log.i("Bloon Coordinates", "X Val: " + b.getX());
            canvas.drawBitmap(redBloon, b.getX(), b.getY(), null);
        }
    }
}
