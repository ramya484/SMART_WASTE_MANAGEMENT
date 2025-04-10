package com.example.smart_waste_management;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ViewReportsActivity extends AppCompatActivity {
    private ListView listView;
    private WasteReportAdapter adapter;
    private DBHelper dbHelper;
    private List<WasteReport> wasteReports;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);
        listView = findViewById(R.id.list_reports);
        dbHelper = new DBHelper(this);
        wasteReports = dbHelper.getUncollectedReports();
        if (wasteReports.isEmpty()) {
            android.util.Log.d("ViewReportsActivity", "No uncollected reports found.");
            listView.setVisibility(View.GONE);
            Toast.makeText(this, "No list", Toast.LENGTH_SHORT).show();
            return;
        }
        adapter = new WasteReportAdapter(this, dbHelper, R.layout.waste_report_item,wasteReports);
        listView.setAdapter(adapter);
    }
}