package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

public abstract class CollectionArea {
    public static final int RECTANGLE = 0;
    public static final int CIRCLE = 1;
    public static final int LINE = 2;

    private int xLocation;
    private int yLocation;
    protected int width;
    protected int height;
    protected int screenWidth;
    protected int screenHeight;

    public CollectionArea(int xLocation, int yLocation, int screenWidth, int screenHeight) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public abstract void updateSecondaryPoint(int new_xLocation, int new_yLocation);

    public int getX(){
        return xLocation;
    }

    public int getY(){
        return yLocation;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public abstract void checkBalloon(Balloon balloon);
}
