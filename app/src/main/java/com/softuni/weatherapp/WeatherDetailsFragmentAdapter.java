package com.softuni.weatherapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class WeatherDetailsFragmentAdapter extends RecyclerView.Adapter<WeatherDetailsFragmentViewHolder> {
    List<AdapterDetailModel> data;


    public WeatherDetailsFragmentAdapter(List<AdapterDetailModel> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public WeatherDetailsFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_detailed_fragment, parent, false);
        WeatherDetailsFragmentViewHolder holder = new WeatherDetailsFragmentViewHolder(v);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull WeatherDetailsFragmentViewHolder holder, int position) {
        AdapterDetailModel dataItem = this.data.get(position);
        holder.dateAndHour.setText(dataItem.date);
        holder.detailsTypeWeather.setText(dataItem.weather);
        holder.detailsTemperature.setText(dataItem.temperature + "°");
        holder.detailsDescription.setText(dataItem.weatherDesc);
        ConvertUtil.setTemperatureBackgroundColor(dataItem.temperature, holder.cardView);
        ConvertUtil.setImageWeather(dataItem.weather, holder.weatherPicture);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class AdapterDetailModel {
        public String date;
        public String weather;
        public String weatherDesc;
        public int temperature;
    }
}
