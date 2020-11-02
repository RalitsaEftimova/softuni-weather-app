package com.softuni.weatherapp;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.softuni.weatherModel.WeatherDetailedModel;
import com.softuni.weatherModel.WeatherService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsTab extends Fragment {
    RecyclerView recyclerView;
    List<WeatherDetailedModel.WeatherDetailedData> data;
    double latNet;
    double lonNet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_details_tab, container, false);

        recyclerView = view.findViewById(R.id.rec_view);

        latNet = ((MainActivity) getActivity()).lat;
        lonNet = ((MainActivity) getActivity()).lon;

        getWeatherDetailedFromApi();
        return view;
    }

    private void getWeatherDetailedFromApi() {
        WeatherService service = ((MainActivity) getActivity()).getWeatherService();
        Call<WeatherDetailedModel> callDetailedWeather = service.getDetailedWeather(latNet, lonNet,
                "09a8a590d1b034cf0cd50777f7e675fd", 9, "metric");
        callDetailedWeather.enqueue(new Callback<WeatherDetailedModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<WeatherDetailedModel> call, Response<WeatherDetailedModel> response) {
                if (response != null && response.isSuccessful()) {
                    WeatherDetailedModel model = response.body();

                    setupWeatherDetailedFragmentAdapter(ConvertUtil.convertFromWeatherDetailModelToDetailAdapterData(model));

                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherDetailedModel> call, Throwable t) {

            }
        });
    }

    private void setupWeatherDetailedFragmentAdapter(List<WeatherDetailsFragmentAdapter.AdapterDetailModel> dataForAdapter) {
        WeatherDetailsFragmentAdapter detailsFragmentAdapter = new WeatherDetailsFragmentAdapter(dataForAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(detailsFragmentAdapter);
    }
}
