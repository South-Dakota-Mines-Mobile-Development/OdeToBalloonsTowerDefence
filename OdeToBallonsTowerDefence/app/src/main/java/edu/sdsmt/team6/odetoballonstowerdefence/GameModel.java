package edu.sdsmt.team6.odetoballonstowerdefence;

import java.util.ArrayList;
import java.util.Random;

import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.Balloon;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionArea;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionCircle;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionLine;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionRectangle;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.PlayerModel;

public class GameModel {

    public enum Playerturn{PLAYERONE, PLAYERTWO}

    private ArrayList<Balloon> balloons = new ArrayList<>();
    private ArrayList<Balloon> playerOneBloons = new ArrayList<>();
    private ArrayList<Balloon> playerTwoBloons = new ArrayList<>();
    private CollectionArea collectionArea = null;
    private Playerturn playerTurn = Playerturn.PLAYERONE;
    private PlayerModel playerOne= new PlayerModel("player1");
    private PlayerModel playerTwo = new PlayerModel("player2");
    private int roundNumber = 0;
    private int collectionAreaType = CollectionArea.RECTANGLE; //default is rectangle
    private int screenWidth;
    private int screenHeight;

    public GameModel(int screenWidth, int screenHeight, int numBalloons){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        Random rand = new Random();

        for (int i = 0; i < numBalloons; i++) {
            //arbitrarily adding a 10 pixel padding around the screen
            int locX = rand.nextInt(screenWidth-20) + 10;
            int locY = rand.nextInt(screenHeight-20) + 10;

            balloons.add(new Balloon(locX, locY));
        }
    }

    public void updateTurn(){
        if(playerTurn == Playerturn.PLAYERONE){
            playerTurn = Playerturn.PLAYERTWO;
        }
        else {
            playerTurn = Playerturn.PLAYERONE;
        }
    }

    public void updateRound(){
        roundNumber++;
    }

    public int getRound(){
        return roundNumber;
    }

    public void openCollectArea(int x, int y){
        switch(collectionAreaType){
            case CollectionArea.RECTANGLE:
                collectionArea = new CollectionRectangle(x, y, 100, 100, screenWidth, screenHeight);
            case CollectionArea.CIRCLE:
                collectionArea = new CollectionCircle(x, y, 100, 100, screenWidth, screenHeight);
            case CollectionArea.LINE:
                collectionArea = new CollectionLine(x, y, 100, 100, screenWidth, screenHeight);
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
            if(balloon.isPopped()){
                addToPlayersList(balloon);
            }
        }
    }

    private void addToPlayersList(Balloon b){
        if(playerTurn == Playerturn.PLAYERONE){
            playerOneBloons.add(b);
            playerOne.updateScore(playerOne.getScore() +1);
        }
        else{
            playerTwoBloons.add(b);
            playerTwo.updateScore(playerTwo.getScore() +1);
        }
    }
}
