package edu.sdsmt.team6.odetoballonstowerdefence;

import java.util.ArrayList;
import java.util.Random;

import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.Balloon;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionArea;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionCircle;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionLine;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionRectangle;

public class GameModel {
    private ArrayList<Balloon> balloons = new ArrayList<>();
    private int collectionAreaType = CollectionArea.RECTANGLE; //default is rectangle
    private CollectionArea collectionArea = null;

    public GameModel(int screenWidth, int screenHeight, int numBalloons){
        Random rand = new Random();

        for (int i = 0; i < numBalloons; i++) {
            //arbitrarily adding a 10 pixel padding around the screen
            int locX = rand.nextInt(screenWidth-20) + 10;
            int locY = rand.nextInt(screenHeight-20) + 10;

            //We may want to randomize these parameters
            int balloonHeight = 30;
            int balloonWidth = 10;

            balloons.add(new Balloon(locX, locY, balloonWidth, balloonHeight));
        }
    }

    public void openCollectArea(int x, int y){
        switch(collectionAreaType){
            case CollectionArea.RECTANGLE:
                collectionArea = new CollectionRectangle(x, y);
            case CollectionArea.CIRCLE:
                collectionArea = new CollectionCircle(x, y);
            case CollectionArea.LINE:
                collectionArea = new CollectionLine(x, y);
        }

        checkBalloons();
    }

    public void moveCollectArea(int x, int y){
        collectionArea.move(x, y);
        checkBalloons();
    }

    public void closeCollectArea(int x, int y){
        collectionArea = null;
    }

    public void setCollectionAreaType(int type){
        collectionAreaType = type;
    }



    public ArrayList<Balloon> getBalloons(){
        ArrayList<Balloon> unPoppedBalloons = new ArrayList<>();

        for (Balloon balloon: balloons) {
            if(!balloon.isPopped()){
                unPoppedBalloons.add(balloon);
            }
        }

        return unPoppedBalloons;
    }

    public int getNumPopped(){
        int popped = 0;

        for (Balloon balloon: balloons) {
            if(balloon.isPopped()){
                popped++;
            }
        }

        return popped;
    }

    public CollectionArea getCollectionArea(){
        return collectionArea;
    }

    private void checkBalloons(){
        for (Balloon balloon: balloons) {
            collectionArea.checkBalloon(balloon);
        }
    }
}
