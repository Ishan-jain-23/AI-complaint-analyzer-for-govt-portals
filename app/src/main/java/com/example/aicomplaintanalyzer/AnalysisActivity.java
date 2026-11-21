package com.example.aicomplaintanalyzer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class AnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        String name = getIntent().getStringExtra("name");
        String complaint = getIntent().getStringExtra("complaint");

        TextView tvID = findViewById(R.id.tvComplaintID);
        TextView tvCategory = findViewById(R.id.tvCategory);
        TextView tvDept = findViewById(R.id.tvDept);
        TextView tvPriority = findViewById(R.id.tvPriority);
        TextView tvConfidence = findViewById(R.id.tvConfidence);
        TextView tvSummary = findViewById(R.id.tvSummary);
        TextView tvAction = findViewById(R.id.tvAction);
        TextView tvLocation = findViewById(R.id.tvLocation);

        String state = getIntent().getStringExtra("state");
        String city = getIntent().getStringExtra("city");
        tvLocation.setText(city + ", " + state);

        Random random = new Random();
        String id = "GRV-2026-" + (10000 + random.nextInt(90000));
        tvID.setText("ID: " + id);

        String lowerC = complaint.toLowerCase();
        String category = "General";
        String dept = "General Administration";
        String priority = "MEDIUM";

        if (lowerC.contains("bus") || lowerC.contains("road") || lowerC.contains("transport")) {
            category = "Public Transport";
            dept = "Transport Dept";
        } else if (lowerC.contains("water")) {
            category = "Water Supply";
            dept = "Water Board";
        } else if (lowerC.contains("electric") || lowerC.contains("power")) {
            category = "Electricity";
            dept = "Electricity Board";
        } else if (lowerC.contains("garbage") || lowerC.contains("sanitation") || lowerC.contains("waste")) {
            category = "Sanitation";
            dept = "Sanitation Dept";
        } else if (lowerC.contains("bribe") || lowerC.contains("corrupt")) {
            category = "Corruption";
            dept = "Vigilance Cell";
        }

        if (lowerC.contains("urgent") || lowerC.contains("emergency") || lowerC.contains("danger") || 
            lowerC.contains("accident") || lowerC.contains("fire")) {
            priority = "HIGH";
            tvPriority.setBackgroundResource(R.drawable.badge_high);
            tvPriority.setTextColor(getResources().getColor(R.color.badge_high_text));
        } else {
            tvPriority.setBackgroundResource(R.drawable.badge_medium);
            tvPriority.setTextColor(getResources().getColor(R.color.badge_medium_text));
        }
        tvPriority.setText(priority);

        int confidence = 85 + random.nextInt(13);
        tvConfidence.setText(confidence + "%");

        tvCategory.setText(category);
        tvDept.setText(dept);
        tvSummary.setText("The complaint regarding '" + category + "' has been analyzed. It mentions: " + complaint);
        tvAction.setText("Routing to Nodal Officer, " + dept + ". ETA for initial response: 24-48 hours.");

        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnEdit.setOnClickListener(v -> finish());

        String finalDept = dept;
        btnSubmit.setOnClickListener(v -> {
            Intent intent = new Intent(AnalysisActivity.this, ConfirmationActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("name", name);
            intent.putExtra("dept", finalDept);
            startActivity(intent);
        });
    }
}