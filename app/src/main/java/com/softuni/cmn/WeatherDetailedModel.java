package com.softuni.cmn;

import java.util.List;

public class WeatherDetailedModel {

    List<WeatherDetailedData> list;

    public List<WeatherDetailedData> getList() {
        return list;
    }

    public class WeatherDetailedData {
        Main main;
        List<WeatherDetailed> weather;

        public Main getMain() {
            return main;
        }

        public List<WeatherDetailed> getWeather() {
            return weather;
        }

    public class Main {
        double temp;

        public double getTemp() {
            return temp;
        }
    }
    public class WeatherDetailed {
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
}
