package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

import java.util.Random;

public class CollectionLine extends CollectionArea{

    public CollectionLine(int xLocation, int yLocation, int screenWidth, int screenHeight) {
        super(xLocation, yLocation, screenWidth, screenHeight);
    }

    @Override
    public void updateSecondaryPoint(int new_xLocation, int new_yLocation) {

    }

    @Override
    public void checkBalloon(Balloon balloon) {
        //placeholder for actual logic
        if(new Random().nextInt(3) == 1){
            balloon.pop();
        }

    }
}
