package com.softuni.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.softuni.weatherModel.WeatherDetailedModel;

import java.util.ArrayList;
import java.util.List;


public class WeatherDetailsFragmentAdapter extends RecyclerView.Adapter<WeatherDetailsFragmentViewHolder> {
    List<AdapterDetailModel> data = new ArrayList<>();

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


    @Override
    public void onBindViewHolder(@NonNull WeatherDetailsFragmentViewHolder holder, int position) {
        AdapterDetailModel dataItem = this.data.get(position);
        holder.dateAndHour.setText(dataItem.date);
        holder.detailsTypeWeather.setText(dataItem.weather);
        holder.detailsTemperature.setText(dataItem.temperature + "°");
        holder.detailsDescription.setText(dataItem.weatherDesc);

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
