package edu.sdsmt.team4.odetoballonstowerdefence;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    private final FirebaseAuth userAuth = FirebaseAuth.getInstance();
    private final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
    private EditText usernameText;
    private EditText passwordText;
    private EditText screenNameText;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("ballons", MODE_PRIVATE);

        String screenname = sharedPreferences.getString("screenname", "Screen Name");
        String email = sharedPreferences.getString("email", "email@gmail.com");
        String password = sharedPreferences.getString("password", "");

        this.usernameText = findViewById(R.id.usernameInput);
        this.passwordText = findViewById(R.id.passwordInput);
        this.screenNameText = findViewById(R.id.screenNameInput);

        this.usernameText.setText(email);
        this.passwordText.setText(password);
        this.screenNameText.setText(screenname);

        Button signupButton = findViewById(R.id.signup);
        Button loginButton = findViewById(R.id.login);
        Button resetButton = findViewById(R.id.reset);
        Button howToPlay = findViewById(R.id.howToPlay);
        CheckBox rememberMeCheckbox = findViewById(R.id.rememberMe);


        signupButton.setOnClickListener(this::signup);
        loginButton.setOnClickListener(this::signin);
        resetButton.setOnClickListener(this::resetDatabase);
        rememberMeCheckbox.setOnClickListener(this::rememberMe);
        howToPlay.setOnClickListener(this::howToActivity);
    }

    public void resetDatabase(View view) {
        DatabaseReference gamesRef = this.rootRef.child("games");
        gamesRef.setValue(null);

        DatabaseReference stateRef = this.rootRef.child("state");
        DatabaseReference player1Ref = stateRef.child("player1waiting");
        player1Ref.setValue(false);

        DatabaseReference player2Ref = stateRef.child("player2waiting");
        player2Ref.setValue(false);

        view.post(() -> Toast.makeText(view.getContext(), "Reset Database.", Toast.LENGTH_SHORT).show());
    }

    public void rememberMe(View view) {
        CheckBox rememberMeCheckbox = findViewById(R.id.rememberMe);
        SharedPreferences sharedPreferences = getSharedPreferences("ballons", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        if (rememberMeCheckbox.isChecked()) {
            myEdit.putBoolean("checked", true);
            myEdit.putString("screenname", this.screenNameText.getText().toString());
            myEdit.putString("email", this.usernameText.getText().toString());
            myEdit.putString("password", this.passwordText.getText().toString());

            myEdit.apply();
        } else {
            myEdit.clear();
            myEdit.apply();
        }

    }

    private void signup(View view) {
        String email = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String screenName = screenNameText.getText().toString().trim();

        if (!validInput(email, password, view)) {
            return;
        }

        userAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUser = userAuth.getCurrentUser();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("/" + firebaseUser.getUid() + "/name", screenName);
                    result.put("/" + firebaseUser.getUid() + "/username", email);
                    result.put("/" + firebaseUser.getUid() + "/password", password);
                    userRef.updateChildren(result);

                    passwordText.setText("");

                    view.post(() -> Toast.makeText(view.getContext(), "Account successfully created. " +
                            "Please re-enter your password and click Login to start playing!", Toast.LENGTH_SHORT).show());
                } else {
                    passwordText.setText("");

                    view.post(() -> Toast.makeText(view.getContext(), "Could not create account. " +
                            "Please try again.", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void howToActivity(View view) {
        Intent intent = new Intent(this, HowToActivity.class);

        startActivity(intent);
    }

    private void signin(View view) {
        String email = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if (!validInput(email, password, view)) {
            return;
        }

        userAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                view.post(() -> Toast.makeText(view.getContext(), "Could not login", Toast.LENGTH_SHORT).show());
            }

        });
    }

    private boolean validInput(String email, String password, View view) {
        if (email.isEmpty()) {
            view.post(() -> Toast.makeText(view.getContext(), "Please enter a email.", Toast.LENGTH_SHORT).show());

            return false;
        }

        if (password.isEmpty()) {
            view.post(() -> Toast.makeText(view.getContext(), "Please enter a password.",
                    Toast.LENGTH_SHORT).show());

            return false;
        }

        if (password.length() < 6) {
            view.post(() -> Toast.makeText(view.getContext(), "Please enter a password with more " +
                    "then 6 characters.", Toast.LENGTH_SHORT).show());

            return false;
        }

        return true;
    }
}