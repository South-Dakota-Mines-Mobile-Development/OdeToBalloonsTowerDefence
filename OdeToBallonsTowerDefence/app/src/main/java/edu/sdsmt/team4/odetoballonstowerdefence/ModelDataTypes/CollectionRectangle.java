package edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes;

import java.util.Random;

public class CollectionRectangle extends CollectionArea {

    public CollectionRectangle(int xLocation, int yLocation, int screenWidth, int screenHeight) {
        super(xLocation, yLocation, screenWidth, screenHeight);
    }

    @Override
    public void updateSecondaryPoint(int new_xLocation, int new_yLocation) {
        this.width = new_xLocation - this.getX();
        this.height = new_yLocation - this.getY();
    }

    @Override
    public void checkBalloon(Balloon balloon) {
        if(balloonInArea(balloon)){
            if(new Random().nextInt(100) <= chanceOfCapture()){
                balloon.pop();
            }
        }
    }

    private boolean balloonInArea(Balloon b){
        return betweenX(b) && betweenY(b);
    }

    private boolean betweenX(Balloon b){
        int left = Math.min(getX(), getX() + width);
        int right = Math.max(getX(), getX() + width);

        return b.getX() >= left && b.getX() <= right;
    }

    private boolean betweenY(Balloon b){
        int top = Math.min(getY(), getY() + height);
        int bottom = Math.max(getY(), getY() + height);

        return b.getY() >= top && b.getX() <= bottom;
    }

    private int chanceOfCapture(){
        if( (float)(width*height)/ (this.screenHeight * screenWidth) < 0.30){
            return 50;
        }
        else if( (float)(width*height)/ (this.screenHeight * screenWidth) < 0.50){
            return 40;
        }
        else if( (float)(width*height)/ (this.screenHeight * screenWidth) < 0.70){
            return 30;
        }
        else if( (float)(width*height)/ (this.screenHeight * screenWidth) < 0.85){
            return 20;
        }
        return 1;// your saying I got a chance!
    }
}
