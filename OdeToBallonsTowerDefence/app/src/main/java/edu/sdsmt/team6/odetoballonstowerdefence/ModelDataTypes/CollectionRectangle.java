package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

import java.util.Random;

public class CollectionRectangle extends CollectionArea {

    public CollectionRectangle(int xLocation, int yLocation, int width, int height, int screenWidth, int screenHeight) {
        super(xLocation, yLocation, 100, 150, screenWidth, screenHeight);
    }

    @Override
    public void checkBalloon(Balloon balloon) {
        //placeholder for actual logic
        if(new Random().nextInt(3) == 1){
            balloon.pop();
        }
    }
}
