package edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours;

import java.io.Serializable;

public class WeatherHourPostInfo implements Serializable {

    private final String mDate;
    private final String mTime;
    private final String mOutlook;
    private final String mTemperature;

    private WeatherHourPostInfo(final WeatherHourInfoBuilder builder) {
        this.mDate = builder.mDate;
        this.mTime = builder.mTime;
        this.mOutlook = builder.mOutlook;
        this.mTemperature = builder.mTemperature;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public String getOutlook() {
        return mOutlook;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public static class WeatherHourInfoBuilder {
        private final String mDate;
        private final String mTime;
        private String mOutlook;
        private String mTemperature;

        public WeatherHourInfoBuilder(String date, String time) {
            this.mDate = date;
            this.mTime = time;
        }

        public WeatherHourInfoBuilder addOutlook(final String outlook) {
            this.mOutlook = outlook;
            return this;
        }

        public WeatherHourInfoBuilder addTemperature(final String temperature) {
            this.mTemperature = temperature;
            return this;
        }

        public WeatherHourPostInfo build() {
            return new WeatherHourPostInfo(this);
        }
    }

}
