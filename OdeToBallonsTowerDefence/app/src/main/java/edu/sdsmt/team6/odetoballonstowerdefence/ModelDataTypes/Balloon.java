package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

public class Balloon {
    private final int xLocation;
    private final int yLocation;
    private final int width;
    private final int height;
    private boolean popped;

    public Balloon(int xLocation, int yLocation, int width, int height){
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.width = width;
        this.height = height;
    }

    public int getX(){
        return xLocation;
    }

    public int getY(){
        return yLocation;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public boolean isPopped(){
        return popped;
    }

    public void pop(){
        popped = true;
    }
}
