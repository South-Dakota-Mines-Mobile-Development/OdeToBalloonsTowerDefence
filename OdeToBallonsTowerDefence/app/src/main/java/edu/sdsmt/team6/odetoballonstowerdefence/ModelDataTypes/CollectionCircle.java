package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

import java.util.Random;

public class CollectionCircle extends CollectionArea {

    private float radius = (float)Math.sqrt(((height/2)*(width/2))*2);

    public CollectionCircle(int xLocation, int yLocation, int width, int height, int screenWidth, int screenHeight) {
        super(xLocation, yLocation, 100, 100, screenWidth, screenHeight);
    }

    @Override
    public void checkBalloon(Balloon balloon) {
        //placeholder for actual logic
        if(new Random().nextInt(3) == 1){
            balloon.pop();
        }
    }
}
