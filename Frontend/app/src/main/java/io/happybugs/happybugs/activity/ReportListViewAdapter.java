package io.happybugs.happybugs.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.happybugs.happybugs.APIInterface.APIInterface;
import io.happybugs.happybugs.R;
import io.happybugs.happybugs.model.UserReportItem;
import io.happybugs.happybugs.model.UserReportList;
import io.happybugs.happybugs.network.RetrofitInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportListViewAdapter extends BaseAdapter {

    private ArrayList<ReportListViewItem> reportList = new ArrayList<ReportListViewItem>();

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

        final ReportListViewItem reportListViewItem = reportList.get(position);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.report_title) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.report_content) ;
        descTextView.setText(reportListViewItem.getReportContent());

        Button btnEditReport = (Button) convertView.findViewById(R.id.btn_report_edit);
        Button btnDeleteReport = (Button) convertView.findViewById(R.id.btn_report_delete);

        btnDeleteReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit rfInstance = RetrofitInstance.getInstance(v.getContext());
                APIInterface service = rfInstance.create(APIInterface.class);

                Call<ResponseBody> requestDeleteReport = service.deleteReport(
                        reportListViewItem.getReportId());

                requestDeleteReport.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 204) {
                            reportList.remove(pos);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Failed to delete", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        return convertView;
    }

    public void addItem(String content, int reportId) {
        ReportListViewItem item = new ReportListViewItem();
        item.setReportContent(content);
        item.setReportId(reportId);

        reportList.add(item);
    }

    public void addAll(List<UserReportItem> userReportList) {
        for (UserReportItem item : userReportList) {
            addItem(item.getWhat(), item.getId());
        }
    }
}