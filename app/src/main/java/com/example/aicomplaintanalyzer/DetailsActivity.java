package com.example.aicomplaintanalyzer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private EditText etName, etState, etCity, etComplaint;
    private Spinner spinLang;
    private CheckBox cbTerms, cbAccuracy;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        etName = findViewById(R.id.etName);
        etState = findViewById(R.id.etState);
        etCity = findViewById(R.id.etCity);
        etComplaint = findViewById(R.id.etComplaint);
        spinLang = findViewById(R.id.spinLang);
        cbTerms = findViewById(R.id.cbTerms);
        cbAccuracy = findViewById(R.id.cbAccuracy);
        tvError = findViewById(R.id.tvError);

        Button btnBack = findViewById(R.id.btnBack);
        Button btnAnalyze = findViewById(R.id.btnAnalyze);
        Button btnUpload = findViewById(R.id.btnUpload);

        btnBack.setOnClickListener(v -> finish());

        btnUpload.setOnClickListener(v -> {
            Toast.makeText(this, "Upload clicked", Toast.LENGTH_SHORT).show();
        });

        btnAnalyze.setOnClickListener(v -> {
            if (validate()) {
                Intent intent = new Intent(DetailsActivity.this, LoadingActivity.class);
                intent.putExtra("name", etName.getText().toString());
                intent.putExtra("lang", spinLang.getSelectedItem().toString());
                intent.putExtra("state", etState.getText().toString());
                intent.putExtra("city", etCity.getText().toString());
                intent.putExtra("complaint", etComplaint.getText().toString());
                startActivity(intent);
            }
        });
    }

    private boolean validate() {
        if (etName.getText().toString().isEmpty() ||
            etState.getText().toString().isEmpty() ||
            etCity.getText().toString().isEmpty() ||
            etComplaint.getText().toString().isEmpty()) {
            tvError.setText("Please fill all fields.");
            tvError.setVisibility(View.VISIBLE);
            return false;
        }
        if (!cbTerms.isChecked() || !cbAccuracy.isChecked()) {
            tvError.setText("Please accept the terms and certify accuracy.");
            tvError.setVisibility(View.VISIBLE);
            return false;
        }
        tvError.setVisibility(View.GONE);
        return true;
    }
}