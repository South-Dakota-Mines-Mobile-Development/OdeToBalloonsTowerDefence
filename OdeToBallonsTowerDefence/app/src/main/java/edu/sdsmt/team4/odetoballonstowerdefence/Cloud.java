package edu.sdsmt.team4.odetoballonstowerdefence;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cloud {
    private final static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String currentGame = null;
    private GameView gameView = null;
    private ValueEventListener stateEventListener = null;
    private ValueEventListener gameEventListener = null;
    private ValueEventListener turnEventListener = null;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public Cloud() {

    }

    /**
     * Initially upload the balloons and players if player 1 or sync if player 2.
     *
     * @param view GameView to save.
     */
    public void init(GameView view, boolean player1) {
        DatabaseReference ref = database.getReference().child("state");

        final State state = new State();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentGame = dataSnapshot.child("game").getValue().toString();
                //DataSnapshot state = dataSnapshot.child("state");

                String p1Name = dataSnapshot.child("player1name").getValue().toString();
                String p2Name = dataSnapshot.child("player2name").getValue().toString();

                // check is you are player1
                if (auth.getCurrentUser().getEmail().equals(p2Name)) {
                    // if not join load the game
                    loadFromCloud(view);
                }
                else {
                    // if so, create the game
                    currentGame = database.getReference().push().getKey();

                    ref.child("game").setValue(currentGame);

                    saveToCloud(view);

                    loadFromCloud(view);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //check for errors
                Toast.makeText(view.getContext(), R.string.state_failed, Toast.LENGTH_SHORT);
            }
        });
    }

    public void loadFromCloud(GameView view) {

        DatabaseReference gameRef = database.getReference().child("games").child(currentGame);

        gameView = view;

        gameEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() > 27 && dataSnapshot.child("gamemodel").getChildrenCount() > 2) {
                    gameView.loadJson(dataSnapshot);
                    gameView.invalidate();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(gameView.getContext(), R.string.load_failed, Toast.LENGTH_LONG).show();
            }
        };

        gameRef.addValueEventListener(gameEventListener);
    }

    public void saveToCloud(GameView view) {
        DatabaseReference gameRef = database.getReference().child("games").child(currentGame);

        gameView = view;

        gameRef.setValue(currentGame, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError != null) {
                    // Error condition!
                    Toast.makeText(gameView.getContext(), R.string.save_failed, Toast.LENGTH_LONG).show();

                }
                gameView.saveJson(gameRef);
            }});
    }

    /**
     * Resets the game state in the database (probably called at the end of the game).
     */
    public void resetState() {

        DatabaseReference ref = database.getReference().child("state");
        DatabaseReference gameRef = database.getReference().child("games").child(currentGame);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ref.child("player1waiting").setValue(false);
                ref.child("player2waiting").setValue(false);
                if (stateEventListener != null)
                    ref.removeEventListener(stateEventListener);
                if (gameEventListener != null)
                    gameRef.removeEventListener(gameEventListener);
                if (turnEventListener != null)
                    ref.removeEventListener(turnEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Function to get which players are waiting.
     * Use a new CloudCallback like a listener when calling.
     * The callback will be called anytime the state child is changed in the database
     *
     * @param view Used to display failed toast
     * @param cc The CloudCallback used to return the values in an asynchronous fashion
     */
    public void playersWaiting(View view, CloudCallback cc) {
        DatabaseReference ref = database.getReference().child("state");

        final State state = new State();

        stateEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                state.player1Waiting = (boolean)dataSnapshot.child("player1waiting").getValue();
                state.player2Waiting = (boolean)dataSnapshot.child("player2waiting").getValue();
                //game might be unnecessary
                currentGame = dataSnapshot.child("game").getValue().toString();

                if (state.player1Waiting) {
                    ref.child("player1waiting").setValue(true);
                    //ref.child("player1name").setValue()
                }
                else {
                    ref.child("player2waiting").setValue(true);
                }
                cc.playersWaitingCallback(state.player1Waiting, state.player2Waiting);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //check for errors
                Toast.makeText(view.getContext(), R.string.failed_state, Toast.LENGTH_LONG).show();
            }
        };

        ref.addValueEventListener(stateEventListener);
    }

    public void turn(GameView view, CloudCallback cc) {
        DatabaseReference ref = database.getReference().child("state");

        final State state = new State();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String p1Name = dataSnapshot.child("player1name").getValue().toString();
                String p2Name = dataSnapshot.child("player2name").getValue().toString();

                if (auth.getCurrentUser().getEmail().equals(p1Name)) {
                    cc.turnCallback(true);
                }
                else {
                    cc.turnCallback(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //check for errors
                Toast.makeText(view.getContext(), R.string.failed_state, Toast.LENGTH_LONG).show();
            }
        });
    }

    private class State {
        public boolean player1Waiting = false;
        public boolean player2Waiting = false;
    }
}

// Callback interface used for getting which players are waiting.
interface CloudCallback {
    public void playersWaitingCallback(boolean p1Waiting, boolean p2Waiting);
    public void turnCallback(boolean isPlayer1);
}
