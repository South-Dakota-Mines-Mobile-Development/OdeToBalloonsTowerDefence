package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

import java.util.Random;

public class CollectionLine extends CollectionArea{

    public CollectionLine(int xLocation, int yLocation, int screenWidth, int screenHeight) {
        super(xLocation, yLocation, screenWidth, screenHeight);
    }

    @Override
    public void updateSecondaryPoint(int new_xLocation, int new_yLocation) {
        this.width = Math.abs(this.getX() - new_xLocation) + 5;
        this.height = Math.abs(this.getY()- new_yLocation) + 5;
    }

    @Override
    public void checkBalloon(Balloon balloon) {
        //check if in line!
        if(new Random().nextInt(100) >=25){//75 percent chance of capture!
            balloon.pop();
        }

    }

    private boolean balloonInArea(Balloon b){

        return false;
    }
}
