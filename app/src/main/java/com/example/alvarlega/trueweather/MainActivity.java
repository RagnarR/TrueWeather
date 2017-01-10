package com.example.alvarlega.trueweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alvarlega.trueweather.model.Forecast;
import com.example.alvarlega.trueweather.model.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.iv_weather_icon) ImageView mWeatherIcon;
    @BindView(R.id.tv_temperature) TextView mTemperature;
    @BindView(R.id.tv_weather) TextView mWeather;
    @BindView(R.id.tv_humidity) TextView mHumidity;
    @BindView(R.id.tv_pressure) TextView mPressure;
    @BindView(R.id.tv_wind) TextView mWind;
    @BindView(R.id.recycler) RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<WeatherInfo> mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Timber.tag(TAG);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDataset = new ArrayList<>();
        mAdapter = new ForecastAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        TrueWatherApp.getApi().getWeatherById("2950159").enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                Timber.d("Weather in Berlin is: %s", response.body().toString());
                Observable.just(response.body())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(weatherInfo -> {
                            mTemperature.setText(String.valueOf(weatherInfo.getWeatherMain().getTemp()) + "°C");
                            mHumidity.setText(String.valueOf(weatherInfo.getWeatherMain().getHumidity()) + "%");
                            mPressure.setText(String.valueOf(weatherInfo.getWeatherMain().getPressure()) + " hPa");
                            mWind.setText(String.valueOf(weatherInfo.getWind().getSpeed()) + " m/s");
                            String weatherString = weatherInfo.getWeather().get(0).getDescription().toUpperCase();
                            mWeather.setText(weatherString);
                        });


            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                Timber.d(t);
            }
        });

        TrueWatherApp.getApi().getForecastById("2950159").enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                mDataset.addAll(response.body().getList());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Timber.d(t);
            }
        });
    }
}
