package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

import java.util.Random;

public class CollectionLine extends CollectionArea{

    public CollectionLine(int xLocation, int yLocation) {
        super(xLocation, yLocation, 1, 100);
    }

    @Override
    public void checkBalloon(Balloon balloon) {
        //placeholder for actual logic
        if(new Random().nextInt(3) == 1){
            balloon.pop();
        }

    }
}
