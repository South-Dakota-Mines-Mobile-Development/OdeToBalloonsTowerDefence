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

    public enum PlayerTurn {PLAYER_ONE, PLAYER_TWO}

    private ArrayList<Balloon> balloons = new ArrayList<>();
    private CollectionArea collectionArea = null;
    private PlayerTurn playerTurn = PlayerTurn.PLAYER_ONE;
    private PlayerModel playerOne= new PlayerModel("player1");
    private PlayerModel playerTwo = new PlayerModel("player2");
    private int roundNumber = 0;
    private int roundsToEnd;
    private int collectionAreaType = CollectionArea.RECTANGLE; //default is rectangle
    private int screenWidth;
    private int screenHeight;

    public void setScreenSize(int screenWidth, int screenHeight){//may need to move balloons location?
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

    }

    public void setPlayerNames(String playerOneName, String playerTwoName){
        playerOne.setName(playerOneName);
        playerTwo.setName(playerTwoName);
    }

    public void setNumBalloons(int numBalloons){//may want to clear old balloons?
        Random rand = new Random();

        for (int i = 0; i < numBalloons; i++) {
            //arbitrarily adding a 10 pixel padding around the screen
            int locX = rand.nextInt(screenWidth-20) + 10;
            int locY = rand.nextInt(screenHeight-20) + 10;

            balloons.add(new Balloon(locX, locY));
        }
    }

    public void updateTurn(){
        if(playerTurn == PlayerTurn.PLAYER_ONE){
            playerTurn = PlayerTurn.PLAYER_TWO;
        }
        else {
            playerTurn = PlayerTurn.PLAYER_ONE;
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
                collectionArea = new CollectionRectangle(x, y, screenWidth, screenHeight);
                break;
            case CollectionArea.CIRCLE:
                collectionArea = new CollectionCircle(x, y, screenWidth, screenHeight);
                break;
            case CollectionArea.LINE:
                collectionArea = new CollectionLine(screenWidth/2, screenHeight, screenWidth, screenHeight);
                break;
        }
    }

    public void updateSecondaryPoint(int x, int y){
        collectionArea.updateSecondaryPoint(x,y);
    }

    public void makeMove(){
        checkBalloons();
        closeCollectArea();
        if(playerTurn == PlayerTurn.PLAYER_TWO){
            updateRound();
        }
        updateTurn();
    }

    public void closeCollectArea(){
        collectionArea = null;
    }

    public void setCollectionAreaType(int type){
        closeCollectArea();
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

    public PlayerModel GetPlayerOne(){
        return playerOne;
    }

    public PlayerModel GetPlayerTwo(){
        return playerTwo;
    }

    public CollectionArea getCollectionArea(){
        return collectionArea;
    }

    private void checkBalloons(){
        for (Balloon balloon: balloons) {
            if(!balloon.isPopped()){
                collectionArea.checkBalloon(balloon);
                if(balloon.isPopped()){
                    addToPlayersList(balloon);
                }
            }
        }
    }

    private void addToPlayersList(Balloon b){
        if(playerTurn == PlayerTurn.PLAYER_ONE){
            playerOne.updateScore(playerOne.getScore() +1);
        }
        else{
            playerTwo.updateScore(playerTwo.getScore() +1);
        }
    }

    public void setNumberOfRounds(int round){
        roundsToEnd = round;
    }

    public boolean gameIsOver(){
        return roundsToEnd == roundNumber;
    }
}
