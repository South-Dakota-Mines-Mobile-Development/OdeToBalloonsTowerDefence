package edu.sdsmt.team6.odetoballonstowerdefence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Spinner numberDropdown = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Disables the top action bar
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        MediaPlayer mediaPlayer= MediaPlayer.create(MainActivity.this,R.raw.maintheme);

        mediaPlayer.start();

        setContentView(R.layout.activity_main);

        numberDropdown = findViewById(R.id.numberSpinner);
        Integer[] numbers = new Integer[]{3, 5, 10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item, numbers);
        numberDropdown.setAdapter(adapter);
    }

    public void onGameStart(View view) {
        Intent intent = new Intent(this, GameActivity.class);

        TextView firstNameInput = (TextView)findViewById(R.id.playerOneTextbox);
        String playerOne = firstNameInput.getText().toString();

        TextView secondNameInput = (TextView)findViewById(R.id.playerTwoTextbox);
        String playerTwo = secondNameInput.getText().toString();

        Integer roundNumber = (Integer)numberDropdown.getSelectedItem();

        intent.putExtra("edu.sdsmt.bloons.roundNumber", roundNumber);
        intent.putExtra("edu.sdsmt.bloons.PlayerOneName", playerOne);
        intent.putExtra("edu.sdsmt.bloons.PlayerTwoName", playerTwo);

        startActivity(intent);
    }

    public void howToActivity(View view) {
        Intent intent = new Intent(this, HowToActivity.class);

        startActivity(intent);
    }
}