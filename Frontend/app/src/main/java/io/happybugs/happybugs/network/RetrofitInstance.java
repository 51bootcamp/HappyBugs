package io.happybugs.happybugs.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit rfInstance;
    private static OkHttpClient client;
    private static final String BASE_URL = "http://10.0.2.2:3000/";

    public static Retrofit getInstance() {
        if (rfInstance == null) {
            rfInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkhttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return rfInstance;
    }

    private static OkHttpClient getOkhttpClient() {
        if (client == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor).build();
        }
        return client;
    }
}