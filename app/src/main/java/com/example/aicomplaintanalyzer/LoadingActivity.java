package com.example.aicomplaintanalyzer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final Bundle extras = getIntent().getExtras();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(LoadingActivity.this, AnalysisActivity.class);
            if (extras != null) {
                intent.putExtras(extras);
            }
            startActivity(intent);
            finish();
        }, 2500);
    }
}