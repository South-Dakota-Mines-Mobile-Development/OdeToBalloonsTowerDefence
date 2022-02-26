package edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes;

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
}
