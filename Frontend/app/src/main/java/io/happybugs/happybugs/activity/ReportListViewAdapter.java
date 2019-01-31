package io.happybugs.happybugs.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import io.happybugs.happybugs.R;

public class ReportListViewAdapter extends BaseAdapter {

    private ArrayList<ReportListViewItem> reportList = new ArrayList<ReportListViewItem>();

    public ReportListViewAdapter() {}

    @Override
    public int getCount() {
        return reportList.size();
    }

    @Override
    public Object getItem(int position) {
        return reportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.report_listview_item,
                    parent, false);
        }

        ReportListViewItem reportListViewItem = reportList.get(position);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.report_title) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.report_content) ;
        descTextView.setText(reportListViewItem.getReportContent());

        Button btnEdit = (Button) convertView.findViewById(R.id.btn_report_edit);
        Button btnDelete = (Button) convertView.findViewById(R.id.btn_report_delete);

        return convertView;
    }

    public void addItem(String content) {
        ReportListViewItem item = new ReportListViewItem();
        item.setReportContent(content);

        reportList.add(item);
    }
}