package edu.sdsmt.team4.odetoballonstowerdefence;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Login {
    private static final String TAG = "monitor";
//    public final static Login INSTANCE = new Login();

//    private String USERNAME;
//    private String PASSWORD;

    private final FirebaseAuth userAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser;
    private final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");

    private boolean authenticated = false;

    public boolean isAuthenticated(){
        return authenticated;
    }

    public String getUserUid(){
        if(firebaseUser == null)
            return "";
        else
            return firebaseUser.getUid();
    }

    public void startAuthListening() {
        userAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if ( firebaseUser != null) {

                    authenticated = true;

                    Log.d(TAG, "onAuthStateChanged:signed_in:" +  firebaseUser.getUid());
                } else {
                    authenticated = false;

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        });

    }

    private void signIn(String username, String password) {
        Task<AuthResult> result = userAuth.signInWithEmailAndPassword(username, password);
        result.addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    authenticated = true;

                } else {
                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                    authenticated = false;
                }
            }
        });
    }

    public void createUser(String username, String password) {
        Task<AuthResult> result = userAuth.createUserWithEmailAndPassword(username, password);
        result.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    firebaseUser = userAuth.getCurrentUser();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("/"+firebaseUser.getUid()+"/username", username);
                    result.put("/"+firebaseUser.getUid()+"/password", password);
                    userRef.updateChildren(result);

                    // Signs in user after creating account
                    signIn(username, password);

                }else if(task.getException().getMessage().equals("The email address is already in use by another account.")){
                    // Signs in the user if the connect already exits
                    signIn(username, password);
                }else {
                    Log.d(TAG, "Problem: " + task.getException().getMessage());
                    authenticated = false;
                }
            }
        });
    }
}
