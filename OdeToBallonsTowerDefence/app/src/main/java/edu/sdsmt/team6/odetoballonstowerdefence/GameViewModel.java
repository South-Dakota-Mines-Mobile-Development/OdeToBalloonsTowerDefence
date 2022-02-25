package edu.sdsmt.team6.odetoballonstowerdefence;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.Random;
>>>>>>> 584ebab (Basic drawing of bloons on the canvas)

import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.Balloon;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionArea;

public class GameViewModel extends ViewModel {
    private final MutableLiveData<CollectionArea> collectionArea = new MutableLiveData<>();
<<<<<<< HEAD
    private final MutableLiveData<ArrayList<Balloon>> balloons = new MutableLiveData<>();
    private final MutableLiveData<Boolean> canMakeMove = new MutableLiveData<>();
    private final GameModel gameModel;

    public GameViewModel(){
        gameModel = new GameModel();
        notifyStateChange();
=======
    private final MutableLiveData<ArrayList<Balloon>> bloons = new MutableLiveData<>();

    public GameViewModel(){

        collectionArea.setValue(new CollectionCircle(100, 100)
        );
>>>>>>> 584ebab (Basic drawing of bloons on the canvas)
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

    public void onGameSizeChanged(int screenWidth, int screenHeight){
        gameModel.setScreenSize(screenWidth, screenHeight);
        notifyStateChange();
    }

    public void onUpdateCollectionAreaType(int collectionAreaType){
        gameModel.setCollectionAreaType(collectionAreaType);
        notifyStateChange();
    }

    public void onInitialPress(int x, int y){
        gameModel.openCollectArea(x, y);
        notifyStateChange();
    }

    public void onMoveOrSecondaryPress(int x, int y){
        //gameModel function to be added
        //gameModel.updateSecondaryPoint(x, y);
        notifyStateChange();
    }

    public void onResetCollectionArea(){
        gameModel.closeCollectArea();
        notifyStateChange();
    }

    public void onMakeMove(){
        //gameModel function to be added
        //gameModel.makeMove();
        notifyStateChange();
    }





    private void notifyStateChange(){
        collectionArea.setValue(gameModel.getCollectionArea());
        balloons.setValue(gameModel.getBalloons());
        canMakeMove.setValue(gameModel.getCollectionArea() != null);
    }

}
