package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

import java.util.Random;

public class CollectionLine extends CollectionArea{


    public CollectionLine(int xLocation, int yLocation, int screenWidth, int screenHeight) {
        super(xLocation, yLocation, screenWidth, screenHeight);
    }

    @Override
    public void updateSecondaryPoint(int new_xLocation, int new_yLocation) {
        this.width = new_xLocation - this.getX();
        this.height = new_yLocation - this.getY();
    }

    @Override
    public void checkBalloon(Balloon balloon) {
        //check if in line!
        if(balloonInArea(balloon)){
            if(new Random().nextInt(100) >=25){//75 percent chance of capture!
                balloon.pop();
            }
        }
    }

    private boolean balloonInArea(Balloon b){
        return distanceFromLine(b) < 20.0;
    }

    private double distanceFromLine(Balloon b) {
        int secondXLoc = getX() + width;
        int secondYLoc = getY() + height;

        double xDelta = this.getX() - secondXLoc;
        double yDelta = this.getY() - secondYLoc;
        double u = ((b.getX() - this.getX()) * xDelta + (b.getY() - this.getY()) * yDelta) / (xDelta * xDelta + yDelta * yDelta);
        double tempX = this.getX() + u * xDelta;//closest point online to balloon
        double tempY = this.getY() + u * yDelta;

        return Math.sqrt(Math.pow(tempX - b.getX(), 2) + Math.pow(tempY - b.getY(), 2));//distance formula
    }
}
