package edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes;

import java.util.Random;

public class CollectionLine extends CollectionArea{


    public CollectionLine(int xLocation, int yLocation, int screenWidth, int screenHeight) {
        super(xLocation, yLocation, screenWidth, screenHeight);
    }

    @Override
    public void updateSecondaryPoint(int new_xLocation, int new_yLocation) {
        double lengthOfLine = distanceFormula(getX(), getY(), new_xLocation, new_yLocation);
        if(lengthOfLine >= screenWidth * 0.2 && lengthOfLine <= screenWidth * 0.8){
            this.width = new_xLocation - this.getX();
            this.height = new_yLocation - this.getY();
        }
        else if(lengthOfLine <= screenWidth* 0.2){
            this.width = adjustCoordinateForDistance(getX(), new_xLocation, lengthOfLine, screenWidth * 0.2) - this.getX();
            this.height = adjustCoordinateForDistance(getY(), new_yLocation, lengthOfLine, screenWidth * 0.2) - this.getY();
        }
        else if(distanceFormula(getX(), getY(), new_xLocation, new_yLocation) >= screenWidth* 0.8){
            this.width = adjustCoordinateForDistance(getX(), new_xLocation, lengthOfLine, screenWidth * 0.8) - this.getX();
            this.height = adjustCoordinateForDistance(getY(), new_yLocation, lengthOfLine, screenWidth * 0.8) - this.getY();
        }
    }

    private int adjustCoordinateForDistance(int v1, int v2, double currentLength, double desiredLength){
        double delta = (v2 - v1)/ currentLength;
        return (int)(v1 + (desiredLength) * delta);
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
        return distanceFromLine(b) < 30.0;
    }

    private double distanceFromLine(Balloon b) {
        int secondXLoc = getX() + width;
        int secondYLoc = getY() + height;

        double xDelta = this.getX() - secondXLoc;
        double yDelta = this.getY() - secondYLoc;
        double u = ((b.getX() - this.getX()) * xDelta + (b.getY() - this.getY()) * yDelta) / (xDelta * xDelta + yDelta * yDelta);
        double tempX = this.getX() + u * xDelta;//closest point on line to balloon
        double tempY = this.getY() + u * yDelta;

        if( b.getY() < screenWidth * 0.2 + 10 || b.getY() < secondYLoc - 10 ){
            return 1000.0;
        }
        else {
            return distanceFormula(b.getX(), b.getY(), tempX, tempY);
        }

    }

}
