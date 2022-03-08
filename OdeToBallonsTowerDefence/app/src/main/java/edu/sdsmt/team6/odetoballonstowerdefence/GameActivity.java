package edu.sdsmt.team6.odetoballonstowerdefence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Objects;

import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionArea;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.PlayerModel;

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
        });

        viewModel.getIsGameOver().observe(this, isGameOver ->{
            //Handle the game end screen
            if (isGameOver == true) {
                Intent gameOver = new Intent(this, EndGameActivity.class);
                PlayerModel p1 = viewModel.getPlayerOne().getValue();
                PlayerModel p2 =  viewModel.getPlayerTwo().getValue();
                int currentRoundNumber = viewModel.getCurrentRoundNumber().getValue();
                gameOver.putExtra("edu.sdsmt.bloons.rounds", currentRoundNumber);
                gameOver.putExtra("edu.sdsmt.bloons.p1Score", p1.getScore());
                gameOver.putExtra("edu.sdsmt.bloons.p2Score", p2.getScore());
                gameOver.putExtra("edu.sdsmt.bloons.p1Name", p1.getName());
                gameOver.putExtra("edu.sdsmt.bloons.p2Name", p2.getName());

                startActivity(gameOver);
            }
        });

        viewModel.getCurrentPlayerTurn().observe(this, playerTurn ->{
            switch(playerTurn){
                case PLAYER_ONE:
                    ((TextView)findViewById(R.id.player1Label))
                            .setText(R.string.player1LabelActive);
                    ((TextView)findViewById(R.id.player2Label)).setText(R.string.player2Label);
                    break;
                case PLAYER_TWO:
                    ((TextView)findViewById(R.id.player2Label))
                            .setText(R.string.player2LabelActive);
                    ((TextView)findViewById(R.id.player1Label)).setText(R.string.player1Label);
                    break;
            }
        });

        viewModel.getCurrentRoundNumber().observe(this, roundNumber ->{
            ((TextView)findViewById(R.id.roundCounter))
                    .setText(String.valueOf(roundNumber));
        });

        findViewById(R.id.makeMoveButton)
                .setOnClickListener(v -> viewModel.onMakeMove());


        Intent intent = getIntent();
        String playerOneName = intent.getStringExtra("edu.sdsmt.bloons.PlayerOneName");
        String playerTwoName = intent.getStringExtra("edu.sdsmt.bloons.PlayerTwoName");
        viewModel.setNumBalloons(25);
        viewModel.setNumOfRounds(4);
        viewModel.setPlayerNames(playerOneName, playerTwoName);
        ((TextView)findViewById(R.id.player1Label))
                .setText(R.string.player1LabelActive);


    }

    public void selectionModeActivity(View view) {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                int selectionMode = data.getIntExtra("selectionMethod", -1);
                viewModel.onChangeCollectionAreaType(selectionMode);
            }
        }
    }
}