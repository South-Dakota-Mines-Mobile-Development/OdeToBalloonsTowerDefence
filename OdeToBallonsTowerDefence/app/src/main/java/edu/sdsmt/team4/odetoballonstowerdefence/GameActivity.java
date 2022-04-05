package edu.sdsmt.team4.odetoballonstowerdefence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.PlayerModel;

public class GameActivity extends AppCompatActivity {
    private GameViewModel viewModel;
    private GameView gameView = null;
    private Cloud cloud = null;
    private Button moveButton = null;
    private Button selectButton = null;
    // replace with check in state on database!
    private boolean isPlayer1 = false;

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
        moveButton = findViewById(R.id.makeMoveButton);
        selectButton = findViewById(R.id.selectMode);
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        viewModel.onGameSizeChanged(gameViewSize, gameViewSize);
        gameView.setViewModel(viewModel);
        gameView.setButtons(moveButton, selectButton);
        cloud = new Cloud();
        cloud.init(gameView, isPlayer1);

        findViewById(R.id.gameView).addOnLayoutChangeListener(
                (v, left, top, right, bottom, lastLeft, lastTop, lastRight, lastBottom) ->
                        viewModel.onGameSizeChanged(v.getWidth(), v.getHeight()));

        viewModel.getPlayerOne().observe(this, playerOne -> {
            ((TextView)findViewById(R.id.player1Name)).setText(playerOne.getName());
            ((TextView)findViewById(R.id.player1Score)).setText(String.valueOf(playerOne.getScore()));
        });

        viewModel.getPlayerTwo().observe(this, playerTwo -> {
            ((TextView)findViewById(R.id.player2Name)).setText(playerTwo.getName());
            ((TextView)findViewById(R.id.player2Score)).setText(String.valueOf(playerTwo.getScore()));
        });

        viewModel.getCollectionArea().observe(this, collectionArea ->
                gameView.setCollectionArea(collectionArea));

        viewModel.getBalloons().observe(this, bloons -> gameView.setBloons(bloons));

        viewModel.getCanMakeMove().observe(this, canMakeMove ->
                findViewById(R.id.makeMoveButton).setEnabled(canMakeMove));

        viewModel.getIsGameOver().observe(this, isGameOver ->{
            //Handle the game end screen
            if (isGameOver) {
                Intent gameOver = new Intent(this, EndGameActivity.class);
                PlayerModel p1 = viewModel.getPlayerOne().getValue();
                PlayerModel p2 =  viewModel.getPlayerTwo().getValue();
                int currentRoundNumber = viewModel.getCurrentRoundNumber().getValue();
                gameOver.putExtra("edu.sdsmt.bloons.rounds", currentRoundNumber);
                assert p1 != null;
                gameOver.putExtra("edu.sdsmt.bloons.p1Score", p1.getScore());
                assert p2 != null;
                gameOver.putExtra("edu.sdsmt.bloons.p2Score", p2.getScore());
                gameOver.putExtra("edu.sdsmt.bloons.p1Name", p1.getName());
                gameOver.putExtra("edu.sdsmt.bloons.p2Name", p2.getName());
                cloud.resetState();
                startActivity(gameOver);
            }
        });

        viewModel.getCurrentPlayerTurn().observe(this, playerTurn ->{
            switch(playerTurn){
                case PLAYER_ONE:
                    ((TextView)findViewById(R.id.player1Label))
                            .setText(R.string.player1LabelActive);
                    ((TextView)findViewById(R.id.player2Label)).setText(R.string.player2Label);
                    cloud.turn(gameView, new CloudCallback() {
                        @Override
                        public void playersWaitingCallback(boolean p1Waiting, boolean p2Waiting) {
                            // should never be called
                        }

                        @Override
                        public void turnCallback(boolean isPlayer1) {
                            if (isPlayer1) {
                                selectButton.setEnabled(true);
                                gameView.setTouchEnabled(true);
                            }
                            else {
                                selectButton.setEnabled(false);
                                gameView.setTouchEnabled(false);
                                moveButton.setEnabled(false);
                            }
                        }
                    });
                    break;
                case PLAYER_TWO:
                    ((TextView)findViewById(R.id.player2Label))
                            .setText(R.string.player2LabelActive);
                    ((TextView)findViewById(R.id.player1Label)).setText(R.string.player1Label);
                    cloud.turn(gameView, new CloudCallback() {
                        @Override
                        public void playersWaitingCallback(boolean p1Waiting, boolean p2Waiting) {
                            // should never be called
                        }

                        @Override
                        public void turnCallback(boolean isPlayer1) {
                            if (!isPlayer1) {
                                selectButton.setEnabled(true);
                                gameView.setTouchEnabled(true);
                            }
                            else {
                                selectButton.setEnabled(false);
                                gameView.setTouchEnabled(false);
                                moveButton.setEnabled(false);
                            }
                        }
                    });
                    break;
            }
        });

        viewModel.getCurrentRoundNumber().observe(this, roundNumber ->
                ((TextView)findViewById(R.id.roundCounter))
                    .setText(String.valueOf(roundNumber)));

        findViewById(R.id.makeMoveButton)
                .setOnClickListener(v -> {
                    viewModel.onMakeMove();
                    cloud.saveToCloud(gameView);
                });


        Intent intent = getIntent();
        String playerOneName = intent.getStringExtra("edu.sdsmt.bloons.PlayerOneName");
        String playerTwoName = intent.getStringExtra("edu.sdsmt.bloons.PlayerTwoName");
        int roundCount = intent.getIntExtra("edu.sdsmt.bloons.roundNumber", -1);
        viewModel.setNumBalloons(25);
        viewModel.setNumOfRounds(roundCount);
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

/* Project 1 Grading
 *
 * We chose to be graded as a group for this project
 *
 *
 * Group:
 *      ✓____ 6pt No redundant activities
 *      ✓____ 6pt How to play dialog
 *      ✓____ 6pt Icons
 *      ✓____ 6pt End activity
 *      ✓____ 6pt Back button handled
 * How to open the "how to play dialog":
 *      - Click the "How to play" button on the start screen
 *
 * Individual:
 *
 * 	Play activity and custom view
 *
 * 		✓____ 9pt Activity appearance
 * 		✓____ 16pt Static Custom View
 * 		✓____ 20pt Dynamic part of the Custom View
 * 		✓____ 15pt Rotation
 *
 * 	Welcome activity and Game Class
 *
 * 		✓____ 13pt Welcome activity appearance
 * 		✓____ 20pt Applying capture rules
 * 		✓____ 12pt Game state
 * 		✓____ 15pt Rotation
 * 		What is the probability of the rectangle capture:
 *          - If the rectangle is <30% of the screen 50%
 *          - If the rectangle is <50% of the screen 40%
 *          - If the rectangle is <70% of the screen 30%
 *          - If the rectangle is <85% of the screen 20%
 *          - If the rectangle is >85% of the screen 1%
 *
 * 	Capture activity and activity sequencing
 *
 * 		✓____ 9pt Capture activity appearance
 * 		✓____ 16pt Player round sequencing
 * 		✓____ 20pt Move to next activity
 * 		✓____ 15pt Rotation
 *
 * 	Timer
 *
 * 		✓____ 9pt Timer activity
 * 		✓____ 24pt Graphic
 * 		✓____ 12pt Player turn end
 * 		✓____ 15pt Rotation
 *
 *
 * Please list any additional rules that may be needed to properly grade
 * your project:
 *
 *
 */