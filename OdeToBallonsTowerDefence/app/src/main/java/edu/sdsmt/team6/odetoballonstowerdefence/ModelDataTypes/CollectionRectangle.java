package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

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
        return betweenXCoor(b) && betweenYCoor(b);
    }

    private boolean betweenXCoor(Balloon b){
        int oppPointX = this.getX() + width;
        if(oppPointX < this.getX()){
            return b.getX() >= oppPointX && b.getX() <= this.getX();
        }else{
            return b.getX() >= this.getX() && b.getX() <= oppPointX;
        }
    }

    private boolean betweenYCoor(Balloon b){
        int oppPointY = this.getY() + height;
        if(oppPointY < this.getX()){
            return b.getX() >= oppPointY && b.getX() <= this.getX();
        }else{
            return b.getX() >= this.getX() && b.getX() <= oppPointY;
        }
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
