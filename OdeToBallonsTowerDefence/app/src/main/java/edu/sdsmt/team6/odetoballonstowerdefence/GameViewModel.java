package edu.sdsmt.team6.odetoballonstowerdefence;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.Balloon;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionArea;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionRectangle;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.PlayerModel;

public class GameViewModel extends ViewModel {
    private final MutableLiveData<CollectionArea> collectionArea = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Balloon>> balloons = new MutableLiveData<>();
    private final MutableLiveData<Boolean> canMakeMove = new MutableLiveData<>();
    private final MutableLiveData<PlayerModel> playerOne = new MutableLiveData<>();
    private final MutableLiveData<PlayerModel> playerTwo = new MutableLiveData<>();
    private final MutableLiveData<Integer> currentRoundNumber = new MutableLiveData<>();
    private final MutableLiveData<GameModel.PlayerTurn> currentPlayerTurn = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    private final GameModel gameModel;

    public GameViewModel(){
        gameModel = new GameModel();
        notifyStateChange();
    }

    public void setNumBalloons(int numBalloons){
        gameModel.setNumBalloons(numBalloons);
        notifyStateChange();
    }

    public void setPlayerNames(String playerOneName, String playerTwoName){
        gameModel.setPlayerNames(playerOneName, playerTwoName);
        notifyStateChange();
    }

    public void setNumOfRounds(int numRounds){
        gameModel.setNumberOfRounds(numRounds);
    }

    public LiveData<CollectionArea> getCollectionArea() {
        return collectionArea;
    }

    public LiveData<ArrayList<Balloon>> getBalloons() {
        return balloons;
    }

    public LiveData<Boolean> getCanMakeMove() {
        return canMakeMove;
    }

    public LiveData<PlayerModel> getPlayerOne(){
        return playerOne;
    }

    public LiveData<PlayerModel> getPlayerTwo(){
        return playerTwo;
    }

    public LiveData<Integer> getCurrentRoundNumber(){
        return currentRoundNumber;
    }

    public LiveData<GameModel.PlayerTurn> getCurrentPlayerTurn(){
        return currentPlayerTurn;
    }

    public LiveData<Boolean> getIsGameOver(){
        return isGameOver;
    }




    public void onGameSizeChanged(int screenWidth, int screenHeight){
        gameModel.setScreenSize(screenWidth, screenHeight);
        notifyStateChange();
    }

    public void onInitialPress(int x, int y){
        gameModel.openCollectArea(x, y);
        notifyStateChange();
    }

    public void onMoveOrSecondaryPress(int x, int y){
        gameModel.updateSecondaryPoint(x, y);
        notifyStateChange();
    }

    public void onResetCollectionArea(){
        gameModel.closeCollectArea();
        notifyStateChange();
    }

    public void onMakeMove(){
        gameModel.makeMove();
        notifyStateChange();
    }

    public void onChangeCollectionAreaType(int collectionAreaType){
        gameModel.setCollectionAreaType(collectionAreaType);
    }

    private void notifyStateChange(){
        collectionArea.setValue(gameModel.getCollectionArea());
        balloons.setValue(gameModel.getBalloons());
        canMakeMove.setValue(gameModel.getCollectionArea() != null);
        playerOne.setValue(gameModel.GetPlayerOne());
        playerTwo.setValue(gameModel.GetPlayerTwo());
        currentRoundNumber.setValue(gameModel.getRound());
        currentPlayerTurn.setValue(gameModel.getPlayerTurn());
        isGameOver.setValue(gameModel.gameIsOver());
    }
}
