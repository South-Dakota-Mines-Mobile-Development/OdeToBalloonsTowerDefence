package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

import java.util.Random;

public class CollectionCircle extends CollectionArea {

    private int radius = (int)Math.sqrt(((height/2)*(width/2))*2);

    private int minX = super.getX() - super.width - 5;
    private int maxX = super.getX() + super.width + 5;
    private int minY = super.getY() - super.height - 5;
    private int maxY = super.getY() + super.height + 5;

    public CollectionCircle(int xLocation, int yLocation, int width, int height, int screenWidth, int screenHeight) {
        super(xLocation, yLocation, width, height, screenWidth, screenHeight);
    }

    @Override
    public void checkBalloon(Balloon balloon) {
        if( balloonInArea(balloon)){
            balloon.pop();
        }
    }

    private boolean balloonInArea(Balloon b){
        if(b.getX() >= minX && b.getX() <= maxX && b.getY() >= minY && b.getY() <= maxY){
            return true;
        }
        return false;
    }

}
