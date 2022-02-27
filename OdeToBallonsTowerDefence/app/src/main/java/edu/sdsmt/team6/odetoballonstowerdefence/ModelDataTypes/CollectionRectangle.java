package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

import java.util.Random;

public class CollectionRectangle extends CollectionArea {

    public CollectionRectangle(int xLocation, int yLocation, int screenWidth, int screenHeight) {
        super(xLocation, yLocation, screenWidth, screenHeight);
    }

    @Override
    public void updateSecondaryPoint(int new_xLocation, int new_yLocation) {
        this.width = Math.abs(this.getX() - new_xLocation) + 5;
        this.height = Math.abs(this.getY()- new_yLocation) + 5;
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
        return b.getX() >= this.getX() - 5 && b.getX() <= this.getX() + width &&
                b.getY() >= this.getY() - 5 && b.getY() <= this.getY() + height;
    }

    private int chanceOfCapture(){
        if( (float)(width*height)/ (this.screenHeight * screenWidth) < 0.15){
            return 50;
        }
        else if( (float)(width*height)/ (this.screenHeight * screenWidth) < 0.25){
            return 40;
        }
        else if( (float)(width*height)/ (this.screenHeight * screenWidth) < 0.35){
            return 30;
        }
        else if( (float)(width*height)/ (this.screenHeight * screenWidth) < 0.45){
            return 20;
        }
        return 1;// your saying I got a chance!
    }
}
