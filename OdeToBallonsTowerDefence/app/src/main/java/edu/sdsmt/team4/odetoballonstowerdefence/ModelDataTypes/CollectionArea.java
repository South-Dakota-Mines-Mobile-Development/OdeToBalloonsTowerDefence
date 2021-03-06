package edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes;

public abstract class CollectionArea {
    public static final int RECTANGLE = 0;
    public static final int CIRCLE = 1;
    public static final int LINE = 2;

    private final int xLocation;
    private final int yLocation;
    protected int width;
    protected int height;
    protected final int screenWidth;
    protected final int screenHeight;

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

    public double distanceFormula(double x1, double y1, double x2, double y2){
        return Math.sqrt( (x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
    }

    public abstract void checkBalloon(Balloon balloon);
}
