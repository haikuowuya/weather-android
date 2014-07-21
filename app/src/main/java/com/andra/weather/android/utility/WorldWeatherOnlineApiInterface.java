package com.andra.weather.android.utility;

import com.andra.weather.android.pojo.WeatherForecast;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface WorldWeatherOnlineApiInterface {
    @GET("/weather.ashx")
    void getForecast( @Query("q") String latlon,
                                 @Query("format") String format,
                                 @Query("num_of_days") int days,
                                 @Query("includelocation") String includeLocation,
                                 @Query("key") String apiKey,
                                 @Query("cc") String cc,
                                 @Query("mca") String mca,
                                 @Query("tp") int tp,
                                 Callback<WeatherForecast> callback);
}
