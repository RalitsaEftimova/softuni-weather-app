package com.softuni.weatherapp;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softuni.weatherModel.WeatherDetailedModel;
import com.softuni.weatherModel.WeatherService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class ConvertUtil extends MainActivity {

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

//    public  void getWeatherDetailedFromApi(final RecyclerView recyclerView) {
//        WeatherService service = getWeatherService();
//        Call<WeatherDetailedModel> callDetailedWeather = service.getDetailedWeather(lat, lon,
//                "09a8a590d1b034cf0cd50777f7e675fd", 9, "metric");
//        callDetailedWeather.enqueue(new Callback<WeatherDetailedModel>() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onResponse(Call<WeatherDetailedModel> call, Response<WeatherDetailedModel> response) {
//                if (response != null && response.isSuccessful()) {
//                    WeatherDetailedModel model = response.body();
//
//                    setupWeatherDetailedFragmentAdapter(ConvertUtil.convertFromWeatherDetailModelToDetailAdapterData(model),recyclerView);
//
//                } else {
//                    Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WeatherDetailedModel> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void setupWeatherDetailedFragmentAdapter(List<WeatherDetailsFragmentAdapter.AdapterDetailModel> dataForAdapter, RecyclerView recyclerView) {
//        WeatherDetailsFragmentAdapter detailsFragmentAdapter = new WeatherDetailsFragmentAdapter(dataForAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//        recyclerView.setAdapter(detailsFragmentAdapter);
//    }
}
