package edu.sdsmt.team4.odetoballonstowerdefence;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Random;

import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.Balloon;
import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.CollectionArea;
import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.CollectionCircle;
import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.CollectionLine;
import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.CollectionRectangle;
import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.PlayerModel;

public class GameModel {

    public enum PlayerTurn {PLAYER_ONE, PLAYER_TWO}

    private final ArrayList<Balloon> balloons = new ArrayList<>();
    private CollectionArea collectionArea = null;
    private PlayerTurn playerTurn = PlayerTurn.PLAYER_ONE;
    private final PlayerModel playerOne= new PlayerModel("player1");
    private final PlayerModel playerTwo = new PlayerModel("player2");
    private int roundNumber = 0;
    private int roundsToEnd;
    private int collectionAreaType = CollectionArea.RECTANGLE; //default is rectangle
    private int screenWidth;
    private int screenHeight;
    private Boolean initialized = false;

    public void setScreenSize(int screenWidth, int screenHeight){//may need to move balloons location?
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

    }

    public void setPlayerNames(String playerOneName, String playerTwoName){
        playerOne.setName(playerOneName);
        playerTwo.setName(playerTwoName);
    }

    public void setNumBalloons(int numBalloons){//may want to clear old balloons?
        if(!initialized){
            Random rand = new Random();
            for (int i = 0; i < numBalloons; i++) {
                //arbitrarily adding a 20 pixel padding around the screen
                int locX = rand.nextInt(screenWidth-40) + 20;
                int locY = rand.nextInt(screenHeight-40) + 20;

                balloons.add(new Balloon(locX, locY));
            }
            initialized = true;
        }
    }

    public PlayerTurn getPlayerTurn(){
        return playerTurn;
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
        for (Balloon balloon: getBalloons()) {
            collectionArea.checkBalloon(balloon);
            if(balloon.isPopped()){
                addToPlayersList(balloon);
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
        return roundsToEnd == roundNumber || getBalloons().size() == 0;
    }

    /**
     * Function to save the players and balloons to the server.
     *
     * @param db Database reference to the current game being played.
     */
    public void saveJson(DatabaseReference db) {
        // save players
        playerOne.saveJson(db, 1);
        playerTwo.saveJson(db, 2);

        for (int i = 1; i <= balloons.size(); i++) {
            // passed screen height and width to store relative coordinates
            balloons.get(i - 1).saveJson(db, i, screenWidth, screenHeight);
        }
    }

    /**
     * Function to load data from the database.
     *
     * @param db Snapshot of the game currently being played.
     */
    public void loadJson(DataSnapshot db) {
        // load players
        playerOne.loadJson(db, 1);
        playerTwo.loadJson(db, 2);

        for (int i = 1; i <= balloons.size(); i++) {
            // passed screen height and width to store relative coordinates
            balloons.get(i - 1).loadJson(db, i, screenWidth, screenHeight);
        }
    }
}
