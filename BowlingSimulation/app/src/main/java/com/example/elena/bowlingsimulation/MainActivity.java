package com.example.elena.bowlingsimulation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onPlayClick(View view) {
        Intent startPlayIntent = new Intent(this, PlayActivity.class);
        startActivity(startPlayIntent);
    }

    public void onInstructionClick(View view) {
        Intent startInstructionsIntent = new Intent(this, InstructionsActivity.class);
        startActivity(startInstructionsIntent);
    }
}
