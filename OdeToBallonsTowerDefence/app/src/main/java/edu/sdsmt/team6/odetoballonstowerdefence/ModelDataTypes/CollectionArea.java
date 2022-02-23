package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

public abstract class CollectionArea {
    public static final int RECTANGLE = 0;
    public static final int CIRCLE = 1;
    public static final int LINE = 2;

    private int xLocation;
    private int yLocation;
    protected final int width;
    protected final int height;

    public CollectionArea(int xLocation, int yLocation, int width, int height) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.width = width;
        this.height = height;
    }

    public void move(int xLocation, int yLocation){
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

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
