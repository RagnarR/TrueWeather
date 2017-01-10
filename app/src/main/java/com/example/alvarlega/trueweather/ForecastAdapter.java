package com.example.alvarlega.trueweather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.alvarlega.trueweather.model.WeatherInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<WeatherInfo> mWeatherInfos;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_temperature) TextView mTemperature;
        @BindView(R.id.tv_weather) TextView mWeather;
        @BindView(R.id.tv_date) TextView mDate;
        @BindView(R.id.iv_weather_icon) ImageView mIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public ForecastAdapter(List<WeatherInfo> weatherInfos) {
        mWeatherInfos = weatherInfos;
    }

    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ForecastAdapter.ViewHolder holder, int position) {
        holder.mTemperature.setText(String.valueOf(mWeatherInfos.get(position).getWeatherMain().getTemp()) + "°C");
        String weatherString = mWeatherInfos.get(position).getWeather().get(0).getDescription().toUpperCase();
        holder.mWeather.setText(weatherString);
        holder.mDate.setText(mWeatherInfos.get(position).getDtTxt());
    }

    @Override
    public int getItemCount() {
        return mWeatherInfos.size();
    }
}
