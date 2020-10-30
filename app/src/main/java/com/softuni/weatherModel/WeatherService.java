package com.softuni.weatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("weather")
    Call<WeatherModel> getCurrentWeather(@Query("lat") Double lat , @Query("lon") Double lon,
                                         @Query("appid") String apiKey,@Query("units") String metric);
    @GET("forecast/daily")
    Call<WeatherModelTomorrow> getTomorrowWeather(@Query("lat") Double lat , @Query("lon") Double lon,
                                                  @Query("appid") String apiKey,@Query("cnt") int countDays,
                                                  @Query("units") String metric);
}
