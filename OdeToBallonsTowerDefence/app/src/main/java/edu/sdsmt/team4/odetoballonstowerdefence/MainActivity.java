package edu.sdsmt.team4.odetoballonstowerdefence;

//Project 1 Grading
//       We discussed in email we will be team graded I believe, but didnt know what that mean so
//       folllowed check list as normal
//
//        Group:
//        done____ 6pt No redundant activities
//        done____ 6pt How to play dialog
//        done____ 6pt Icons
//        done____ 6pt End activity
//        done____ 6pt Back button handled (we handled it by not allowing turns to be reverted, but can return to previous screens)
//        How to open the "how to play dialog": ____done, press the how to play button
//
//        Individual:
//
//        Play activity and custom view
//
//        done____ 9pt Activity appearence
//        done____ 16pt Static Custom View
//        done____ 20pt Dynamic part of the Custom View
//        done____ 15pt Rotation
//
//        Welcome activity and Game Class
//
//        done____ 13pt Welcome activity appearence
//        done____ 20pt Applying capture rules
//        done____ 12pt Game state
//        done____ 15pt Rotation
//        What is the probaility
//        of the reactangle capture: _____the probability is based upon area or rectangle, to area
//        of the game area, it starts at 50% chance per ballon, and goes as low as 20% if the area
//        stay below 85 percent of game area. There is a 1% chance of capture if rect is larger than
//        85 percent game area, else it probability goes down 10 percent for every 20% increase in
//        rectangles area. ( if confused CollectionRectangle, has the function at bottom)
//
//        Capture activity and activity sequencing
//
//        done____ 9pt Capture activity apearence
//        done____ 16pt Player round sequencing
//        done____ 20pt Move to next activity
//        done____ 15pt Rotation
//
//        Timer
//
//        ____ 9pt Timer activity
//        ____ 24pt Graphic
//        ____ 12pt Player turn end
//        ____ 15pt Rotation
//
//
//        Please list any additional rules that may be needed to properly grade your project:
//      we have them randomly place in area, and I swear random is cursed so make sure to test
//      a few time if balloon not captured!
//      Depending on phone screen size you will need to scroll for how to play, and win screen!

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Cloud cloud = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);

        //Disables the top action bar
        Objects.requireNonNull(this.getSupportActionBar()).hide();

        MediaPlayer mediaPlayer= MediaPlayer.create(MainActivity.this,R.raw.maintheme);

        mediaPlayer.start();

        setContentView(R.layout.activity_main);

        Spinner numberDropdown = findViewById(R.id.numberSpinner);
        Integer[] numbers = new Integer[]{3, 5, 10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, numbers);
        numberDropdown.setAdapter(adapter);

        cloud = new Cloud();

        cloud.playersWaiting(numberDropdown, new CloudCallback() {
            @Override
            public void playersWaitingCallback(boolean join) {
                if (join) {
                    cloud.getNames(numberDropdown, new NameCallback() {
                        @Override
                        public void names(String p1, String p2) {
                            onGameStart(p1, p2);
                        }
                    });
                }
            }

            @Override
            public void turnCallback(boolean isPlayer1) {

            }
        });




    }

    public void onGameStart(String p1Name, String p2Name) {
        Intent intent = new Intent(this, GameActivity.class);

        Spinner numberDropdown = findViewById(R.id.numberSpinner);
        Integer roundNumber = (Integer)numberDropdown.getSelectedItem();

        intent.putExtra("edu.sdsmt.bloons.roundNumber", roundNumber);
        intent.putExtra("edu.sdsmt.bloons.PlayerOneName", p1Name);
        intent.putExtra("edu.sdsmt.bloons.PlayerTwoName", p2Name);

        startActivity(intent);
    }

    public void onBackPressed() {

        cloud.resetState();
        super.onBackPressed();
    }
}