package edu.sdsmt.team6.odetoballonstowerdefence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class HowToActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);

        //Disables the top action bar
        Objects.requireNonNull(this.getSupportActionBar()).hide();

        setContentView(R.layout.activity_how_to);
    }

    public void backToStart(View view) {
        Intent intent = new Intent();

        setResult(RESULT_OK);
        finish();
    }
}