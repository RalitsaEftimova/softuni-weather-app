package com.softuni.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.softuni.weatherModel.WeatherDetailedModel;
import com.softuni.weatherModel.WeatherService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String city;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapter adapter;
    EditText txtCity;
    ProgressBar progressBar;

    private LocationManager locationManager;
    public double lat;
    public double lon;
    private WeatherService weatherService;
    private Location location;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        txtCity = findViewById(R.id.editLocation);
        progressBar = findViewById(R.id.simpleProgressBar);

        initRetrofit();
        setSupportActionBar(toolbar);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new OverallTab(), "Overall");
        adapter.addFragment(new DetailsTab(), "Details");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        txtCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    city = txtCity.getText().toString();
                    ((OverallTab) adapter.getItem(0)).getCurrentCityWeather(null, city);
                    ((OverallTab) adapter.getItem(0)).getTomorrowCityWeather(null, city);
                    ((DetailsTab) adapter.getItem(1)).getDetailedCityWeather(mainCallback, city);
                    return true;
                }
                return false;
            }
        });
        if (isNetworkOnline()) {
            location = getLastKnowLocation();

            if (!(location == null)) {
                setCityFromCoords(location);

            } else {
                setDefaultCityIfMissCoords();
            }
        } else {
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (isNetworkOnline()) {
            location = getLastKnowLocation();
            if (!(location == null)) {
                setCityFromCoords(location);
            } else {
                setDefaultCityIfMissCoords();
            }
        } else {
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_bar_search) {

            city = txtCity.getText().toString();
            ((OverallTab) adapter.getItem(0)).getCurrentCityWeather(null, city);
            ((OverallTab) adapter.getItem(0)).getTomorrowCityWeather(null, city);
            ((DetailsTab) adapter.getItem(1)).getDetailedCityWeather(mainCallback, city);

        } else if (item.getItemId() == R.id.action_bar_refresh) {
            progressBar.setVisibility(View.VISIBLE);
            if (isNetworkOnline()) {
                city = txtCity.getText().toString();
                if (!TextUtils.isEmpty(city)) {
                    ((OverallTab) adapter.getItem(0)).getCurrentCityWeather(null, city);
                    ((OverallTab) adapter.getItem(0)).getTomorrowCityWeather(null, city);
                    ((DetailsTab) adapter.getItem(1)).getDetailedCityWeather(mainCallback, city);
                } else {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    checkLocationPermission();
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                    txtCity.setText(getLocationName(lat, lon));
                    city = txtCity.getText().toString();
                    ((OverallTab) adapter.getItem(0)).getCurrentCityWeather(null, city);
                    ((OverallTab) adapter.getItem(0)).getTomorrowCityWeather(null, city);
                    ((DetailsTab) adapter.getItem(1)).getDetailedCityWeather(mainCallback, city);
                }

            } else {
                Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);

    }

    private Location getLastKnowLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkLocationPermission();
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return location;
    }

    private void setCityFromCoords(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
        txtCity.setText(getLocationName(lat, lon));
    }

    private void setDefaultCityIfMissCoords() {
        txtCity.setText(R.string.sofia);
        city = txtCity.getText().toString();
        Toast.makeText(this, "Your location didn't found.Displayed Sofia city", Toast.LENGTH_SHORT).show();
    }

    public String getCity() {
        return city;
    }

    private final MainCallback mainCallback = new MainCallback() {
        @Override
        public void onFinished() {
            hideProgressBar();
        }
    };

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public String getLocationName(double lattitude, double longitude) {

        String cityName = "";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {

            List<Address> addresses = gcd.getFromLocation(lattitude, longitude,
                    10);

            for (Address adrs : addresses) {
                if (adrs != null) {

                    String city = adrs.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;

                    } else {

                    }
                    // // you should also try with addresses.get(0).toSring();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("title")
                        .setMessage("message")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        locationManager.requestLocationUpdates("gps",
                                400, 1, new LocationListener() {
                                    @Override
                                    public void onLocationChanged(Location location) {

                                    }

                                    @Override
                                    public void onStatusChanged(String provider, int status, Bundle extras) {

                                    }

                                    @Override
                                    public void onProviderEnabled(String provider) {

                                    }

                                    @Override
                                    public void onProviderDisabled(String provider) {

                                    }
                                });
                    }
                }
            }
        }
    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;

    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherService = retrofit.create(WeatherService.class);
    }

    public WeatherService getWeatherService() {
        return weatherService;
    }

    public interface MainCallback {
        void onFinished();
    }
}
