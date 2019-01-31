package io.happybugs.happybugs.network;

import android.content.Context;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit rfInstance;
    private static OkHttpClient client;
    private static final String BASE_URL = "http://52.8.70.153:80/";

    public static Retrofit getInstance(Context context) {
        if (rfInstance == null) {
            rfInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkhttpClient(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return rfInstance;
    }

    private static OkHttpClient getOkhttpClient(Context context) {
        CookieJar cookieJar = new PersistentCookieJar(
                        new SetCookieCache(),
                        new SharedPrefsCookiePersistor(context));

        if (client == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .addInterceptor(interceptor).build();
        }
        return client;
    }
}