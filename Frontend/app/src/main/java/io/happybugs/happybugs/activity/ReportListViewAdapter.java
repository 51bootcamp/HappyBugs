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
import io.happybugs.happybugs.network.RetrofitInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportListViewAdapter extends BaseAdapter {

    private Context currContext;
    private ArrayList<ReportListViewItem> reportList = new ArrayList<ReportListViewItem>();

    public ReportListViewAdapter() { }

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

        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView_report_title);
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView_report_content);
        TextView textViewCreatedAt = (TextView) convertView.findViewById(R.id.textView_report_created_date);

        descTextView.setText(reportListViewItem.getReportContent());
        //TODO(Jelldo): Change createdAt by date
        //textViewCreatedAt.setText(reportListViewItem.getCreatedAt());

        Button btnEditReport = (Button) convertView.findViewById(R.id.button_edit_report);
        Button btnDeleteReport = (Button) convertView.findViewById(R.id.button_delete_report);
        Button btnReportingUserCount = (Button) convertView.findViewById(R.id.button_perpetrator_match);

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

        btnEditReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendReportID = new Intent(v.getContext(), ReportActivity.class);
                sendReportID.putExtra("isFromBtnEditReport", true);
                sendReportID.putExtra("reportID", reportListViewItem.getReportId());
                v.getContext().startActivity(sendReportID);
            }
        });

        if (!reportListViewItem.getReportTitle().equals("")) {
            titleTextView.setText(reportListViewItem.getReportTitle());
        } else {
            String createdAt = reportListViewItem.getCreatedAt();
            titleTextView.setText("Report");
        }
        btnReportingUserCount.setText(Integer.toString(reportListViewItem.getReportUserCount() - 1));
        return convertView;
    }

    public void addItem(String title, String content, int reportId, int reportingUserCount, String createdAt) {
        ReportListViewItem item = new ReportListViewItem();
        item.setReportTitle(title);
        item.setReportContent(content);
        item.setReportId(reportId);
        item.setReportUserCount(reportingUserCount);
        item.setCreatedAt(createdAt);

        reportList.add(item);
    }

    public void addAll(List<UserReportItem> userReportList) {
        for (UserReportItem item : userReportList) {
            addItem(item.getTime(), item.getWhat(), item.getId(), item.getPerpetrator().getReportingUserCount(), item.getCreatedAt());
        }
    }
}