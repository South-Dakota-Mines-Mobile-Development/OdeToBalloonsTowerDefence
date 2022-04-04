package edu.sdsmt.team4.odetoballonstowerdefence;

import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cloud {
    private final static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String currentGame = null;
    private GameView gameView = null;

    public Cloud() {

    }

    /**
     *
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

                // check is you are player1
                if (!player1) {
                    // if if not join load the game
                    ref.child("player2waiting").setValue(true);
                    loadFromCloud(view);
                }
                else {
                    // if so, create the game
                    ref.child("player1waiting").setValue(true);
                    currentGame = database.getReference().push().getKey();

                    ref.child("game").setValue(currentGame);

                    saveToCloud(view);
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

        gameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 26 && dataSnapshot.child("balloon25").getChildrenCount() > 2) {
                    gameView.loadJson(dataSnapshot);
                    gameView.invalidate();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(gameView.getContext(), R.string.load_failed, Toast.LENGTH_LONG).show();
            }
        });
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

    public void resetState() {
        DatabaseReference ref = database.getReference().child("state");

        ref.setValue(new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError != null) {
                    // Error condition!

                }
                ref.child("player1waiting").setValue(false);
                ref.child("player2waiting").setValue(false);
            }});
    }

    /**
     * Function to get which players are waiting.
     * Use a new CloudCallback like a listener when calling.
     *
     * @param view Used to display failed toast
     * @param cc The CloudCallback used to return the values in an asynchronous fashion
     */
    public void playersWaiting(View view, CloudCallback cc) {
        DatabaseReference ref = database.getReference().child("state");

        final State state = new State();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                state.player1Waiting = (boolean)dataSnapshot.child("player1waiting").getValue();
                state.player2Waiting = (boolean)dataSnapshot.child("player2waiting").getValue();
                currentGame = dataSnapshot.child("game").getValue().toString();
                cc.playersWaitingCallback(state.player1Waiting, state.player2Waiting);
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
}
