package com.softuni.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softuni.weatherModel.WeatherDetailedModel;
import com.softuni.weatherModel.WeatherModel;
import com.softuni.weatherModel.WeatherService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.material.internal.ContextUtils.getActivity;


public  class ConvertUtil  {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<WeatherDetailsFragmentAdapter.AdapterDetailModel>
    convertFromWeatherDetailModelToDetailAdapterData(WeatherDetailedModel model) {

        List<WeatherDetailsFragmentAdapter.AdapterDetailModel> dataForAdapter = new ArrayList<>();
        int step = 3;
        for (int i = 0; i < model.getList().size(); i++) {
            WeatherDetailsFragmentAdapter.AdapterDetailModel obj = new WeatherDetailsFragmentAdapter.AdapterDetailModel();
            if (i == 0){
                obj.date = getDateAndHour(i);
            }else {
                obj.date = getDateAndHour(step);
                step += 3;
            }

            obj.temperature = (int) Math.rint(model.getList().get(i).getMain().getTemp());
            obj.weather = model.getList().get(i).getWeather().get(0).getMain();
            obj.weatherDesc = model.getList().get(i).getWeather().get(0).getDescription();

            dataForAdapter.add(obj);
        }

        return dataForAdapter;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static String getDateAndHour(int step) {
        String dateAndHour = "";
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour + step > 23){
            dateAndHour = ((day +1)+"/" + (month+1) + " " + ((hour + step) - 24) + ":00");

        }else {
            dateAndHour = (day +"/" + (month+1) + " " + (hour + step) + ":00");
        }

        return dateAndHour;
    }

    public static void setTemperatureBackgroundColor(int temperature, CardView cardView) {
        if (temperature < 15) {
            cardView.setCardBackgroundColor(cardView.getResources().getColor(R.color.blue));
        } else if (temperature < 22) {
            cardView.setCardBackgroundColor(cardView.getResources().getColor(R.color.green));
        } else {
            cardView.setCardBackgroundColor(cardView.getResources().getColor(R.color.yellow));
        }
    }

    public static void setImageWeather(String weather, ImageView weatherImage) {
        if (weather.equalsIgnoreCase("thunderstorm")) {
            weatherImage.setImageResource(R.drawable.ic_wi_lightning);
        } else if (weather.equalsIgnoreCase("drizzle")) {
            weatherImage.setImageResource(R.drawable.ic_wi_sleet);
        } else if (weather.equalsIgnoreCase("rain")) {
            weatherImage.setImageResource(R.drawable.ic_wi_rain);
        } else if (weather.equalsIgnoreCase(
                "mist")
                || weather.equalsIgnoreCase("fog")) {
            weatherImage.setImageResource(R.drawable.ic_wi_fog);
        } else if (weather.equalsIgnoreCase("clouds")) {
            weatherImage.setImageResource(R.drawable.ic_wi_cloudy);
        } else if (weather.equalsIgnoreCase("various")) {
            weatherImage.setImageResource(R.drawable.ic_wi_cloudy);
        } else if (weather.equalsIgnoreCase("snow")) {
            weatherImage.setImageResource(R.drawable.ic_wi_snow);
        } else if (weather.equalsIgnoreCase("extreme")) {
            weatherImage.setImageResource(R.drawable.ic_wi_meteor);
        } else if (weather.equalsIgnoreCase("clear")) {
            weatherImage.setImageResource(R.drawable.ic_wi_day_sunny);
        }
    }




}
