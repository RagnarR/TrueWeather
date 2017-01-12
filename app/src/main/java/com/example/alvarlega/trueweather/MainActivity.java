package com.example.alvarlega.trueweather;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
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
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mDataset = new ArrayList<>();
        mAdapter = new ForecastAdapter(this, mDataset);
        mRecyclerView.setAdapter(mAdapter);

        TrueWatherApp.getApi().getWeatherById("2950159").enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                Timber.d("Weather in Berlin is: %s", response.body().toString());
                Observable.just(response.body())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(weatherInfo -> {
                            Double temperature = weatherInfo.getWeatherMain().getTemp();
                            mTemperature.setText(String.valueOf(temperature) + "°C");
                            if (temperature >= 0) {
                                mTemperature.setTextColor(Color.parseColor("#4CAF50"));
                            } else {
                                mTemperature.setTextColor(Color.parseColor("#F44336"));
                            }
                            mHumidity.setText(String.valueOf(weatherInfo.getWeatherMain().getHumidity()) + "%");
                            mPressure.setText(String.valueOf(weatherInfo.getWeatherMain().getPressure()) + " hPa");
                            mWind.setText(String.valueOf(weatherInfo.getWind().getSpeed()) + " m/s");
                            String weatherString = weatherInfo.getWeather().get(0).getDescription().toUpperCase();
                            mWeather.setText(weatherString);
                            mWeatherIcon.setImageDrawable(getWeatherIcon(weatherInfo.getWeather().get(0).getIcon()));
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

    private Drawable getWeatherIcon(String iconCode){
        Drawable icon = null;
        switch (iconCode) {
            case "01d":
            case "01n":
                icon = getDrawable(R.drawable.ic_sw_01);
                break;
            case "02d":
            case "02n":
                icon = getDrawable(R.drawable.ic_sw_03);
                break;
            case "03d":
            case "03n":
                icon = getDrawable(R.drawable.ic_sw_04);
                break;
            case "04d":
            case "04n":
                icon = getDrawable(R.drawable.ic_sw_06);
                break;
            case "09d":
            case "09n":
                icon = getDrawable(R.drawable.ic_sw_21);
                break;
            case "10d":
            case "10n":
                icon = getDrawable(R.drawable.ic_sw_11);
                break;
            case "11d":
            case "11n":
                icon = getDrawable(R.drawable.ic_sw_27);
                break;
            case "13d":
            case "13n":
                icon = getDrawable(R.drawable.ic_sw_24);
                break;
            case "50d":
            case "50n":
                icon = getDrawable(R.drawable.ic_sw_10);
                break;
        }
        return icon;
    }
}
