package com.example.smart_waste_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class WasteReportAdapter extends ArrayAdapter<WasteReport> {

    private Context context;
    private DBHelper dbHelper;
    private List<WasteReport> wasteReports;
    private int layoutResourceId;

    public WasteReportAdapter(Context context, DBHelper dbHelper, int resource, List<WasteReport> wasteReports) {
        super(context, resource, wasteReports);
        this.context = context;
        this.dbHelper = dbHelper;
        this.layoutResourceId = resource;
        this.wasteReports = wasteReports;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(layoutResourceId, parent, false);
        }
        WasteReport report = getItem(position);

        TextView textViewDetails = view.findViewById(R.id.text_view_details);
        Button btnCollect = view.findViewById(R.id.btn_collect);

        textViewDetails.setText(report.getReportDetails());
        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addWasteReport(report.getReportId(), report.getReportDetails(), 1, 0, null);
                wasteReports.remove(report);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}