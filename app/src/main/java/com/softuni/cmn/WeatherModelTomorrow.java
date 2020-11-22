package com.softuni.cmn;

import java.util.List;

public class WeatherModelTomorrow {
    List<WeatherData> list;

    public List<WeatherData> getData() {
        return list;
    }

    public class WeatherData {
        Temp temp;
        double humidity;
        double speed;
        double clouds;
       List<WeatherTomorrow> weather;

        public Temp getTemp() {
            return temp;
        }

        public double getHumidity() {
            return humidity;
        }

        public double getSpeed() {
            return speed;
        }

        public double getClouds() {
            return clouds;
        }

        public List<WeatherTomorrow>getWeatherTomorrow() {
            return weather;
        }


    }

    public class Temp {
        double day;
        double min;
        double max;

        public double getDay() {
            return day;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }
    }

    public class WeatherTomorrow {
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




