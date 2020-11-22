package com.softuni.cmn;

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
    @GET("forecast")
    Call<WeatherDetailedModel> getDetailedWeather(@Query("lat") Double lat , @Query("lon") Double lon,
                                                  @Query("appid") String apiKey,@Query("cnt") int countHours,
                                                  @Query("units") String metric);
    @GET("weather")
    Call<WeatherModel> getCurrentWeatherByCity(@Query("q") String city ,
                                         @Query("appid") String apiKey,@Query("units") String metric);
    @GET("forecast/daily")
    Call<WeatherModelTomorrow> getTomorrowWeatherByCity(@Query("q") String city ,
                                                  @Query("appid") String apiKey,@Query("cnt") int countDays,
                                                  @Query("units") String metric);
    @GET("forecast")
    Call<WeatherDetailedModel> getDetailedWeatherByCity(@Query("q") String city ,
                                                  @Query("appid") String apiKey,@Query("cnt") int countHours,
                                                  @Query("units") String metric);

}
