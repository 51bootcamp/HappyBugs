package io.happybugs.happybugs.APIInterface;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import io.happybugs.happybugs.model.UserReportList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("api/v1/user/signin/")
    Call<ResponseBody> signin(@Query("email") String email, @Query("password") String password);

    @POST("api/v1/user/signup/")
    Call<ResponseBody> signup(@Body JSONObject userData);

    // Create report
    @POST("api/v1/report/create")
    Call<ResponseBody> createReport(@Body JSONObject reportData);

    @GET("api/v1/report/list")
    Call<UserReportList> getReportList();
}
