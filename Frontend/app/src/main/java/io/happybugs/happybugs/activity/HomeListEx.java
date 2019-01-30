package io.happybugs.happybugs.activity;

import io.happybugs.happybugs.APIInterface.APIInterface;
import io.happybugs.happybugs.models.ReportData;
import io.happybugs.happybugs.models.ReportDataList;
import io.happybugs.happybugs.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeListEx {
    public static void main(String[] args){
        Retrofit rfInstance = RetrofitInstance.getInstance();
        APIInterface service = rfInstance.create(APIInterface.class);

        Call<ReportDataList> request = service.showReportList();
        request.enqueue(new Callback<ReportDataList>() {
            @Override
            public void onResponse(Call<ReportDataList> call, Response<ReportDataList> response) {
                System.out.println("=================GET report list====================");
                ReportDataList responseBody = response.body();
                System.out.println(responseBody.getReportList().size());

            }

            @Override
            public void onFailure(Call<ReportDataList> call, Throwable t) {

            }
        });
    }
}
