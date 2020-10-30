package com.softuni.weatherapp;

import com.softuni.weatherModel.WeatherModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;


import com.softuni.weatherModel.WeatherService;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OverallTab extends Fragment {

    TextView currentDate;
    TextView weatherType;
    TextView cloudinessPercent;
    TextView windPercent;
    TextView humidityPercent;
    TextView currentTemperature;
    TextView temperatureDifference;
    TextView detailedTypeWeather;
    TextView dateTomorrow;
    int year;
    int month;
    int day;
    double latNet;
    double lonNet;
    Calendar calendar = Calendar.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_overall_tab, container, false);
        currentDate = view.findViewById(R.id.date);
        weatherType = view.findViewById(R.id.txt_weather_type);
        cloudinessPercent = view.findViewById(R.id.txt_cloudy_percent);
        windPercent = view.findViewById(R.id.txt_wind_percent);
        humidityPercent = view.findViewById(R.id.txt_humidity_percent);
        currentTemperature = view.findViewById(R.id.txt_current_temperature);
        temperatureDifference = view.findViewById(R.id.txt_temperature_diff);
        detailedTypeWeather = view.findViewById(R.id.txt_detailed_type_weather);
        dateTomorrow = view.findViewById(R.id.tomorrow_date);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        currentDate.setText(day + "/" + (month + 1) + "/" + year);
        dateTomorrow.setText(day + 1 + "/" + (month + 1) + "/" + year);
        latNet = ((MainActivity) getActivity()).lat;
        lonNet = ((MainActivity) getActivity()).lon;
        setUpRetrofit();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void setUpRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);

        Call<WeatherModel> call = service.getCurrentWeather(latNet, lonNet, "37426f016190340c55b693d9a76e5015", "metric");
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response != null && response.isSuccessful()) {
                    WeatherModel model = response.body();

                   weatherType.setText(model.getWeather().get(0).getMain());
                    cloudinessPercent.setText(model.getClouds().getCloudiness() + "");
                    windPercent.setText(model.getWind().getSpeed() + "");
                    humidityPercent.setText(model.getMain().getHumidity() + "");
                    int currentTemp = (int)Math.rint(model.getMain().getTemperature()) ;
                    currentTemperature.setText(currentTemp + "°");
                    temperatureDifference.setText(model.getMain().getMinTemperature() + " - " + model.getMain().getMaxTemperature());
                    detailedTypeWeather.setText(model.getWeather().get(0).getDescription());
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {

            }
        });
    }
}


