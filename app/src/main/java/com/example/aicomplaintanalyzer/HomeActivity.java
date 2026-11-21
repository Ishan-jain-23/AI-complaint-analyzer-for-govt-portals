package com.example.aicomplaintanalyzer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnFile = findViewById(R.id.btnFileComplaint);
        Button btnTrack = findViewById(R.id.btnTrackStatus);

        btnFile.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
            startActivity(intent);
        });

        btnTrack.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TrackActivity.class);
            startActivity(intent);
        });
    }
}