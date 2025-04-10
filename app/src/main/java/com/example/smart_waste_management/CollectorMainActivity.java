package com.example.smart_waste_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CollectorMainActivity extends AppCompatActivity {
    private Button btnViewReports;
    private Button btnRecycleReport;
    private Button btnHotspots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_main);
        btnViewReports = findViewById(R.id.btn_view_reports);
        btnRecycleReport = findViewById(R.id.btn_recycle_report);
        btnHotspots = findViewById(R.id.btn_hotspots);
        btnViewReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectorMainActivity.this, ViewReportsActivity.class);
                startActivity(intent);
            }
        });
        btnRecycleReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectorMainActivity.this, RecycleReportActivity.class);
                startActivity(intent);
            }
        });
        btnHotspots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CollectorMainActivity.this, HotstopsActivity.class);
                startActivity(intent);
            }
        });
    }
}