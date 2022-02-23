package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

import java.util.Random;

public class CollectionRectangle extends CollectionArea {

    public CollectionRectangle(int xLocation, int yLocation) {
        super(xLocation, yLocation, 100, 150);
    }

    @Override
    public void checkBalloon(Balloon balloon) {
        //placeholder for actual logic
        if(new Random().nextInt(3) == 1){
            balloon.pop();
        }
    }
}
