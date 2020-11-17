package com.softuni.weatherapp;

import com.softuni.weatherModel.WeatherModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.softuni.weatherModel.WeatherModelTomorrow;
import com.softuni.weatherModel.WeatherService;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OverallTab extends Fragment {

    TextView currentDate;
    TextView weatherType;
    TextView cloudinessPercent;
    TextView windPercent;
    TextView humidityPercent;
    TextView currentTemperature;
    TextView temperatureDifference;
    TextView detailedTypeWeather;
    ImageView weatherImageToday;
    CardView cardViewToday;
    TextView dateTomorrow;
    TextView weatherTypeTomorrow;
    TextView cloudinessPercentTomorrow;
    TextView windPercentTomorrow;
    TextView humidityPercentTomorrow;
    TextView currentTemperatureTomorrow;
    TextView temperatureDifferenceTomorrow;
    TextView detailedTypeWeatherTomorrow;
    ImageView weatherImageTomorrow;
    CardView cardViewTomorrow;
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
        weatherImageToday = view.findViewById(R.id.img_weather_type);
        cardViewToday = view.findViewById(R.id.card_view_today);
        dateTomorrow = view.findViewById(R.id.tomorrow_date);
        weatherTypeTomorrow = view.findViewById(R.id.txt_tomorrow_weather_type);
        cloudinessPercentTomorrow = view.findViewById(R.id.txt_tomorrow_cloudy_percent);
        windPercentTomorrow = view.findViewById(R.id.txt_tomorrow_wind_percent);
        humidityPercentTomorrow = view.findViewById(R.id.txt_tomorrow_humidity_percent);
        currentTemperatureTomorrow = view.findViewById(R.id.txt_tomorrow_temperature);
        temperatureDifferenceTomorrow = view.findViewById(R.id.txt_tomorrow_temperature_diff);
        detailedTypeWeatherTomorrow = view.findViewById(R.id.txt_tomorrow_detailed_type_weather);
        weatherImageTomorrow = view.findViewById(R.id.img_tomorrow_weather_type);
        cardViewTomorrow = view.findViewById(R.id.card_view_tomorrow);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        currentDate.setText(day + "/" + (month + 1) + "/" + year);
        dateTomorrow.setText(day + 1 + "/" + (month + 1) + "/" + year);
        latNet = ((MainActivity) getActivity()).lat;
        lonNet = ((MainActivity) getActivity()).lon;
        if ((latNet == 0.0) || (lonNet == 0.0)) {
            getCurrentCityWeather(null, ((MainActivity) getActivity()).getCity());
            getTomorrowCityWeather(null, ((MainActivity) getActivity()).getCity());
        } else {
            refresh();
        }


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getCurrentCityWeather(null, ((MainActivity) getActivity()).getCity());
        getTomorrowCityWeather(null, ((MainActivity) getActivity()).getCity());
    }


    private void getCurrentWeatherFromApi(final MainActivity.MainCallback mainCallback) {

        WeatherService service = ((MainActivity) getActivity()).getWeatherService();

        Call<WeatherModel> call = service.getCurrentWeather(latNet, lonNet,
                "09a8a590d1b034cf0cd50777f7e675fd", "metric");
        call.enqueue(new Callback<WeatherModel>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {

                if (getActivity() != null && isAdded()) {
                    if (mainCallback != null) {
                        mainCallback.onFinished();
                    }
                }

                if (response != null && response.isSuccessful()) {
                    WeatherModel model = response.body();

                    String currentWeather = model.getWeather().get(0).getMain();
                    weatherType.setText(currentWeather);
                    cloudinessPercent.setText((int) Math.rint(model.getClouds().getCloudiness()) + " %");
                    windPercent.setText((int) Math.rint(model.getWind().getSpeed()) + " m/s");
                    humidityPercent.setText((int) Math.rint(model.getMain().getHumidity()) + " %");
                    int currentTemp = (int) Math.rint(model.getMain().getTemperature());
                    currentTemperature.setText(currentTemp + "°");
                    temperatureDifference.setText((int) Math.rint(model.getMain().getMinTemperature())
                            + "° - " + (int) Math.rint(model.getMain().getMaxTemperature()) + "°");
                    detailedTypeWeather.setText(model.getWeather().get(0).getDescription());

                    ConvertUtil.setTemperatureBackgroundColor(currentTemp, cardViewToday);
                    ConvertUtil.setImageWeather(currentWeather, weatherImageToday);

                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {

                if (getActivity() != null && isAdded()) {
                    if (mainCallback != null) {
                        mainCallback.onFinished();
                    }
                }
            }
        });
    }

    private void getTomorrowWeatherFromApi(MainActivity.MainCallback mainCallback) {

        WeatherService service = ((MainActivity) getActivity()).getWeatherService();

        Call<WeatherModelTomorrow> callTomorrowWeather = service.getTomorrowWeather(latNet, lonNet,
                "37426f016190340c55b693d9a76e5015", 1, "metric");
        callTomorrowWeather.enqueue(new Callback<WeatherModelTomorrow>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<WeatherModelTomorrow> call, Response<WeatherModelTomorrow> response) {
                if (response != null && response.isSuccessful()) {
                    WeatherModelTomorrow model = response.body();
                    String weather = model.getData().get(0).getWeatherTomorrow().get(0).getMain();

                    weatherTypeTomorrow.setText(weather);
                    cloudinessPercentTomorrow.setText((int) Math.rint(model.getData().get(0).getClouds()) + " %");
                    windPercentTomorrow.setText((int) Math.rint(model.getData().get(0).getSpeed()) + " m/s");
                    humidityPercentTomorrow.setText((int) Math.rint(model.getData().get(0).getHumidity()) + " %");
                    int tomorrowTemp = (int) Math.rint(model.getData().get(0).getTemp().getDay());
                    currentTemperatureTomorrow.setText(tomorrowTemp + "°");
                    temperatureDifferenceTomorrow.setText((int) Math.rint(model.getData().get(0).getTemp().getMin())
                            + "° - " + (int) Math.rint(model.getData().get(0).getTemp().getMax()) + "°");
                    detailedTypeWeatherTomorrow.setText(model.getData().get(0).getWeatherTomorrow().get(0).getDescription());

                    ConvertUtil.setTemperatureBackgroundColor(tomorrowTemp, cardViewTomorrow);
                    ConvertUtil.setImageWeather(weather, weatherImageTomorrow);

                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherModelTomorrow> call, Throwable t) {

            }
        });
    }

    public void getCurrentCityWeather(final MainActivity.MainCallback mainCallback, String city) {
        WeatherService service = ((MainActivity) getActivity()).getWeatherService();

        Call<WeatherModel> call = service.getCurrentWeatherByCity(city, "37426f016190340c55b693d9a76e5015", "metric");
        call.enqueue(new Callback<WeatherModel>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {

                if (getActivity() != null && isAdded()) {
                    if (mainCallback != null) {
                        mainCallback.onFinished();
                    }
                }

                if (response != null && response.isSuccessful()) {
                    WeatherModel model = response.body();

                    String currentWeather = model.getWeather().get(0).getMain();
                    weatherType.setText(currentWeather);
                    cloudinessPercent.setText((int) Math.rint(model.getClouds().getCloudiness()) + " %");
                    windPercent.setText((int) Math.rint(model.getWind().getSpeed()) + " m/s");
                    humidityPercent.setText((int) Math.rint(model.getMain().getHumidity()) + " %");
                    int currentTemp = (int) Math.rint(model.getMain().getTemperature());
                    currentTemperature.setText(currentTemp + "°");
                    temperatureDifference.setText((int) Math.rint(model.getMain().getMinTemperature())
                            + "° - " + (int) Math.rint(model.getMain().getMaxTemperature()) + "°");
                    detailedTypeWeather.setText(model.getWeather().get(0).getDescription());

                    ConvertUtil.setTemperatureBackgroundColor(currentTemp, cardViewToday);
                    ConvertUtil.setImageWeather(currentWeather, weatherImageToday);

                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {

            }
        });
    }

    public void getTomorrowCityWeather(final MainActivity.MainCallback mainCallback, String city) {
        WeatherService service = ((MainActivity) getActivity()).getWeatherService();

        Call<WeatherModelTomorrow> callTomorrowWeather = service.getTomorrowWeatherByCity(city,
                "37426f016190340c55b693d9a76e5015", 1, "metric");
        callTomorrowWeather.enqueue(new Callback<WeatherModelTomorrow>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<WeatherModelTomorrow> call, Response<WeatherModelTomorrow> response) {
                if (response != null && response.isSuccessful()) {
                    WeatherModelTomorrow model = response.body();
                    String weather = model.getData().get(0).getWeatherTomorrow().get(0).getMain();

                    weatherTypeTomorrow.setText(weather);
                    cloudinessPercentTomorrow.setText((int) Math.rint(model.getData().get(0).getClouds()) + " %");
                    windPercentTomorrow.setText((int) Math.rint(model.getData().get(0).getSpeed()) + " m/s");
                    humidityPercentTomorrow.setText((int) Math.rint(model.getData().get(0).getHumidity()) + " %");
                    int tomorrowTemp = (int) Math.rint(model.getData().get(0).getTemp().getDay());
                    currentTemperatureTomorrow.setText(tomorrowTemp + "°");
                    temperatureDifferenceTomorrow.setText((int) Math.rint(model.getData().get(0).getTemp().getMin())
                            + "° - " + (int) Math.rint(model.getData().get(0).getTemp().getMax()) + "°");
                    detailedTypeWeatherTomorrow.setText(model.getData().get(0).getWeatherTomorrow().get(0).getDescription());

                    ConvertUtil.setTemperatureBackgroundColor(tomorrowTemp, cardViewTomorrow);
                    ConvertUtil.setImageWeather(weather, weatherImageTomorrow);

                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherModelTomorrow> call, Throwable t) {

            }
        });
    }

    public void refresh() {
        getCurrentWeatherFromApi(null);
        getTomorrowWeatherFromApi(null);
    }

    public void refresh(MainActivity.MainCallback mainCallback) {
        getCurrentWeatherFromApi(mainCallback);
        getTomorrowWeatherFromApi(mainCallback);

    }
}


