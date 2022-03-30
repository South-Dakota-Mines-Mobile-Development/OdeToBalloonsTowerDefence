package edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class PlayerModel {

    private String name;
    private int score;

    public PlayerModel(String name){
        this.name = name;
        this.score = 0;
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        name = newName;
    }


    public int getScore(){
        return score;
    }

    public void updateScore(int newScore){
        score = newScore;
    }

    public void saveJson(DatabaseReference db, int index) {
        // use the string player and the int index to get player1 and player2
        DatabaseReference balloonDb = db.child("player" + index);

        balloonDb.child("name").setValue(name);
        balloonDb.child("score").setValue(score);
    }

    public void loadJson(DataSnapshot db, int index) {
        if (!db.hasChild("player" + index)) {
            Log.i("Failed", "loadJson: " + "player" + index + " does not exist in the database.");
            return;
        }
        // use the string player and the int index to get player1 and player2
        DataSnapshot balloonDb = db.child("player" + index);

        name = balloonDb.child("name").getValue().toString();
        score = Integer.parseInt(balloonDb.child("score").getValue().toString());
    }
}
