package com.softuni.cmn;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.softuni.weatherapp.R;

public class WeatherDetailsFragmentViewHolder extends RecyclerView.ViewHolder {
    public TextView dateAndHour;
    public TextView detailsTypeWeather;
    public ImageView weatherPicture;
    public TextView detailsTemperature;
    public TextView detailsDescription;
    public CardView cardView;

    public WeatherDetailsFragmentViewHolder(@NonNull View itemView) {
        super(itemView);
        dateAndHour = itemView.findViewById(R.id.date_and_hour);
        detailsTypeWeather = itemView.findViewById(R.id.txt_details_weather);
        weatherPicture = itemView.findViewById(R.id.img_weather);
        detailsTemperature = itemView.findViewById(R.id.txt_details_temperature);
        detailsDescription = itemView.findViewById(R.id.details_weather_description);
        cardView = itemView.findViewById(R.id.card_view_detailed);

    }
}
