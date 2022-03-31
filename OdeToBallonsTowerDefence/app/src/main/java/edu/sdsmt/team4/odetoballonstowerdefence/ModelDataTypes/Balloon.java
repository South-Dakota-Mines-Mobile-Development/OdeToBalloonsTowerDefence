package edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class Balloon {
    private int xLocation;
    private int yLocation;
    private boolean popped;

    public Balloon(int xLocation, int yLocation){
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

    public int getX(){
        return xLocation;
    }

    public int getY(){
        return yLocation;
    }

    public boolean isPopped(){
        return popped;
    }

    public void pop(){
        popped = true;
    }

    public void saveJson(DatabaseReference db, int index, int screenWidth, int screenHeight) {
        DatabaseReference balloonDb = db.child("balloon" + index);
        // store location as relative position instead of pixel position
        balloonDb.child("x").setValue(xLocation/(float)screenWidth);
        balloonDb.child("y").setValue(yLocation/(float)screenHeight);
        balloonDb.child("popped").setValue(popped);
    }

    public void loadJson(DataSnapshot db, int index, int screenWitch, int screenHeight) {
        if (!db.hasChild("balloon" + index)) {
            Log.i("Failed", "loadJson: " + "balloon" + index + " does not exist in the database.");
            return;
        }
        DataSnapshot balloonDb = db.child("balloon" + index);
        // convert locations relative position on server to pixels of local device
        xLocation = (int)(Float.parseFloat(balloonDb.child("x").getValue().toString()) * screenWitch);
        yLocation = (int)(Float.parseFloat(balloonDb.child("y").getValue().toString()) * screenHeight);
        popped = (boolean)balloonDb.child("popped").getValue();
    }
}
