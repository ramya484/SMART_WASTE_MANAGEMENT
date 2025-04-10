package com.example.smart_waste_management;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class UserMainActivity extends AppCompatActivity {

    private Button btnReportWaste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        btnReportWaste = findViewById(R.id.btn_report_waste);

        btnReportWaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, ReportWasteActivity.class);
                startActivity(intent);
            }
        });
    }
}