package edu.sdsmt.team6.odetoballonstowerdefence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class SelectionActivity extends AppCompatActivity {
    public static final int RECTANGLE = 0;
    public static final int CIRCLE = 1;
    public static final int LINE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Disables the top action bar
        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}

        setContentView(R.layout.activity_selection);
    }

    private void setSelectionMethod(int selectionMethod) {
        Intent intent = new Intent();
        intent.putExtra("selectionMethod", selectionMethod);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setCircle(View view) {
        setSelectionMethod(CIRCLE);
    }
    public void setRectangle(View view) {
        setSelectionMethod(RECTANGLE);
    }
    public void setLine(View view) {
        setSelectionMethod(LINE);
    }
}