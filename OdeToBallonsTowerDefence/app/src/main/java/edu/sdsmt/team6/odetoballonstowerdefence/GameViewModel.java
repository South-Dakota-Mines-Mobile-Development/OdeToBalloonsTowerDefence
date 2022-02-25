package edu.sdsmt.team6.odetoballonstowerdefence;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionArea;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionCircle;

public class GameViewModel extends ViewModel {
    private final MutableLiveData<CollectionArea> collectionArea = new MutableLiveData<>();

    public GameViewModel(){
        collectionArea.setValue(new CollectionCircle(100, 100));
    }

    public LiveData<CollectionArea> getCollectionArea() {
        return collectionArea;
    }

    public void onButtonClick(){
        Random random = new Random();

        collectionArea.setValue(new CollectionCircle(
                random.nextInt(100),
                random.nextInt(100)));
    }

}
