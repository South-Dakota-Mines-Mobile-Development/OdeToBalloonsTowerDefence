package edu.sdsmt.team4.odetoballonstowerdefence;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private EditText usernameText;
    private EditText passwordText;
    private final FirebaseAuth userAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser;
    private final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.usernameText = findViewById(R.id.usernameInput);
        this.passwordText = findViewById(R.id.passwordInput);

        Button signupButton = findViewById(R.id.signup);
        Button loginButton = findViewById(R.id.login);

        signupButton.setOnClickListener(this::signup);
        loginButton.setOnClickListener(this::signin);
    }

    private void signup(View view) {
        String email = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

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