package com.example.smart_waste_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportWasteActivity extends AppCompatActivity {
    private EditText etWasteType, etWasteLocation;
    private Button btnSubmitReport;
    private DBHelper dbHelper;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_waste);
        etWasteType = findViewById(R.id.et_waste_type);
        etWasteLocation = findViewById(R.id.et_waste_location);
        btnSubmitReport = findViewById(R.id.btn_submit_report);
        dbHelper = new DBHelper(this);
        username = getIntent().getStringExtra("username");
        btnSubmitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wasteType = etWasteType.getText().toString().trim();
                String wasteLocation = etWasteLocation.getText().toString().trim();
                if (wasteType.isEmpty() || wasteLocation.isEmpty() ) {
                    Toast.makeText(ReportWasteActivity.this, "Report:Error", Toast.LENGTH_SHORT).show();
                    return;
                }
                String reportDetails = "Type: " + wasteType + "\n" +
                        "Location: " + wasteLocation + "\n" ;
                dbHelper.updateCollect(username, reportDetails, 0, 0, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                Toast.makeText(ReportWasteActivity.this, "Report Added successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReportWasteActivity.this, UserMainActivity.class);
                startActivity(intent);
            }
        });
    }
}