package com.softuni.cmn;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherModel {

    Coordinates coord;
    List<Weather> weather;
    MainData main;
    Wind wind;
    Clouds clouds;
    int cod;

    public List<Weather> getWeather() {
        return weather;
    }

    public Coordinates getCoord() {
        return coord;
    }

    public MainData getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public int getCod() {
        return cod;
    }


    public class Coordinates {
        double lon;
        double lat;
    }

    public class MainData {

        @SerializedName("temp")
        double temperature;
        @SerializedName("temp_min")
        double minTemperature;
        @SerializedName("temp_max")
        double maxTemperature;
        double humidity;

        public double getTemperature() {
            return temperature;
        }

        public double getMinTemperature() {
            return minTemperature;
        }

        public double getMaxTemperature() {
            return maxTemperature;
        }

        public double getHumidity() {
            return humidity;
        }

    }

    public class Wind {
        double speed;

        public double getSpeed() {
            return speed;
        }
    }

    public class Clouds {
        @SerializedName("all")
        double cloudiness;

        public double getCloudiness() {
            return cloudiness;
        }
    }

    class Code {
        int cod;

        public int getCod() {
            return cod;
        }
    }

    public class Weather {
        String main;
        String description;

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }
    }
}
