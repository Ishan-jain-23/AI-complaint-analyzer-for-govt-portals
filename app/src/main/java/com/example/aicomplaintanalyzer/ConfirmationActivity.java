package com.example.aicomplaintanalyzer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String dept = getIntent().getStringExtra("dept");

        TextView tvID = findViewById(R.id.tvConfID);
        TextView tvName = findViewById(R.id.tvConfName);
        TextView tvDept = findViewById(R.id.tvConfDept);
        TextView tvDate = findViewById(R.id.tvConfDate);

        tvID.setText("ID: " + id);
        tvName.setText("Name: " + name);
        tvDept.setText("Dept: " + dept);
        
        String currentDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        tvDate.setText("Date: " + currentDate);

        Button btnTrack = findViewById(R.id.btnTrack);
        Button btnFileAnother = findViewById(R.id.btnFileAnother);

        btnTrack.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmationActivity.this, TrackActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });

        btnFileAnother.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmationActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}