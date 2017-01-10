package com.example.alvarlega.trueweather;

import com.example.alvarlega.trueweather.model.Forecast;
import com.example.alvarlega.trueweather.model.WeatherInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    @GET("weather")
    Call<WeatherInfo> getWeatherById(@Query("id") String cityId);

    @GET("forecast")
    Call<Forecast> getForecastById(@Query("id") String cityId);

    @GET("weather")
    Call<WeatherInfo> getWeatherByLocation(@Query("lat") long latitude, @Query("lon") long longitude);

    @GET("forecast")
    Call<Forecast> getForecastByLocation(@Query("lat") long latitude, @Query("lon") long longitude);

}
