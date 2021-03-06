package edu.sdsmt.team4.odetoballonstowerdefence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.Balloon;
import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.CollectionArea;
import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.CollectionCircle;
import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.CollectionLine;
import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.CollectionRectangle;

public class GameView extends View {
    private ArrayList<Balloon> bloons = null;
    private CollectionArea collectionArea = null;
    private Button moveButton = null;
    private Button selectButton = null;
    private Bitmap redBloon = null;
    private GameViewModel viewModel;
    private boolean touchEnabled = false;

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
    public void setTouchEnabled(boolean enabled) {
        touchEnabled = enabled;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchEnabled){
            switch(event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    viewModel.onInitialPress((int)event.getX(), (int)event.getY());
                    return true;

                case MotionEvent.ACTION_MOVE:
                    viewModel.onMoveOrSecondaryPress((int)event.getX(), (int)event.getY());
                    return true;
            }
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
            canvas.drawBitmap(resizedBloon, b.getX()- (int)(redBloon.getWidth() * 0.4 / 2),
                    b.getY()- (int)(redBloon.getHeight() * 0.4 / 2), null);
        }
    }

    private void drawCollectionArea(Canvas canvas){
        if(collectionArea == null)
            return;

        if(collectionArea instanceof CollectionRectangle){
            int left = Math.min(collectionArea.getX(),
                    collectionArea.getX() + collectionArea.getWidth());
            int right = Math.max(collectionArea.getX(),
                            collectionArea.getX() + collectionArea.getWidth());
            int top = Math.min(collectionArea.getY(),
                    collectionArea.getY() + collectionArea.getHeight());
            int bottom = Math.max(collectionArea.getY(),
                    collectionArea.getY() + collectionArea.getHeight());

            Paint myPaint = new Paint();
            myPaint.setColor(Color.rgb(255, 0, 0));
            myPaint.setStrokeWidth(10);
            myPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(left, top, right, bottom, myPaint);
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

    public void saveJson(DatabaseReference db) {
        viewModel.saveJson(db);
    }

    public void loadJson(DataSnapshot db) {
        viewModel.loadJson(db);
    }

    public void setButtons(Button move, Button select) {
        moveButton = move;
        selectButton = select;
    }
}
