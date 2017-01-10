package com.example.alvarlega.trueweather;

import android.app.Application;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;


public class TrueWatherApp extends Application {

    private static String TAG = TrueWatherApp.class.getSimpleName();

    public static final String API_KEY = "49edbd385e83069d98f249ee3511d91b";

    private static OpenWeatherApi openWeatherApi;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        Timber.tag(TAG);

        // OkHTTP Interceptors
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("appid", API_KEY)
                    .addQueryParameter("units", "metric")
                    .addQueryParameter("lang", "ru")
                    .build();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        // Retrofit 2 configuration
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        openWeatherApi = retrofit.create(OpenWeatherApi.class);
    }

    public static String getApiKey() {
        Timber.d("Return API key => %s", API_KEY);
        return API_KEY;
    }

    public static OpenWeatherApi getApi() {
        return openWeatherApi;
    }
}
