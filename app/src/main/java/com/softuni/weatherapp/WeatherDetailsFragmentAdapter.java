//package com.softuni.weatherapp;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WeatherDetailsFragmentAdapter extends RecyclerView.Adapter<WeatherDetailsFragmentViewHolder> {
//        List<> container = new ArrayList();
//    @NonNull
//    @Override
//    public WeatherDetailsFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.activity_details_tab, parent, false);
//       WeatherDetailsFragmentViewHolder holder = new  WeatherDetailsFragmentViewHolder(v);
//        return holder;
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(@NonNull WeatherDetailsFragmentViewHolder holder, int position) {
//        int image = images[position];
//       WeatherDetailsFragmentViewHolder.image.setImageResource(image);
//        WeatherDetailsFragmentViewHolder.textDescription.setText("Position: " + position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return container.size();
//    }
//}
