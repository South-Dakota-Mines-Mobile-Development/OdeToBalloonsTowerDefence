package edu.sdsmt.team4.odetoballonstowerdefence;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    private EditText usernameText;
    private EditText passwordText;
    private EditText screenNameText;
    private final FirebaseAuth userAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser;
    private final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("ballons", MODE_PRIVATE);

        String screenname = sharedPreferences.getString("screenname", "Screen Name");
        String email = sharedPreferences.getString("email", "email@gmail.com");
        String password = sharedPreferences.getString("password", "");
        Boolean checkboxChecked = sharedPreferences.getBoolean("checked", false);

        this.usernameText = findViewById(R.id.usernameInput);
        this.passwordText = findViewById(R.id.passwordInput);
        this.screenNameText = findViewById(R.id.screenNameInput);

        this.usernameText.setText(email);
        this.passwordText.setText(password);
        this.screenNameText.setText(screenname);

        Button signupButton = findViewById(R.id.signup);
        Button loginButton = findViewById(R.id.login);
        Button resetButton = findViewById(R.id.reset);
        CheckBox rememberMeCheckbox = findViewById(R.id.rememberMe);
        rememberMeCheckbox.setChecked(checkboxChecked);

        signupButton.setOnClickListener(this::signup);
        loginButton.setOnClickListener(this::signin);
        resetButton.setOnClickListener(this::resetDatabase);
        rememberMeCheckbox.setOnClickListener(this::rememberMe);
    }

    public void resetDatabase(View view) {
        this.rootRef.setValue(null);
    }

    public void rememberMe(View view) {
        CheckBox rememberMeCheckbox = findViewById(R.id.rememberMe);
        SharedPreferences sharedPreferences = getSharedPreferences("ballons",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        if (rememberMeCheckbox.isChecked()) {
            myEdit.putBoolean("checked", true);
            myEdit.putString("screenname", this.screenNameText.getText().toString());
            myEdit.putString("email", this.usernameText.getText().toString());
            myEdit.putString("password", this.passwordText.getText().toString());

            myEdit.commit();
        } else {
            myEdit.clear();
            myEdit.commit();
        }

    }

    private void signup(View view) {
        String email = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String screenName = screenNameText.getText().toString().trim();

        if (!validInput(email, password, view)) {
            return;
        }

        userAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    firebaseUser = userAuth.getCurrentUser();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("/"+firebaseUser.getUid()+"/name", screenName);
                    result.put("/"+firebaseUser.getUid()+"/username", email);
                    result.put("/"+firebaseUser.getUid()+"/password", password);
                    userRef.updateChildren(result);

                    view.post(() -> Toast.makeText(view.getContext(), "Account successfully created.", Toast.LENGTH_SHORT).show());
                }
                else
                {
                    view.post(() -> Toast.makeText(view.getContext(), "Could not create account. Please try again.", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void signin(View view) {
        String email = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if (!validInput(email, password, view)) {
            return;
        }

        userAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                startActivity(new Intent(this, MainActivity.class));
            }
            else
            {
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
            view.post(() -> Toast.makeText(view.getContext(), "Please enter a password.", Toast.LENGTH_SHORT).show());

            return false;
        }

        if (password.length() < 6) {
            view.post(() -> Toast.makeText(view.getContext(), "Please enter a password with more then 6 characters.", Toast.LENGTH_SHORT).show());

            return false;
        }

        return true;
    }
}