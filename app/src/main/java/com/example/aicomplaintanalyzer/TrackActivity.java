package com.example.aicomplaintanalyzer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TrackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        EditText etID = findViewById(R.id.etTrackID);
        Button btnTrack = findViewById(R.id.btnTrackAction);
        Button btnHome = findViewById(R.id.btnHome);

        String prefillID = getIntent().getStringExtra("id");
        if (prefillID != null) {
            etID.setText(prefillID);
        }

        btnTrack.setOnClickListener(v -> {
            String id = etID.getText().toString();
            if (id.isEmpty()) {
                Toast.makeText(this, "Please enter a Complaint ID", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Tracking ID: " + id + ". This demo does not store complaints between visits.", Toast.LENGTH_LONG).show();
            }
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(TrackActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}