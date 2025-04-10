package com.example.smart_waste_management;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class RecycleReportActivity extends AppCompatActivity {
    private ListView listView;
    private RecycledReportAdapter adapter;
    private DBHelper dbHelper;
    private List<WasteReport> wasteReports;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_report);
        listView = findViewById(R.id.list_reports);
        dbHelper = new DBHelper(this);
        wasteReports = dbHelper.getUnrecycledReports();
        if (wasteReports.isEmpty()) {
            android.util.Log.d("RecycledReportActivity", "No unrecycled reports found.");
            listView.setVisibility(View.GONE);
            Toast.makeText(this, "No List", Toast.LENGTH_SHORT).show();
            return;
        }
        adapter = new RecycledReportAdapter(this, dbHelper, R.layout.recycle_itemlayout, wasteReports);
        listView.setAdapter(adapter);
    }
}