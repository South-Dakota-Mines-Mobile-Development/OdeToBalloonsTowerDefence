package edu.sdsmt.team6.odetoballonstowerdefence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HowToActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Disables the top action bar
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_how_to);
    }

    public void backToStart(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}