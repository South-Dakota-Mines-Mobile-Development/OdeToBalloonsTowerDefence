package edu.sdsmt.team6.odetoballonstowerdefence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.Balloon;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionArea;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionCircle;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionLine;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionRectangle;

public class GameView extends View {
    private ArrayList<Balloon> bloons = null;
    private CollectionArea collectionArea = null;

    private Bitmap redBloon = null;
    private GameViewModel viewModel;

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

    public void setViewModel(GameViewModel viewModel){
        this.viewModel = viewModel;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                viewModel.onInitialPress((int)event.getX(), (int)event.getY());
                return true;

            case MotionEvent.ACTION_MOVE:
                viewModel.onMoveOrSecondaryPress((int)event.getX(), (int)event.getY());
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //If there are Bloons to be drawn, draw them
        drawBloons(canvas);

        drawCollectionArea(canvas);
    }

    public void setCollectionArea(CollectionArea collectionArea){
        this.collectionArea = collectionArea;
        invalidate();
    }

    public void setBloons(ArrayList<Balloon> bloons) {
        this.bloons = bloons;
        invalidate();
    }

    private  void drawBloons(Canvas canvas) {
        for (Balloon b : bloons
             ) {
            Log.i("Bloon Coordinates", "X Val: " + b.getX());
            Bitmap resizedBloon = Bitmap.createScaledBitmap(
                    redBloon, (int)(redBloon.getWidth() * 0.4), (int)(redBloon.getHeight() * 0.4), false);
            canvas.drawBitmap(resizedBloon, b.getX() - 74, b.getY() - 94, null);
        }
    }

    private void drawCollectionArea(Canvas canvas){
        if(collectionArea == null)
            return;

        if(collectionArea instanceof CollectionRectangle){
            Paint myPaint = new Paint();
            myPaint.setColor(Color.rgb(255, 0, 0));
            myPaint.setStrokeWidth(10);
            myPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(
                    collectionArea.getX(),
                    collectionArea.getY(),
                    collectionArea.getX() + collectionArea.getWidth(),
                    collectionArea.getY() + collectionArea.getHeight(),
                    myPaint);
        }
        else if(collectionArea instanceof CollectionCircle){
            Paint myPaint = new Paint();
            myPaint.setColor(Color.rgb(0, 255, 0));
            myPaint.setStrokeWidth(10);
            myPaint.setStyle(Paint.Style.STROKE);

            CollectionCircle collectionCircle = (CollectionCircle)collectionArea;
            canvas.drawCircle(collectionCircle.getX(),
                    collectionCircle.getY(),
                    collectionCircle.getRadius(),
                    myPaint);

        }
        else if(collectionArea instanceof CollectionLine){
            Paint myPaint = new Paint();
            myPaint.setColor(Color.rgb(0, 0, 255));
            myPaint.setStrokeWidth(10);
            myPaint.setStyle(Paint.Style.STROKE);

            canvas.drawLine(
                    collectionArea.getX(),
                    collectionArea.getY(),
                    collectionArea.getX() + collectionArea.getWidth(),
                    collectionArea.getY() + collectionArea.getHeight(),
                    myPaint);
        }
    }
}
