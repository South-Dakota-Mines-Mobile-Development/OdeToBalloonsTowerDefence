package edu.sdsmt.team6.odetoballonstowerdefence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Disables the top action bar
        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}

        setContentView(R.layout.activity_end_game);

        Intent gameOver = getIntent();

        int roundCount = gameOver.getIntExtra("edu.sdsmt.bloons.rounds", -1);
        int p1Score = gameOver.getIntExtra("edu.sdsmt.bloons.p1Score", -1);
        int p2Score = gameOver.getIntExtra("edu.sdsmt.bloons.p2Score", -1);
        String p1Name = gameOver.getStringExtra("edu.sdsmt.bloons.p1Name");
        String p2Name = gameOver.getStringExtra("edu.sdsmt.bloons.p2Name");

        TextView roundCountText = (TextView)findViewById(R.id.finalRoundCnt);
        TextView p1NameText = (TextView)findViewById(R.id.player1NameF);
        TextView p2NameText = (TextView)findViewById(R.id.player2NameF);
        TextView p2ScoreText = (TextView)findViewById(R.id.player2ScoreF);
        TextView p1ScoreText = (TextView)findViewById(R.id.player1ScoreF);

        roundCountText.setText("Rounds: " + String.valueOf(roundCount));
        p1NameText.setText(p1Name);
        p2NameText.setText(p2Name);
        p1ScoreText.setText(String.valueOf(p1Score));
        p2ScoreText.setText(String.valueOf(p2Score));


    }

    public void restartGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}