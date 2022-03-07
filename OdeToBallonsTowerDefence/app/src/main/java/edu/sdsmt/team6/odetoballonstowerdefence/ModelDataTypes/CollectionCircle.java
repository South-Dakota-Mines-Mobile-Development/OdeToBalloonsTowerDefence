package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

public class CollectionCircle extends CollectionArea {

    private int radius;

    public CollectionCircle(int xLocation, int yLocation, int screenWidth, int screenHeight) {
        super(xLocation, yLocation, screenWidth, screenHeight);
    }

    public int getRadius(){
        return radius;
    }

    @Override
    public void updateSecondaryPoint(int new_xLocation, int new_yLocation) {
        int x_width = Math.abs(getX() - new_xLocation);
        int y_width = Math.abs(getY() - new_yLocation);
        this.radius = (int)distanceFormula(getX(), getY(), new_xLocation, new_yLocation);
        if(x_width > y_width){
            this.width = x_width + 5;
            this.height = x_width + 5;
        }
        else{
            this.width = y_width + 5;
            this.height = y_width + 5;
        }
    }

    @Override
    public void checkBalloon(Balloon balloon) {
        if( balloonInArea(balloon)){
            balloon.pop();
        }
    }

    private boolean balloonInArea(Balloon b){
        return distanceFormula(getX(), getY(), b.getX(), b.getY()) < radius + 5;
    }
}
