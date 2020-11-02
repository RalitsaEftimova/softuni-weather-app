package com.softuni.weatherapp;

import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.softuni.weatherModel.WeatherDetailedModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConvertUtil {

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
}
