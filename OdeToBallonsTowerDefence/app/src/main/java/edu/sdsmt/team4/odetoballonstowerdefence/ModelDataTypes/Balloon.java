package edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes;

public class Balloon {
    private final int xLocation;
    private final int yLocation;
    private boolean popped;

    public Balloon(int xLocation, int yLocation){
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

    public int getX(){
        return xLocation;
    }

    public int getY(){
        return yLocation;
    }

    public boolean isPopped(){
        return popped;
    }

    public void pop(){
        popped = true;
    }
}
