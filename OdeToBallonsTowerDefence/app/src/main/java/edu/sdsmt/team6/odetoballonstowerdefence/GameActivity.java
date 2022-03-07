package edu.sdsmt.team6.odetoballonstowerdefence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import java.util.Objects;

import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionArea;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionCircle;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionLine;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionRectangle;

public class GameActivity extends AppCompatActivity {
    private GameViewModel viewModel;
    private GameView gameView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Disables the top action bar
        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int gameViewSize = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);

        //Get Views
        gameView = findViewById(R.id.gameView);
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        viewModel.onGameSizeChanged(gameViewSize, gameViewSize);
        gameView.setViewModel(viewModel);


        findViewById(R.id.gameView).addOnLayoutChangeListener(
                (v, left, top, right, bottom, lastLeft, lastTop, lastRight, lastBottom) -> {
                    viewModel.onGameSizeChanged(v.getWidth(), v.getHeight());
        });

        viewModel.getPlayerOne().observe(this, playerOne -> {
            ((TextView)findViewById(R.id.player1Name)).setText(playerOne.getName());
            ((TextView)findViewById(R.id.player1Score)).setText(String.valueOf(playerOne.getScore()));
        });

        viewModel.getPlayerTwo().observe(this, playerTwo -> {
            ((TextView)findViewById(R.id.player2Name)).setText(playerTwo.getName());
            ((TextView)findViewById(R.id.player2Score)).setText(String.valueOf(playerTwo.getScore()));
        });

        viewModel.getCollectionArea().observe(this, collectionArea -> {
            gameView.setCollectionArea(collectionArea);
        });

        viewModel.getBalloons().observe(this, bloons -> gameView.setBloons(bloons));

        viewModel.getCanMakeMove().observe(this, canMakeMove ->{
            findViewById(R.id.makeMoveButton).setEnabled(canMakeMove);
            findViewById(R.id.resetMove).setEnabled(canMakeMove);
        });

        findViewById(R.id.makeMoveButton)
                .setOnClickListener(v -> viewModel.onMakeMove());

        findViewById(R.id.resetMove)
                .setOnClickListener(v -> viewModel.onResetCollectionArea());


        Intent intent = getIntent();
        String playerOneName = intent.getStringExtra("edu.sdsmt.bloons.PlayerOneName");
        String playerTwoName = intent.getStringExtra("edu.sdsmt.bloons.PlayerTwoName");
        viewModel.setNumBalloons(5);
        viewModel.setPlayerNames(playerOneName, playerTwoName);

        //Need to replace with activity switcher
        viewModel.onChangeCollectionAreaType(CollectionArea.LINE);

    }
}