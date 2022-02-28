package edu.sdsmt.team6.odetoballonstowerdefence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        //Get Views
        gameView = (GameView) findViewById(R.id.gameView);

        //Set Player's Names from the main activity screen
        Intent intent = getIntent();
        String playerOneName = intent.getStringExtra("edu.sdsmt.bloons.PlayerOneName");
        String playerTwoName = intent.getStringExtra("edu.sdsmt.bloons.PlayerTwoName");

        TextView playerOneTextView = findViewById(R.id.player1Name);
        TextView playerTwoTextView = findViewById(R.id.player2Name);
        playerOneTextView.setText(playerOneName);
        playerTwoTextView.setText(playerTwoName);


        //ViewModel Example Code
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        gameView = findViewById(R.id.gameView);
        viewModel.onGameSizeChanged(200, 200);
        viewModel.setNumBalloons(5);

        findViewById(R.id.gameView).addOnLayoutChangeListener(
                (v, left, top, right, bottom, lastLeft, lastTop, lastRight, lastBottom) -> {
                    viewModel.onGameSizeChanged(v.getWidth(), v.getHeight());
        });

        findViewById(R.id.gameView).setOnClickListener(v -> {
            viewModel.onInitialPress(0, 0);
        });

        viewModel.getCollectionArea().observe(this, collectionArea -> {
            // Set something in the ui with collection data.
            //this code will run any time the collection area object changes.
            if(collectionArea == null)
                Log.i("GameActivity", "collectionArea null");
            else
                Log.i("GameActivity",
                        "collectionX: " + collectionArea.getX() +
                                ", collectionY: " + collectionArea.getY() +
                                ", CollectionH: " + collectionArea.getHeight() +
                                ", collectionW: " + collectionArea.getWidth());
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

    }
}