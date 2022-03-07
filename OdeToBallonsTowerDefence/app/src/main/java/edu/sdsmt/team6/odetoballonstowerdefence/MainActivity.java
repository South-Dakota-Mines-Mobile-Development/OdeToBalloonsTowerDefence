package edu.sdsmt.team6.odetoballonstowerdefence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Disables the top action bar
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        MediaPlayer mainTheme= MediaPlayer.create(MainActivity.this,R.raw.maintheme);
        mainTheme.start();

        setContentView(R.layout.activity_main);

    }

    public void onGameStart(View view) {
        Intent intent = new Intent(this, GameActivity.class);

        TextView firstNameInput = (TextView)findViewById(R.id.playerOneTextbox);
        String playerOne = firstNameInput.getText().toString();

        TextView secondNameInput = (TextView)findViewById(R.id.playerTwoTextbox);
        String playerTwo = secondNameInput.getText().toString();

        intent.putExtra("edu.sdsmt.bloons.PlayerOneName", playerOne);
        intent.putExtra("edu.sdsmt.bloons.PlayerTwoName", playerTwo);

        startActivity(intent);
    }

    public void howToActivity(View view) {
        Intent intent = new Intent(this, HowToActivity.class);

        startActivity(intent);
    }
}