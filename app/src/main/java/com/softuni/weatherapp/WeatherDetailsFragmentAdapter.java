package com.softuni.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
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


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull WeatherDetailsFragmentViewHolder holder, int position) {
        AdapterDetailModel dataItem = this.data.get(position);
        holder.dateAndHour.setText(dataItem.date);
        holder.detailsTypeWeather.setText(dataItem.weather);
        holder.detailsTemperature.setText(dataItem.temperature + "°");
        holder.detailsDescription.setText(dataItem.weatherDesc);

        Context c = holder.itemView.getContext();

        if (dataItem.temperature < 11) {
            holder.cardView.setCardBackgroundColor(c.getResources().getColor(R.color.blue));
        }else if (dataItem.temperature < 13){
            holder.cardView.setCardBackgroundColor(c.getResources().getColor(R.color.green));
        }else {
            holder.cardView.setCardBackgroundColor(c.getResources().getColor(R.color.yellow));
        }

        if (dataItem.weather.equalsIgnoreCase("thunderstorm")){
            holder.weatherPicture.setImageResource(R.drawable.ic_wi_lightning);
        }else if (dataItem.weather.equalsIgnoreCase("drizzle")){
            holder.weatherPicture.setImageResource(R.drawable.ic_wi_sleet);
        }else if (dataItem.weather.equalsIgnoreCase("rain")) {
            holder.weatherPicture.setImageResource(R.drawable.ic_wi_rain);
        }else if (dataItem.weather.equalsIgnoreCase("fog")) {
            holder.weatherPicture.setImageResource(R.drawable.ic_wi_fog);
        }else if (dataItem.weather.equalsIgnoreCase("clouds")) {
            holder.weatherPicture.setImageResource(R.drawable.ic_wi_cloudy);
        }else if (dataItem.weather.equalsIgnoreCase("various")) {
            holder.weatherPicture.setImageResource(R.drawable.ic_wi_cloudy);
        }else if (dataItem.weather.equalsIgnoreCase("snow")) {
            holder.weatherPicture.setImageResource(R.drawable.ic_wi_snow);
        }else if (dataItem.weather.equalsIgnoreCase("extreme")) {
            holder.weatherPicture.setImageResource(R.drawable.ic_wi_meteor);
        }else if (dataItem.weather.equalsIgnoreCase("clear")) {
            holder.weatherPicture.setImageResource(R.drawable.ic_wi_day_sunny);
        }
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
