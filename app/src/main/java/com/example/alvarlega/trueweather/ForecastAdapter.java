package com.example.alvarlega.trueweather;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

    private Context mContext;
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

    public ForecastAdapter(Context context, List<WeatherInfo> weatherInfos) {
        mContext = context;
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

        Double temperature = mWeatherInfos.get(position).getWeatherMain().getTemp();
        holder.mTemperature.setText(String.valueOf(temperature) + "°C");
        if (temperature >= 0) {
            holder.mTemperature.setTextColor(Color.parseColor("#81C784"));
        } else {
            holder.mTemperature.setTextColor(Color.parseColor("#E57373"));
        }
        String weatherString = mWeatherInfos.get(position).getWeather().get(0).getDescription().toUpperCase();
        holder.mWeather.setText(weatherString);
        holder.mDate.setText(mWeatherInfos.get(position).getDtTxt());
        holder.mIcon.setImageDrawable(getWeatherIcon(mWeatherInfos.get(position).getWeather().get(0).getIcon()));
    }

    @Override
    public int getItemCount() {
        return mWeatherInfos.size();
    }

    private Drawable getWeatherIcon(String iconCode){
        Drawable icon = null;
        switch (iconCode) {
            case "01d":
            case "01n":
                icon = mContext.getDrawable(R.drawable.ic_sw_01);
                break;
            case "02d":
            case "02n":
                icon = mContext.getDrawable(R.drawable.ic_sw_03);
                break;
            case "03d":
            case "03n":
                icon = mContext.getDrawable(R.drawable.ic_sw_04);
                break;
            case "04d":
            case "04n":
                icon = mContext.getDrawable(R.drawable.ic_sw_06);
                break;
            case "09d":
            case "09n":
                icon = mContext.getDrawable(R.drawable.ic_sw_21);
                break;
            case "10d":
            case "10n":
                icon = mContext.getDrawable(R.drawable.ic_sw_11);
                break;
            case "11d":
            case "11n":
                icon = mContext.getDrawable(R.drawable.ic_sw_27);
                break;
            case "13d":
            case "13n":
                icon = mContext.getDrawable(R.drawable.ic_sw_24);
                break;
            case "50d":
            case "50n":
                icon = mContext.getDrawable(R.drawable.ic_sw_10);
                break;
        }
        return icon;
    }
}
