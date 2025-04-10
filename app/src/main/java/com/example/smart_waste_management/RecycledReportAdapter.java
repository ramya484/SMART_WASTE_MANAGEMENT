package com.example.smart_waste_management;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class RecycledReportAdapter extends ArrayAdapter<WasteReport> {

    private Context context;
    private DBHelper dbHelper;
    private List<WasteReport> recycledReports;
    private int layoutResourceId;

    public RecycledReportAdapter(Context context, DBHelper dbHelper, int resource,List<WasteReport> recycledReports) {
        super(context, resource,recycledReports);
        this.context = context;
        this.dbHelper = dbHelper;
        this.layoutResourceId = resource;
        this.recycledReports = recycledReports;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(layoutResourceId, parent, false);
        }
        WasteReport report = getItem(position);

        TextView textViewDetails = view.findViewById(R.id.text_view_details);
        Button btnRecycle = view.findViewById(R.id.btn_recycle);

        textViewDetails.setText(report.getReportDetails());
        btnRecycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addWasteReport(report.getReportId(), report.getReportDetails(), 1, 1, null);
                recycledReports.remove(report);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
