package edu.sdsmt.team6.odetoballonstowerdefence;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionArea;

public class GameActivity extends AppCompatActivity {
    private GameViewModel viewModel;

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

        viewModel.getCollectionArea().observe(this, collectionArea -> {
            // Set something in the ui with collection data.
            //this code will run any time the collection area object changes.
            Log.i("GameActivity",
                    "collectionX: " + collectionArea.getX() +
                         ", collectionY: " + collectionArea.getY() +
                         ", CollectionH: " + collectionArea.getHeight() +
                         ", collectionW: " + collectionArea.getWidth()
            );
        });

        findViewById(R.id.viewModelExampleButton)
                .setOnClickListener(v -> viewModel.onButtonClick());

    }
}