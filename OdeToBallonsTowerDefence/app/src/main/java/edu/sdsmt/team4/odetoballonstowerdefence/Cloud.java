package edu.sdsmt.team4.odetoballonstowerdefence;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cloud {
    private final static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String currentGame = null;
    private boolean player1 = false;

    public Cloud(GameView view) {
        DatabaseReference ref = database.getReference().child("state");

        final State state = new State();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                state.player1Waiting = (boolean)dataSnapshot.child("player1waiting").getValue();
                state.player2Waiting = (boolean)dataSnapshot.child("player2waiting").getValue();
                currentGame = dataSnapshot.child("game").getValue().toString();

                // check if game is waiting to start
                if (state.player1Waiting) {
                    // if it is join
                    player1 = false;
                    ref.child("player2waiting").setValue(true);
                    loadFromCloud(view);
                }
                else {
                    // if not create a new game
                    player1 = true;
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

        gameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                view.loadJson(dataSnapshot);
                view.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(view.getContext(), R.string.load_failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveToCloud(GameView view) {
        DatabaseReference gameRef = database.getReference().child("games").child(currentGame);

        gameRef.setValue(currentGame, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError != null) {
                    // Error condition!
                    Toast.makeText(view.getContext(), R.string.save_failed, Toast.LENGTH_LONG).show();

                }
                view.saveJson(gameRef);
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

    private class State {
        public boolean player1Waiting = false;
        public boolean player2Waiting = false;
    }
}
