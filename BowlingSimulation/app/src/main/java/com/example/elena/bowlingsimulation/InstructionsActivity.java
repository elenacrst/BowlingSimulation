package com.example.elena.bowlingsimulation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }

    public void onLearnMoreClick(View view) {
        String urlString = "https://en.wikipedia.org/wiki/Ten-pin_bowling";
        Uri webpage = Uri.parse(urlString);
        Intent showWebpageIntent = new Intent(Intent.ACTION_VIEW, webpage);
        if(showWebpageIntent.resolveActivity(getPackageManager()) != null){
            startActivity(showWebpageIntent);
        }else {
            Toast.makeText(this,"Error: Can't open webpage.\nThis device does not have a web browser.",
                    Toast.LENGTH_LONG).show();
        }
    }
}
