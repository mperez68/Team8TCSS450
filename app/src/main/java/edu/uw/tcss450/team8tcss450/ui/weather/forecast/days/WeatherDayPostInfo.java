package edu.uw.tcss450.team8tcss450.ui.weather.forecast.days;

import edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours.WeatherHourPostInfo;

public class WeatherDayPostInfo {

    private final String mDate;
    private final String mOutlook;
    private final String mHighTemperature;
    private final String mLowTemperature;

    private WeatherDayPostInfo(final WeatherDayPostInfo.WeatherDayInfoBuilder builder) {
        this.mDate = builder.mDate;
        this.mOutlook = builder.mOutlook;
        this.mHighTemperature = builder.mHighTemperature;
        this.mLowTemperature = builder.mLowTemperature;
    }

    public String getDate() {
        return mDate;
    }

    public String getOutlook() {
        return mOutlook;
    }

    public String getHighTemperature() {
        return mHighTemperature;
    }

    public String getLowTemperature() { return mLowTemperature; }

    public static class WeatherDayInfoBuilder {
        private final String mDate;
        private String mOutlook;
        private String mHighTemperature;
        private String mLowTemperature;

        public WeatherDayInfoBuilder(String date) {
            this.mDate = date;
        }

        public WeatherDayInfoBuilder addOutlook(final String outlook) {
            this.mOutlook = outlook;
            return this;
        }

        public WeatherDayInfoBuilder addHighTemperature(final String highTemperature) {
            this.mHighTemperature = highTemperature;
            return this;
        }

        public WeatherDayInfoBuilder addLowTemperature(final String lowTemperature) {
            this.mLowTemperature = lowTemperature;
            return this;
        }

        public WeatherDayPostInfo build() {
            return new WeatherDayPostInfo(this);
        }
    }

}
