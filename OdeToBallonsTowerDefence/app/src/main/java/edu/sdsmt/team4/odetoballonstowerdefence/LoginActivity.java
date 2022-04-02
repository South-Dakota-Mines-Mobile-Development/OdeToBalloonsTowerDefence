package edu.sdsmt.team4.odetoballonstowerdefence;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    private Login login;
    private EditText usernameText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.login = new Login();

        setContentView(R.layout.activity_login);
    }

    public void onLogin(View view) {
        this.usernameText = (EditText)findViewById(R.id.usernameInput);
        this.passwordText = (EditText)findViewById(R.id.passwordInput);

        this.login.createUser(this.usernameText.getText().toString(), this.passwordText.getText().toString());

        if (this.login.isAuthenticated()) {
            view.post(() -> Toast.makeText(view.getContext(), "User authenticated.", Toast.LENGTH_SHORT).show());

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            view.post(() -> Toast.makeText(view.getContext(), "User could not get authenticated.", Toast.LENGTH_SHORT).show());
        }
    }

}